package com.vibe.app.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.graphics.Rect
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.vibe.app.ui.OverlayManager

class VibeAccessibilityService : AccessibilityService() {

    companion object {
        private const val TAG = "VibeAccessibilityService"
        private const val WHATSAPP_PACKAGE = "com.whatsapp"
        private const val GRACE_PERIOD_MS = 2000L
    }

    private var isWhatsAppForeground = false
    private val processedMessages = mutableSetOf<Int>()
    private val messageAnalyzer = ViewNodeAnalyzer()
    private lateinit var overlayManager: OverlayManager
    private var currentVibeScore = 100
    private var monitoringStartTime = 0L

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d(TAG, "✅ VibeAccessibilityService connected")
        overlayManager = OverlayManager(this)

        val serviceInfo = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED or
                    AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED or
                    AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED

            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS or
                    AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS

            notificationTimeout = 100
            packageNames = arrayOf(WHATSAPP_PACKAGE)
        }

        setServiceInfo(serviceInfo)
        Log.d(TAG, "Service configured for WhatsApp monitoring")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        try {
            when (event.eventType) {
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> handleWindowStateChanged(event)
                AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> handleWindowContentChanged(event)
                AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> handleTextChanged(event)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error handling accessibility event", e)
        }
    }

    private fun handleWindowStateChanged(event: AccessibilityEvent) {
        val packageName = event.packageName?.toString()

        if (packageName == WHATSAPP_PACKAGE) {
            if (!isWhatsAppForeground) {
                isWhatsAppForeground = true
                Log.d(TAG, "📱 WhatsApp opened - now monitoring")
                onWhatsAppOpened()
            }
        } else {
            if (isWhatsAppForeground) {
                isWhatsAppForeground = false
                Log.d(TAG, "📱 WhatsApp closed - stopped monitoring")
                onWhatsAppClosed()
            }
        }
    }

    private fun handleWindowContentChanged(event: AccessibilityEvent) {
        if (!isWhatsAppForeground || System.currentTimeMillis() - monitoringStartTime < GRACE_PERIOD_MS) {
            return
        }

        event.source?.let { source ->
            findMessageBubbles(source).forEach { bubble ->
                processMessageBubble(bubble)
            }
        }
    }

    private fun handleTextChanged(event: AccessibilityEvent) {
        if (!isWhatsAppForeground) return

        event.source?.let { source ->
            if (isInputField(source)) {
                val inputText = source.text?.toString() ?: ""
                if (inputText.isNotEmpty()) {
                    Log.d(TAG, "⌨️ Input field changed: ${inputText.take(50)}...")
                    onInputFieldChanged(inputText)
                }
            }
        }
    }

    private fun findMessageBubbles(root: AccessibilityNodeInfo): List<AccessibilityNodeInfo> {
        val messageBubbles = mutableListOf<AccessibilityNodeInfo>()
        findMessageBubblesRecursive(root, messageBubbles)
        return messageBubbles
    }

    private fun findMessageBubblesRecursive(
        node: AccessibilityNodeInfo,
        bubbles: MutableList<AccessibilityNodeInfo>
    ) {
        if (isMessageBubble(node)) {
            bubbles.add(node)
            return
        }
        for (i in 0 until node.childCount) {
            node.getChild(i)?.let { findMessageBubblesRecursive(it, bubbles) }
        }
    }

    private fun isMessageBubble(node: AccessibilityNodeInfo): Boolean {
        val text = node.text?.toString()
        if (text.isNullOrBlank()) return false

        val className = node.className?.toString() ?: ""
        if (!className.contains("TextView") && !className.contains("Text")) return false

        if (!node.isVisibleToUser) return false
        val bounds = Rect()
        node.getBoundsInScreen(bounds)
        if (bounds.isEmpty) return false

        val width = bounds.width()
        val height = bounds.height()
        if (width < 50 || height < 20 || width > 800 || height > 500) return false

        var parent = node.parent
        var depth = 0
        while (parent != null && depth < 5) {
            val parentClass = parent.className?.toString() ?: ""
            if (parentClass.contains("RecyclerView") || parentClass.contains("ListView")) {
                return true
            }
            parent = parent.parent
            depth++
        }
        return false
    }

    private fun isInputField(node: AccessibilityNodeInfo): Boolean {
        val className = node.className?.toString() ?: ""
        return className.contains("EditText") && node.isEditable
    }

    private fun processMessageBubble(bubble: AccessibilityNodeInfo) {
        val messageText = bubble.text?.toString() ?: return
        if (messageText.isBlank()) return

        val bounds = Rect()
        bubble.getBoundsInScreen(bounds)
        val messageHash = messageText.hashCode() + bounds.hashCode()

        if (processedMessages.contains(messageHash)) return
        processedMessages.add(messageHash)

        val direction = determineMessageDirection(bubble)
        Log.d(TAG, "💬 Message detected: ${messageText.take(50)}...")

        val analysisResult = messageAnalyzer.analyzeMessage(messageText, direction, bounds)
        onMessageDetected(analysisResult)
    }

    private fun determineMessageDirection(bubble: AccessibilityNodeInfo): MessageDirection {
        val bounds = Rect()
        bubble.getBoundsInScreen(bounds)
        val screenWidth = resources.displayMetrics.widthPixels
        val rightThreshold = screenWidth * 0.6
        return if (bounds.centerX() > rightThreshold) MessageDirection.OUTGOING else MessageDirection.INCOMING
    }

    private fun onWhatsAppOpened() {
        Log.d(TAG, "🎯 WhatsApp monitoring started")
        currentVibeScore = 100
        processedMessages.clear()
        monitoringStartTime = System.currentTimeMillis()
        overlayManager.showMonitoringActive()
        overlayManager.showVibeMeter(SentimentCategory.NEUTRAL, currentVibeScore)
    }

    private fun onWhatsAppClosed() {
        Log.d(TAG, "🎯 WhatsApp monitoring stopped")
        processedMessages.clear()
        overlayManager.hideOverlay()
        overlayManager.hideVibeMeter()
    }

    private fun onInputFieldChanged(text: String) {
        Log.d(TAG, "📝 Input field changed - should analyze sentiment")
    }

    private fun onMessageDetected(analysisResult: MessageAnalysisResult) {
        Log.d(TAG, "📊 Message analyzed - Sentiment: ${analysisResult.sentiment}, Score Change: ${analysisResult.sentimentScore}")

        if (analysisResult.sentimentScore != 0.0f) {
            currentVibeScore = (currentVibeScore + analysisResult.sentimentScore).toInt().coerceIn(0, 100)
        }

        overlayManager.showVibeMeter(analysisResult.sentiment, currentVibeScore)

        when (analysisResult.sentiment) {
            SentimentCategory.VIOLENCE, SentimentCategory.TOXIC, SentimentCategory.AGGRESSIVE -> {
                overlayManager.showOverlay(analysisResult)
            }
            SentimentCategory.SUPPORTIVE -> {
                overlayManager.showVibeSpark()
            }
            else -> {
                overlayManager.hideOverlay()
            }
        }
    }

    override fun onInterrupt() {
        Log.d(TAG, "⚠️ Accessibility service interrupted")
        isWhatsAppForeground = false
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "🛑 Accessibility service destroyed")
        isWhatsAppForeground = false
        overlayManager.hideOverlay()
        overlayManager.hideVibeMeter()
    }
}