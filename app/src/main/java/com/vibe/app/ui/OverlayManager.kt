package com.vibe.app.ui

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OverlayManager(private val context: Context) {

    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var composeView: ComposeView? = null
    private var vibeMeterView: ComposeView? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var lifecycle: com.vibe.app.ui.Lifecycle? = null
    private var isDashboardOpen by mutableStateOf(false)
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
            showDashboard(currentScore)
        } else {
            hideDashboard()
        }
    }

    private fun showDashboard(score: Int) {
        hideOverlay() // Hide any other overlays before showing the dashboard
        composeView = ComposeView(context).apply {
            setViewTreeLifecycleOwner(lifecycle)
            setViewTreeSavedStateRegistryOwner(lifecycle)
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                VibeDataCard(score)
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


    fun showOverlay(analysisResult: MessageAnalysisResult) {
        hideOverlay() // Hide any existing overlay

        composeView = ComposeView(context).apply {
            setViewTreeLifecycleOwner(lifecycle)
            setViewTreeSavedStateRegistryOwner(lifecycle)
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                VibeOverlay(analysisResult)
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

    fun showSpeedBump(onEdit: () -> Unit, onSend: () -> Unit) {
        hideOverlay()

        composeView = ComposeView(context).apply {
            setViewTreeLifecycleOwner(lifecycle)
            setViewTreeSavedStateRegistryOwner(lifecycle)
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                SpeedBumpOverlay(onEdit, onSend)
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
fun VibeOverlay(analysisResult: MessageAnalysisResult) {
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
        Box(
            modifier = Modifier
                .background(Color.White, shape = CircleShape)
                .padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = Color.Black
            )
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
fun SpeedBumpOverlay(onEdit: () -> Unit, onSend: () -> Unit) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFFB923C), Color(0xFFB652F3)) // Orange to Purple
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(gradient, shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "היי, בטוח שזה מה שרצית להגיד?",
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row {
            Button(onClick = onEdit) {
                Text("ערוך הודעה")
            }
            Button(onClick = onSend, modifier = Modifier.padding(start = 8.dp)) {
                Text("שלח בכל זאת")
            }
        }
    }
}


@Composable
fun VibeDataCard(score: Int) {
    val (title, color) = when {
        score >= 80 -> "Positive Vibes" to Color(0xFF34D399)
        score >= 60 -> "Good Vibes" to Color(0xFF60A5FA)
        score >= 40 -> "Getting Heated" to Color(0xFFFB923C)
        else -> "Critical" to Color(0xFFEF4444)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Black.copy(alpha = 0.8f), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(text = title, color = color, modifier = Modifier.padding(bottom = 8.dp))
        Text(text = "Sparks Earned: 0", color = Color.White, modifier = Modifier.padding(bottom = 4.dp))
        Text(text = "Speed Bumps Hit: 0", color = Color.White, modifier = Modifier.padding(bottom = 16.dp))
        Row {
            Button(onClick = { /* Mute alerts */ }) {
                Text("Mute Alerts")
            }
            Button(onClick = { /* Switch language */ }, modifier = Modifier.padding(start = 8.dp)) {
                Text("Switch Language")
            }
        }
    }
}
