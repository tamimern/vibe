# Step-by-Step: Copy Files to Your Android Project

Your Android project is at: `C:\Users\tmernstein\AndroidStudioProjects\vibe`

## 📁 Step 1: Create the `accessibility` folder

1. **In Android Studio**, look at the left side panel (Project view)
2. Navigate to: `app` → `src` → `main` → `java` → `com` → `vibe` → `app`
3. **Right-click** on the `app` folder (the last one)
4. Select: **New** → **Package**
5. Type the name: `accessibility`
6. Press **Enter**

You should now see: `app/src/main/java/com/vibe/app/accessibility/`

---

## 📄 Step 2: Create VibeAccessibilityService.kt

1. **Right-click** on the `accessibility` folder you just created
2. Select: **New** → **Kotlin Class/File**
3. Name it: `VibeAccessibilityService`
4. Choose: **Class**
5. Press **Enter**

A new file will open. **Delete everything** in that file and **paste this code:**

```kotlin
package com.vibe.app.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityWindowInfo
import androidx.annotation.RequiresApi

/**
 * VibeAccessibilityService - Core Accessibility Service for WhatsApp Monitoring
 * 
 * This service monitors WhatsApp (com.whatsapp) to:
 * 1. Detect when WhatsApp is opened/foreground
 * 2. Identify message bubbles on screen
 * 3. Extract message content and directionality (incoming/outgoing)
 * 4. Monitor EditText input field for real-time sentiment analysis
 * 
 * Privacy: This service MUST NOT store raw text strings.
 * All text processing happens locally and is immediately converted to sentiment tokens.
 */
class VibeAccessibilityService : AccessibilityService() {

    companion object {
        private const val TAG = "VibeAccessibilityService"
        private const val WHATSAPP_PACKAGE = "com.whatsapp"
        
        // WhatsApp-specific view identifiers (may need adjustment based on WhatsApp version)
        private const val MESSAGE_BUBBLE_CLASS = "android.widget.TextView"
        private const val INPUT_FIELD_CLASS = "android.widget.EditText"
    }

    private var isWhatsAppForeground = false
    private var lastProcessedMessageHash: Int? = null
    private val messageAnalyzer = ViewNodeAnalyzer()

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d(TAG, "✅ VibeAccessibilityService connected")
        
        // Configure service info
        val serviceInfo = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED or
                        AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED or
                        AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED
            
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS or
                   AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS
            
            notificationTimeout = 100 // Check every 100ms
            
            // Only monitor WhatsApp
            packageNames = arrayOf(WHATSAPP_PACKAGE)
        }
        
        setServiceInfo(serviceInfo)
        Log.d(TAG, "Service configured for WhatsApp monitoring")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return
        
        try {
            when (event.eventType) {
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                    handleWindowStateChanged(event)
                }
                AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
                    handleWindowContentChanged(event)
                }
                AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                    handleTextChanged(event)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error handling accessibility event", e)
        }
    }

    /**
     * Handle window state changes - detect when WhatsApp comes to foreground
     */
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

    /**
     * Handle window content changes - detect new messages appearing
     */
    private fun handleWindowContentChanged(event: AccessibilityEvent) {
        if (!isWhatsAppForeground) return
        
        val source = event.source ?: return
        
        try {
            // Look for message bubbles in the content
            val messageBubbles = findMessageBubbles(source)
            
            messageBubbles.forEach { bubble ->
                processMessageBubble(bubble)
            }
        } finally {
            source.recycle()
        }
    }

    /**
     * Handle text changes - monitor input field for real-time sentiment
     */
    private fun handleTextChanged(event: AccessibilityEvent) {
        if (!isWhatsAppForeground) return
        
        val source = event.source ?: return
        
        try {
            // Check if this is the input field
            if (isInputField(source)) {
                val inputText = source.text?.toString() ?: ""
                if (inputText.isNotEmpty()) {
                    Log.d(TAG, "⌨️ Input field changed: ${inputText.take(50)}...")
                    onInputFieldChanged(inputText)
                }
            }
        } finally {
            source.recycle()
        }
    }

    /**
     * Find all message bubbles in the current window
     */
    private fun findMessageBubbles(root: AccessibilityNodeInfo): List<AccessibilityNodeInfo> {
        val messageBubbles = mutableListOf<AccessibilityNodeInfo>()
        
        // Recursively search for message bubbles
        findMessageBubblesRecursive(root, messageBubbles)
        
        return messageBubbles
    }

    /**
     * Recursive helper to find message bubbles
     */
    private fun findMessageBubblesRecursive(
        node: AccessibilityNodeInfo,
        bubbles: MutableList<AccessibilityNodeInfo>
    ) {
        if (node == null) return
        
        try {
            // Check if this node is a message bubble
            if (isMessageBubble(node)) {
                bubbles.add(AccessibilityNodeInfo.obtain(node))
                return // Don't recurse into message bubbles
            }
            
            // Recurse into children
            for (i in 0 until node.childCount) {
                val child = node.getChild(i)
                if (child != null) {
                    findMessageBubblesRecursive(child, bubbles)
                    child.recycle()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in findMessageBubblesRecursive", e)
        }
    }

    /**
     * Determine if a node is a message bubble
     * 
     * WhatsApp message bubbles are typically:
     * - TextView nodes with text content
     * - Have specific bounds (position on screen)
     * - Are clickable or long-clickable
     * - May have specific content descriptions or IDs
     */
    private fun isMessageBubble(node: AccessibilityNodeInfo): Boolean {
        // Must have text content
        val text = node.text?.toString()
        if (text.isNullOrBlank()) return false
        
        // Must be a TextView (or similar text view)
        val className = node.className?.toString() ?: ""
        if (!className.contains("TextView") && !className.contains("Text")) {
            return false
        }
        
        // Must be visible and have bounds
        if (!node.isVisibleToUser) return false
        val bounds = Rect()
        node.getBoundsInScreen(bounds)
        if (bounds.isEmpty) return false
        
        // Must be clickable (to select/copy)
        // But not all TextViews are messages - need additional heuristics
        
        // Heuristic: Message bubbles are usually in a scrollable container
        // and have a reasonable size (not too small, not too large)
        val width = bounds.width()
        val height = bounds.height()
        
        // Typical message bubble dimensions (adjust based on testing)
        if (width < 50 || height < 20) return false // Too small
        if (width > 800 || height > 500) return false // Too large (probably header/footer)
        
        // Additional check: Look for parent containers that suggest it's a message
        var parent = node.parent
        var depth = 0
        while (parent != null && depth < 5) {
            val parentClass = parent.className?.toString() ?: ""
            val parentDesc = parent.contentDescription?.toString() ?: ""
            
            // WhatsApp often uses RecyclerView or ListView for message lists
            if (parentClass.contains("RecyclerView") || 
                parentClass.contains("ListView") ||
                parentDesc.contains("message") ||
                parentDesc.contains("chat")) {
                return true
            }
            
            parent = parent.parent
            depth++
        }
        
        return false
    }

    /**
     * Determine if a node is the input field (EditText)
     */
    private fun isInputField(node: AccessibilityNodeInfo): Boolean {
        val className = node.className?.toString() ?: ""
        if (!className.contains("EditText")) return false
        
        // Input field is typically editable and focused
        if (!node.isEditable) return false
        
        // Additional check: Look for hints like "Type a message"
        val hint = node.hintText?.toString() ?: ""
        val desc = node.contentDescription?.toString() ?: ""
        
        return hint.contains("message", ignoreCase = true) ||
               desc.contains("input", ignoreCase = true) ||
               desc.contains("message", ignoreCase = true)
    }

    /**
     * Process a message bubble to extract content and directionality
     */
    private fun processMessageBubble(bubble: AccessibilityNodeInfo) {
        try {
            val messageText = bubble.text?.toString() ?: ""
            if (messageText.isBlank()) return
            
            // Create a hash to avoid processing the same message multiple times
            val messageHash = messageText.hashCode()
            if (messageHash == lastProcessedMessageHash) {
                return // Already processed
            }
            lastProcessedMessageHash = messageHash
            
            // Determine message directionality (incoming vs outgoing)
            val direction = determineMessageDirection(bubble)
            
            // Get message bounds
            val bounds = Rect()
            bubble.getBoundsInScreen(bounds)
            
            Log.d(TAG, "💬 Message detected:")
            Log.d(TAG, "   Text: ${messageText.take(50)}...")
            Log.d(TAG, "   Direction: $direction")
            Log.d(TAG, "   Bounds: $bounds")
            
            // Analyze the message (this will convert to sentiment tokens immediately)
            val analysisResult = messageAnalyzer.analyzeMessage(
                text = messageText,
                direction = direction,
                bounds = bounds
            )
            
            // Process the analysis result (sentiment tokens only, no raw text)
            onMessageDetected(analysisResult)
            
        } catch (e: Exception) {
            Log.e(TAG, "Error processing message bubble", e)
        } finally {
            bubble.recycle()
        }
    }

    /**
     * Determine if a message is incoming (left) or outgoing (right)
     * 
     * WhatsApp typically shows:
     * - Outgoing messages on the right side
     * - Incoming messages on the left side
     */
    private fun determineMessageDirection(bubble: AccessibilityNodeInfo): MessageDirection {
        val bounds = Rect()
        bubble.getBoundsInScreen(bounds)
        
        // Get screen width
        val screenWidth = resources.displayMetrics.widthPixels
        val centerX = bounds.centerX()
        
        // Heuristic: Messages on the right side (more than 60% of screen width) are outgoing
        val rightThreshold = screenWidth * 0.6
        
        return if (centerX > rightThreshold) {
            MessageDirection.OUTGOING
        } else {
            MessageDirection.INCOMING
        }
    }

    /**
     * Callback when WhatsApp is opened
     */
    private fun onWhatsAppOpened() {
        Log.d(TAG, "🎯 WhatsApp monitoring started")
        // TODO: Notify other components (NudgeEngine, OverlayManager, etc.)
    }

    /**
     * Callback when WhatsApp is closed
     */
    private fun onWhatsAppClosed() {
        Log.d(TAG, "🎯 WhatsApp monitoring stopped")
        lastProcessedMessageHash = null // Reset to allow re-processing when reopened
        // TODO: Notify other components
    }

    /**
     * Callback when input field text changes
     */
    private fun onInputFieldChanged(text: String) {
        // TODO: Send to NudgeEngine for real-time sentiment analysis
        // This should trigger the "Speed Bump" logic if sentiment is Toxic/Aggressive
        Log.d(TAG, "📝 Input field changed - should analyze sentiment")
    }

    /**
     * Callback when a message is detected and analyzed
     */
    private fun onMessageDetected(analysisResult: MessageAnalysisResult) {
        // TODO: Process sentiment tokens (not raw text)
        // This should update the Vibe Score and trigger interventions if needed
        Log.d(TAG, "📊 Message analyzed - Sentiment: ${analysisResult.sentiment}, Score: ${analysisResult.sentimentScore}")
    }

    override fun onInterrupt() {
        Log.d(TAG, "⚠️ Accessibility service interrupted")
        isWhatsAppForeground = false
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "🛑 Accessibility service destroyed")
        isWhatsAppForeground = false
    }
}

/**
 * Enum for message direction
 */
enum class MessageDirection {
    INCOMING,  // Left side - messages from others
    OUTGOING   // Right side - messages from user
}

/**
 * Data class for message analysis result
 * Contains only sentiment tokens, NOT raw text
 */
data class MessageAnalysisResult(
    val sentiment: SentimentCategory,
    val sentimentScore: Float,
    val timestamp: Long = System.currentTimeMillis(),
    val direction: MessageDirection,
    val bounds: Rect
)

/**
 * Sentiment categories as per PRD
 */
enum class SentimentCategory {
    TOXIC,
    AGGRESSIVE,
    SUPPORTIVE,
    NEUTRAL,
    EXCLUSIVE
}
```

6. **Save** the file (Ctrl+S or Cmd+S)

---

## 📄 Step 3: Create ViewNodeAnalyzer.kt

1. **Right-click** on the `accessibility` folder again
2. Select: **New** → **Kotlin Class/File**
3. Name it: `ViewNodeAnalyzer`
4. Choose: **Class**
5. Press **Enter**

**Delete everything** and **paste this code:**

```kotlin
package com.vibe.app.accessibility

import android.graphics.Rect
import android.util.Log

/**
 * ViewNodeAnalyzer - Analyzes message bubbles and converts text to sentiment tokens
 * 
 * This class handles the scraping logic and immediate conversion to sentiment tokens.
 * It MUST NOT store raw text strings per privacy requirements.
 */
class ViewNodeAnalyzer {
    
    companion object {
        private const val TAG = "ViewNodeAnalyzer"
    }

    /**
     * Analyze a message and convert to sentiment tokens immediately
     * 
     * This method:
     * 1. Extracts text from the message bubble
     * 2. Immediately analyzes sentiment (using local NLP)
     * 3. Converts to sentiment tokens
     * 4. Discards raw text
     * 
     * @param text The message text (will be discarded after analysis)
     * @param direction Whether the message is incoming or outgoing
     * @param bounds The screen bounds of the message bubble
     * @return MessageAnalysisResult containing only sentiment data (no raw text)
     */
    fun analyzeMessage(
        text: String,
        direction: MessageDirection,
        bounds: Rect
    ): MessageAnalysisResult {
        // TODO: Integrate with SentimentAnalyzer (TensorFlow Lite / ML Kit)
        // For now, using placeholder logic
        
        Log.d(TAG, "Analyzing message (${text.length} chars, direction: $direction)")
        
        // Placeholder sentiment analysis
        // In production, this will call SentimentAnalyzer.analyze(text)
        val sentiment = analyzeSentimentPlaceholder(text)
        val score = calculateSentimentScore(sentiment, text)
        
        // Create result WITHOUT storing raw text
        return MessageAnalysisResult(
            sentiment = sentiment,
            sentimentScore = score,
            timestamp = System.currentTimeMillis(),
            direction = direction,
            bounds = bounds
        )
    }

    /**
     * Placeholder sentiment analysis
     * TODO: Replace with actual TensorFlow Lite / ML Kit integration
     */
    private fun analyzeSentimentPlaceholder(text: String): SentimentCategory {
        val lowerText = text.lowercase()
        
        // Simple keyword-based detection (will be replaced with ML model)
        val toxicKeywords = listOf("hate", "kill", "die", "stupid", "idiot", "ugly")
        val aggressiveKeywords = listOf("fight", "attack", "hurt", "angry", "mad")
        val supportiveKeywords = listOf("love", "care", "help", "support", "thanks", "appreciate")
        val exclusiveKeywords = listOf("exclude", "ignore", "leave out", "not invited")
        
        val toxicCount = toxicKeywords.count { lowerText.contains(it) }
        val aggressiveCount = aggressiveKeywords.count { lowerText.contains(it) }
        val supportiveCount = supportiveKeywords.count { lowerText.contains(it) }
        val exclusiveCount = exclusiveKeywords.count { lowerText.contains(it) }
        
        return when {
            toxicCount > 0 -> SentimentCategory.TOXIC
            aggressiveCount > 0 -> SentimentCategory.AGGRESSIVE
            exclusiveCount > 0 -> SentimentCategory.EXCLUSIVE
            supportiveCount > 0 -> SentimentCategory.SUPPORTIVE
            else -> SentimentCategory.NEUTRAL
        }
    }

    /**
     * Calculate sentiment score (0.0 to 1.0)
     * Higher score = more negative/toxic
     */
    private fun calculateSentimentScore(sentiment: SentimentCategory, text: String): Float {
        return when (sentiment) {
            SentimentCategory.TOXIC -> 0.9f
            SentimentCategory.AGGRESSIVE -> 0.8f
            SentimentCategory.EXCLUSIVE -> 0.7f
            SentimentCategory.NEUTRAL -> 0.5f
            SentimentCategory.SUPPORTIVE -> 0.2f
        }
    }
}
```

6. **Save** the file

---

## 📁 Step 4: Create the `xml` folder

1. In Android Studio, navigate to: `app` → `src` → `main` → `res`
2. **Right-click** on the `res` folder
3. Select: **New** → **Directory**
4. Type: `xml`
5. Press **Enter**

You should now see: `app/src/main/res/xml/`

---

## 📄 Step 5: Create accessibility_service_config.xml

1. **Right-click** on the `xml` folder you just created
2. Select: **New** → **File**
3. Type: `accessibility_service_config.xml`
4. Press **Enter**

**Paste this code:**

```xml
<?xml version="1.0" encoding="utf-8"?>
<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android"
    android:description="@string/accessibility_service_description"
    android:packageNames="com.whatsapp"
    android:accessibilityEventTypes="typeWindowStateChanged|typeWindowContentChanged|typeViewTextChanged"
    android:accessibilityFlags="flagIncludeNotImportantViews|flagReportViewIds"
    android:accessibilityFeedbackType="feedbackGeneric"
    android:notificationTimeout="100"
    android:canRetrieveWindowContent="true"
    android:settingsActivity="com.vibe.app.MainActivity" />
```

5. **Save** the file

---

## 📄 Step 6: Update strings.xml

1. Navigate to: `app` → `src` → `main` → `res` → `values`
2. **Open** `strings.xml`
3. **Replace everything** with:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">Vibe</string>
    <string name="accessibility_service_description">Vibe monitors WhatsApp messages to detect cyberbullying and provide real-time nudges. Vibe does not store or transmit your messages - all analysis happens locally on your device.</string>
</resources>
```

4. **Save** the file

---

## 📄 Step 7: Update AndroidManifest.xml

1. Navigate to: `app` → `src` → `main`
2. **Open** `AndroidManifest.xml`
3. **Find** the `<manifest>` tag at the top
4. **Add these permissions** right after the opening `<manifest>` tag (before `<application>`):

```xml
<uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE" />
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
<uses-permission android:name="android.permission.INTERNET" />
```

5. **Find** the `<application>` tag
6. **Add this service** inside the `<application>` tag (after the `<activity>` tag):

```xml
<!-- Vibe Accessibility Service -->
<service
    android:name=".accessibility.VibeAccessibilityService"
    android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE"
    android:exported="false">
    <intent-filter>
        <action android:name="android.accessibilityservice.AccessibilityService" />
    </intent-filter>
    
    <!-- Accessibility Service Configuration -->
    <meta-data
        android:name="android.accessibilityservice"
        android:resource="@xml/accessibility_service_config" />
</service>
```

**Your AndroidManifest.xml should look like this:**

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.vibe.app">

    <uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE" />
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.AppCompat">
        
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- Vibe Accessibility Service -->
        <service
            android:name=".accessibility.VibeAccessibilityService"
            android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE"
            android:exported="false">
            <intent-filter>
                <action android:name="android.accessibilityservice.AccessibilityService" />
            </intent-filter>
            
            <meta-data
                android:name="android.accessibilityservice"
                android:resource="@xml/accessibility_service_config" />
        </service>

    </application>

</manifest>
```

7. **Save** the file

---

## ✅ Step 8: Check Your Project Structure

Your project should now look like this:

```
vibe/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/vibe/app/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   └── accessibility/          ← YOU CREATED THIS
│   │   │   │       ├── VibeAccessibilityService.kt  ← YOU CREATED THIS
│   │   │   │       └── ViewNodeAnalyzer.kt          ← YOU CREATED THIS
│   │   │   ├── res/
│   │   │   │   ├── xml/                    ← YOU CREATED THIS
│   │   │   │   │   └── accessibility_service_config.xml  ← YOU CREATED THIS
│   │   │   │   └── values/
│   │   │   │       └── strings.xml         ← YOU UPDATED THIS
│   │   │   └── AndroidManifest.xml         ← YOU UPDATED THIS
```

---

## 🎯 Next Steps

1. **Build the project**: Click **Build** → **Make Project** (or press `Ctrl+F9`)
2. **Fix any errors** that appear
3. **Run the app** on your device/emulator
4. **Enable the accessibility service** in Android settings

---

## ❓ Need Help?

If you see errors:
- **"Cannot resolve symbol"** → Click **File** → **Sync Project with Gradle Files**
- **"Package does not exist"** → Make sure package name is `com.vibe.app` everywhere
- **Red underlines** → Wait for Android Studio to finish indexing (check bottom right corner)

Good luck! 🚀
