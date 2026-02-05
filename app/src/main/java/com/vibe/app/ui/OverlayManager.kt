package com.vibe.app.ui

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

    fun showVibeMeter(sentiment: SentimentCategory, score: Int) {
        if (vibeMeterView != null) {
            // Update existing Vibe Meter
            vibeMeterView?.setContent {
                VibeMeter(sentiment, score)
            }
            return
        }

        vibeMeterView = ComposeView(context).apply {
            setViewTreeLifecycleOwner(lifecycle)
            setViewTreeSavedStateRegistryOwner(lifecycle)
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                VibeMeter(sentiment, score)
            }
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.END
            x = 16
            y = 16
        }

        windowManager.addView(vibeMeterView, params)
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
            text = "שימו לב, ההודעה מסוכנת",
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
