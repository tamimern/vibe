package com.vibe.app.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.vibe.app.data.ReplyTemplates
import com.vibe.app.data.VibePoints
import com.vibe.app.data.VibePreferences
import com.vibe.app.ui.OverlayManager

class VibeAccessibilityService : AccessibilityService() {

    companion object {
        private const val TAG = "VibeAccessibilityService"
        private const val WHATSAPP_PACKAGE = "com.whatsapp"
        /** Don't score messages visible on screen when you first enter; only score messages that appear after this. */
        private const val GRACE_PERIOD_MS = 2500L
    }

    private var isWhatsAppForeground = false
    private var monitoringStartTime = 0L
    private val processedMessages = mutableSetOf<Int>()
    private val messageAnalyzer = ViewNodeAnalyzer()
    private lateinit var overlayManager: OverlayManager
    private lateinit var vibePreferences: VibePreferences
    private var currentVibeScore
        get() = _currentVibeScore
        set(value) {
            _currentVibeScore = value.coerceIn(0, 100)
            if (::vibePreferences.isInitialized) vibePreferences.harmonyScore = _currentVibeScore
        }
    private var _currentVibeScore = 100
    private var isEditingAfterSpeedBump = false
    private var lastTypedText = ""

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d(TAG, "✅ VibeAccessibilityService connected")
        vibePreferences = VibePreferences(this)
        _currentVibeScore = vibePreferences.harmonyScore
        overlayManager = OverlayManager(this, vibePreferences)

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
                else -> Unit
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
        if (!isWhatsAppForeground) return
        // During grace period we only "seed" processedMessages (don't score) so existing chat doesn't affect score
        val inGracePeriod = System.currentTimeMillis() - monitoringStartTime < GRACE_PERIOD_MS

        // Use full window root so new INCOMING messages are always found (event.source can be null or a small subtree)
        val root = rootInActiveWindow ?: event.source ?: return
        val batchResults = mutableListOf<MessageAnalysisResult>()
        findMessageBubbles(root).forEach { bubble ->
            processMessageBubble(bubble, isInitialScan = false, inGracePeriod = inGracePeriod)?.let { batchResults.add(it) }
        }
        if (batchResults.isNotEmpty()) {
            onMessageBatchDetected(batchResults)
        }
    }

    private fun handleTextChanged(event: AccessibilityEvent) {
        if (!isWhatsAppForeground || isEditingAfterSpeedBump) return
        val text = event.text?.toString() ?: return
        if (text.length < 3) return
        event.source?.let { source ->
            val analysisResult = messageAnalyzer.analyzeMessage(text, MessageDirection.OUTGOING, Rect())
            if (analysisResult.sentiment == SentimentCategory.TOXIC || analysisResult.sentiment == SentimentCategory.VIOLENCE) {
                isEditingAfterSpeedBump = true
                lastTypedText = text
                vibePreferences.speedBumpsHit++
                overlayManager.showSpeedBump(
                    originalText = text,
                    category = analysisResult.sentiment,
                    onEdit = { overlayManager.hideOverlay() },
                    onSend = {
                        findSendButton()?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        overlayManager.hideOverlay()
                        isEditingAfterSpeedBump = false
                    }
                )
            }
        }
    }

    private fun findMessageBubbles(root: AccessibilityNodeInfo): List<AccessibilityNodeInfo> {
        val messageBubbles = mutableListOf<AccessibilityNodeInfo>()
        findMessageBubblesRecursive(root, messageBubbles)
        return messageBubbles
    }

    private fun findMessageBubblesRecursive(
        node: AccessibilityNodeInfo?,
        bubbles: MutableList<AccessibilityNodeInfo>
    ) {
        if (node == null) return
        if (isMessageBubble(node)) {
            bubbles.add(node)
            return
        }
        for (i in 0 until node.childCount) {
            findMessageBubblesRecursive(node.getChild(i), bubbles)
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

    /**
     * Stable key for deduplication: same (content, direction) = same message.
     * We do NOT use bounds, so when the list scrolls or re-layouts the same message
     * is only scored once per session.
     */
    private fun messageDedupKey(messageText: String, direction: MessageDirection): Int {
        return 31 * messageText.hashCode() + direction.ordinal
    }

    /** @return the analysis result when not initial scan, not in grace period, and message was new; null otherwise */
    private fun processMessageBubble(
        bubble: AccessibilityNodeInfo,
        isInitialScan: Boolean,
        inGracePeriod: Boolean = false
    ): MessageAnalysisResult? {
        val messageText = bubble.text?.toString() ?: return null
        if (messageText.isBlank()) return null
        // Skip timestamp/date labels so only real chat messages affect the score
        if (isTimestampOrLabel(messageText)) return null

        val bounds = Rect()
        bubble.getBoundsInScreen(bounds)
        val direction = determineMessageDirection(bubble)
        val dedupKey = messageDedupKey(messageText, direction)

        if (processedMessages.contains(dedupKey)) return null

        processedMessages.add(dedupKey)

        if (isInitialScan) {
            Log.d(TAG, "📝 Initial scan, caching message: ${messageText.take(50)}...")
            return null
        }
        // During grace period (e.g. first 2.5s after opening WhatsApp) don't score existing on-screen messages
        if (inGracePeriod) {
            Log.d(TAG, "📝 Grace period: ignoring on-screen message for score: ${messageText.take(50)}...")
            return null
        }

        Log.d(TAG, "💬 New message detected: ${messageText.take(50)}...")

        return messageAnalyzer.analyzeMessage(messageText, direction, bounds)
    }

    /** Ignores UI labels like "10:33 PM", "Today", "Yesterday" so they don't dilute the harmony score. */
    private fun isTimestampOrLabel(text: String): Boolean {
        val t = text.trim()
        if (t.length > 50) return false
        // Time patterns: 10:33 PM, 22:33, 9:48 PM, 10:01 PM
        if (Regex("^\\d{1,2}:\\d{2}\\s*(AM|PM)?$", RegexOption.IGNORE_CASE).matches(t)) return true
        // Date labels
        if (t.equals("Today", true) || t.equals("Yesterday", true)) return true
        if (t.equals("היום", true) || t.equals("אתמול", true)) return true
        return false
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
        _currentVibeScore = vibePreferences.harmonyScore
        processedMessages.clear()
        monitoringStartTime = System.currentTimeMillis()
        vibePreferences.recordActivity()
        overlayManager.showMonitoringActive()
        overlayManager.showVibeMeter(currentVibeScore)

        Handler(Looper.getMainLooper()).postDelayed({
            rootInActiveWindow?.let { root ->
                Log.d(TAG, "🔬 Performing initial scan of the chat window...")
                findMessageBubbles(root).forEach { bubble ->
                    processMessageBubble(bubble, isInitialScan = true)
                }
            }
        }, 500)
    }

    private fun onWhatsAppClosed() {
        Log.d(TAG, "🎯 WhatsApp monitoring stopped")
        processedMessages.clear()
        overlayManager.hideOverlay()
        overlayManager.hideVibeMeter()
    }

    /**
     * Process a batch of new messages from one content-change event.
     * Updates score from all messages but shows only one overlay: toxic (INCOMING) wins over positive.
     */
    private fun onMessageBatchDetected(batch: List<MessageAnalysisResult>) {
        // 1) Apply score updates and VP/sparks for each message
        for (analysisResult in batch) {
            Log.d(TAG, "📊 Message analyzed - Sentiment: ${analysisResult.sentiment}, Score Change: ${analysisResult.sentimentScore}")

            if (isEditingAfterSpeedBump && analysisResult.direction == MessageDirection.OUTGOING) {
                val previousSentiment = messageAnalyzer.analyzeMessage(lastTypedText, MessageDirection.OUTGOING, Rect()).sentiment
                if ((previousSentiment == SentimentCategory.TOXIC || previousSentiment == SentimentCategory.VIOLENCE) && analysisResult.sentiment == SentimentCategory.SUPPORTIVE) {
                    vibePreferences.sparksEarned++
                    vibePreferences.recordActivity()
                    currentVibeScore = (currentVibeScore + 10).coerceIn(0, 100)
                } else if (analysisResult.sentimentScore != 0.0f) {
                    currentVibeScore = (currentVibeScore + analysisResult.sentimentScore).toInt().coerceIn(0, 100)
                }
                isEditingAfterSpeedBump = false
            } else {
                if (analysisResult.sentimentScore != 0.0f) {
                    currentVibeScore = (currentVibeScore + analysisResult.sentimentScore).toInt().coerceIn(0, 100)
                }
                if (analysisResult.sentiment == SentimentCategory.SUPPORTIVE) {
                    vibePreferences.addVP(VibePoints.POSITIVE_MESSAGE)
                    vibePreferences.sparksEarned++
                    vibePreferences.recordActivity()
                }
            }
        }

        overlayManager.showVibeMeter(currentVibeScore)

        // 2) Show exactly one overlay: prefer toxic (INCOMING) over supportive
        val incomingToxic = batch.firstOrNull { r ->
            r.direction == MessageDirection.INCOMING && r.sentiment in listOf(
                SentimentCategory.VIOLENCE, SentimentCategory.TOXIC, SentimentCategory.AGGRESSIVE
            )
        }
        val hasSupportive = batch.any { it.sentiment == SentimentCategory.SUPPORTIVE }

        when {
            incomingToxic != null -> {
                overlayManager.showOverlay(incomingToxic) { suggestReply(incomingToxic) }
            }
            hasSupportive -> {
                overlayManager.showVibeSpark()
            }
            else -> {
                overlayManager.hideOverlay()
            }
        }
    }

    private fun suggestReply(analysisResult: MessageAnalysisResult) {
        val inputField = rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.whatsapp:id/entry")?.firstOrNull() ?: return
        val reply = ReplyTemplates.getSuggestedReply(
            analysisResult.sentiment,
            vibePreferences.isHebrew
        )
        val arguments = Bundle().apply {
            putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, reply)
        }
        inputField.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
        findSendButton()?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    private fun findSendButton(): AccessibilityNodeInfo? {
        return rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.whatsapp:id/send")?.firstOrNull()
    }

    override fun onInterrupt() {
        isWhatsAppForeground = false
    }

    override fun onDestroy() {
        super.onDestroy()
        overlayManager.hideOverlay()
        overlayManager.hideVibeMeter()
    }
}