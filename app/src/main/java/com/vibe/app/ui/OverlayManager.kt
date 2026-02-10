package com.vibe.app.ui

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.vibe.app.accessibility.MessageAnalysisResult
import com.vibe.app.accessibility.SentimentCategory
import com.vibe.app.data.HarmonyScore
import com.vibe.app.data.RephraseSuggestions
import com.vibe.app.data.VibePoints
import com.vibe.app.data.VibePreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OverlayManager(
    private val context: Context,
    private val preferences: VibePreferences = VibePreferences(context)
) {

    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var composeView: ComposeView? = null
    private var vibeMeterView: ComposeView? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var lifecycle: com.vibe.app.ui.Lifecycle? = null
    private var isDashboardOpen by mutableStateOf(false)
    private val showDashboardCard = mutableStateOf(false) // true = VibeDataCard, false = expanded menu
    private var currentScore by mutableStateOf(100)

    init {
        lifecycle = com.vibe.app.ui.Lifecycle()
        lifecycle?.performRestore()
        lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    fun showMonitoringActive() {
        hideOverlay() // Hide any existing overlay

        composeView = ComposeView(context).apply {
            setViewTreeLifecycleOwner(lifecycle)
            setViewTreeSavedStateRegistryOwner(lifecycle)
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MonitoringActiveOverlay()
            }
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.CENTER
        }

        windowManager.addView(composeView, params)
        lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_START)

        coroutineScope.launch {
            delay(2000) // 2 seconds
            hideOverlay()
        }
    }

    fun showVibeMeter(score: Int) {
        currentScore = score
        if (vibeMeterView == null) {
            vibeMeterView = ComposeView(context).apply {
                setViewTreeLifecycleOwner(lifecycle)
                setViewTreeSavedStateRegistryOwner(lifecycle)
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            }
            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            ).apply {
                gravity = Gravity.TOP or Gravity.END
                x = 16
                y = 16
            }
            windowManager.addView(vibeMeterView, params)
        }
        
        vibeMeterView?.setContent {
            VibeMeter(score) {
                toggleDashboard()
            }
        }
    }

    private fun toggleDashboard() {
        isDashboardOpen = !isDashboardOpen
        if (isDashboardOpen) {
            showDashboardCard.value = false
            showExpandedBubbleMenu(currentScore)
        } else {
            hideDashboard()
        }
    }

    private fun showExpandedBubbleMenu(score: Int) {
        hideOverlay()
        composeView = ComposeView(context).apply {
            setViewTreeLifecycleOwner(lifecycle)
            setViewTreeSavedStateRegistryOwner(lifecycle)
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                if (showDashboardCard.value) {
                    VibeDataCard(
                        score = currentScore,
                        preferences = preferences,
                        onBack = { showDashboardCard.value = false },
                        onMute = { },
                        onSwitchLanguage = { preferences.isHebrew = !preferences.isHebrew }
                    )
                } else {
                    VibeBubbleExpanded(
                        score = currentScore,
                        isHebrew = preferences.isHebrew,
                        onDashboard = { showDashboardCard.value = true },
                        onTodaySummary = { },
                        onSendPositive = { },
                        onSettings = { },
                        onMinimize = { hideDashboard() }
                    )
                }
            }
        }
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.END
            x = 16
            y = 16
        }
        windowManager.addView(composeView, params)
        lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    private fun showDashboard(score: Int) {
        hideOverlay()
        composeView = ComposeView(context).apply {
            setViewTreeLifecycleOwner(lifecycle)
            setViewTreeSavedStateRegistryOwner(lifecycle)
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                VibeDataCard(
                    score = score,
                    preferences = preferences,
                    onBack = { hideDashboard() },
                    onMute = { },
                    onSwitchLanguage = { preferences.isHebrew = !preferences.isHebrew }
                )
            }
        }
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.BOTTOM
        }
        windowManager.addView(composeView, params)
        lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    private fun hideDashboard() {
        hideOverlay()
    }


    fun showOverlay(analysisResult: MessageAnalysisResult, onSuggestReply: () -> Unit) {
        hideOverlay() // Hide any existing overlay

        composeView = ComposeView(context).apply {
            setViewTreeLifecycleOwner(lifecycle)
            setViewTreeSavedStateRegistryOwner(lifecycle)
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                VibeOverlay(analysisResult, onSuggestReply)
            }
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.BOTTOM
        }

        windowManager.addView(composeView, params)
        lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_START)

        coroutineScope.launch {
            delay(5000) // 5 seconds
            hideOverlay()
        }
    }

    fun showSpeedBump(
        originalText: String,
        category: SentimentCategory,
        onEdit: () -> Unit,
        onSend: () -> Unit
    ) {
        hideOverlay()
        val suggestion = RephraseSuggestions.getSuggestion(originalText, category, preferences.isHebrew)
        val categoryLabel = RephraseSuggestions.getCategoryLabel(category, preferences.isHebrew)
        val onEditWithVP = {
            preferences.addVP(VibePoints.ALERT_AVOIDED)
            preferences.alertsAvoided++
            onEdit()
        }

        composeView = ComposeView(context).apply {
            setViewTreeLifecycleOwner(lifecycle)
            setViewTreeSavedStateRegistryOwner(lifecycle)
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                SpeedBumpOverlay(
                    originalText = originalText,
                    categoryLabel = categoryLabel,
                    suggestion = suggestion,
                    onEdit = onEditWithVP,
                    onSend = onSend,
                    isHebrew = preferences.isHebrew
                )
            }
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.BOTTOM
        }

        windowManager.addView(composeView, params)
        lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    fun showVibeSpark() {
        hideOverlay()

        composeView = ComposeView(context).apply {
            setViewTreeLifecycleOwner(lifecycle)
            setViewTreeSavedStateRegistryOwner(lifecycle)
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                VibeSparkOverlay()
            }
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.BOTTOM
        }

        windowManager.addView(composeView, params)
        lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_START)

        coroutineScope.launch {
            delay(3000) // 3 seconds
            hideOverlay()
        }
    }

    fun hideOverlay() {
        composeView?.let {
            lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
            windowManager.removeView(it)
            composeView = null
        }
    }

    fun hideVibeMeter() {
        vibeMeterView?.let {
            windowManager.removeView(it)
            vibeMeterView = null
        }
    }
}

@Composable
fun MonitoringActiveOverlay() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.Black.copy(alpha = 0.7f), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Vibe Monitoring Active",
            color = Color.White
        )
    }
}

@Composable
fun VibeOverlay(analysisResult: MessageAnalysisResult, onSuggestReply: () -> Unit) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFB652F3), Color(0xFFFB923C)) // Purple to Orange
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(gradient, shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Vibe Check",
            tint = Color.White
        )
        Text(
            text = "Detected: ${analysisResult.sentiment}",
            color = Color.White
        )
        Button(onClick = onSuggestReply) {
            Text("Suggest Reply")
        }
    }
}

@Composable
fun VibeSparkOverlay() {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF34D399), Color(0xFF60A5FA)) // Mint Green to Electric Blue
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(gradient, shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Vibe Spark",
            tint = Color.White
        )
        Text(
            text = "Nice one! Keep the good vibes going.",
            color = Color.White
        )
    }
}

@Composable
fun SpeedBumpOverlay(
    originalText: String,
    categoryLabel: String,
    suggestion: String,
    onEdit: () -> Unit,
    onSend: () -> Unit,
    isHebrew: Boolean
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFFEF3C7), Color(0xFFFED7AA))
    )
    val title = if (isHebrew) "היי, בטוח שזה מה שרצית להגיד?" else "Wait a moment..."
    val editLabel = if (isHebrew) "ערוך הודעה (+15 VP)" else "Edit Message (+15 VP)"
    val sendLabel = if (isHebrew) "שלח בכל זאת" else "Send Anyway"
    val detectedLabel = if (isHebrew) "ההודעה עשויה:" else "This message might:"
    val tryInsteadLabel = if (isHebrew) "💡 אולי נסה במקום:" else "💡 Maybe try instead:"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(gradient, shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "⏸️ $title",
            color = Color(0xFF92400E),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "$detectedLabel $categoryLabel",
            color = Color(0xFF78350F),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = tryInsteadLabel,
            color = Color(0xFF78350F),
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )
        Text(
            text = "\"$suggestion\"",
            color = Color(0xFF1E40AF),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row {
            Button(onClick = onEdit, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))) {
                Text(editLabel)
            }
            Button(onClick = onSend, modifier = Modifier.padding(start = 8.dp)) {
                Text(sendLabel)
            }
        }
    }
}


@Composable
fun VibeBubbleExpanded(
    score: Int,
    isHebrew: Boolean,
    onDashboard: () -> Unit,
    onTodaySummary: () -> Unit,
    onSendPositive: () -> Unit,
    onSettings: () -> Unit,
    onMinimize: () -> Unit
) {
    val statusEn = HarmonyScore.statusEn(score)
    val statusHe = HarmonyScore.statusHe(score)
    val status = if (isHebrew) statusHe else statusEn
    val emoji = HarmonyScore.emoji(score)
    val color = HarmonyScore.color(score)
    val dashTitle = if (isHebrew) "הדשבורד שלי" else "My Dashboard"
    val todayTitle = if (isHebrew) "מה היה היום" else "Today's Summary"
    val sendTitle = if (isHebrew) "שלח משהו מגניב" else "Send Something Cool"
    val settingsTitle = if (isHebrew) "הגדרות" else "Settings"
    val minimizeTitle = if (isHebrew) "מזער" else "Minimize"
    val vibeTitle = if (isHebrew) "ויב" else "Vibe"

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White.copy(alpha = 0.95f), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = vibeTitle, color = Color.Black)
            Text(text = "×", color = Color.Gray, modifier = Modifier.clickable { onMinimize() })
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(color.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "$score%", color = color, style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
            Text(text = "$emoji $status", color = Color.DarkGray)
        }
        Column(modifier = Modifier.padding(top = 8.dp)) {
            Row(modifier = Modifier.clickable { onDashboard() }, verticalAlignment = Alignment.CenterVertically) {
                Text("📊", modifier = Modifier.padding(end = 8.dp))
                Text(dashTitle, color = Color.Black)
            }
            Row(modifier = Modifier.clickable { onTodaySummary() }.padding(top = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("🌟", modifier = Modifier.padding(end = 8.dp))
                Text(todayTitle, color = Color.Black)
            }
            Row(modifier = Modifier.clickable { onSendPositive() }.padding(top = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("✨", modifier = Modifier.padding(end = 8.dp))
                Text(sendTitle, color = Color.Black)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "⚙️ $settingsTitle", color = Color.Gray, modifier = Modifier.clickable { onSettings() })
            Text(text = "━ $minimizeTitle", color = Color.Gray, modifier = Modifier.clickable { onMinimize() })
        }
    }
}

@Composable
fun VibeDataCard(
    score: Int,
    preferences: VibePreferences,
    onBack: () -> Unit,
    onMute: () -> Unit,
    onSwitchLanguage: () -> Unit
) {
    val (title, color) = when {
        score >= 90 -> (if (preferences.isHebrew) "מעולה!" else "Excellent!") to Color(0xFF10b981)
        score >= 70 -> (if (preferences.isHebrew) "טוב" else "Good") to Color(0xFF06b6d4)
        score >= 50 -> (if (preferences.isHebrew) "בסדר" else "OK") to Color(0xFFf59e0b)
        score >= 30 -> (if (preferences.isHebrew) "מתוח" else "Tense") to Color(0xFFef4444)
        else -> (if (preferences.isHebrew) "קריטי" else "Critical") to Color(0xFF991b1b)
    }
    val levelInfo = preferences.levelInfo()
    val levelTitle = if (preferences.isHebrew) levelInfo.titleHe else levelInfo.titleEn
    val sparksLabel = if (preferences.isHebrew) "ניצוצות" else "Sparks Earned"
    val bumpsLabel = if (preferences.isHebrew) "פסי האטה" else "Speed Bumps Hit"
    val muteLabel = if (preferences.isHebrew) "השתק התראות" else "Mute Alerts"
    val langLabel = if (preferences.isHebrew) "החלף שפה" else "Switch Language"
    val backLabel = if (preferences.isHebrew) "חזרה" else "Back"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Black.copy(alpha = 0.8f), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Button(onClick = onBack, modifier = Modifier.padding(bottom = 8.dp)) {
            Text(backLabel)
        }
        Text(text = "$title", color = color, modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text = "${levelInfo.emoji} Level ${levelInfo.level}: $levelTitle",
            color = Color.White,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "${levelInfo.totalVP} / ${levelInfo.nextThreshold} VP (${(levelInfo.progress * 100).toInt()}%)",
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(text = "$sparksLabel: ${preferences.sparksEarned}", color = Color.White, modifier = Modifier.padding(bottom = 4.dp))
        Text(text = "$bumpsLabel: ${preferences.speedBumpsHit}", color = Color.White, modifier = Modifier.padding(bottom = 4.dp))
        Text(text = "VP this week: ${preferences.vpThisWeek}", color = Color.White, modifier = Modifier.padding(bottom = 4.dp))
        Text(text = "Streak: ${preferences.streakDays} days", color = Color.White, modifier = Modifier.padding(bottom = 16.dp))
        Row {
            Button(onClick = onMute) {
                Text(muteLabel)
            }
            Button(onClick = onSwitchLanguage, modifier = Modifier.padding(start = 8.dp)) {
                Text(langLabel)
            }
        }
    }
}
