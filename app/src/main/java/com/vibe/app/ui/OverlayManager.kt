package com.vibe.app.ui

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.vibe.app.accessibility.MessageAnalysisResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OverlayManager(private val context: Context) {

    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var composeView: ComposeView? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var lifecycle: com.vibe.app.ui.Lifecycle? = null

    fun showMonitoringActive() {
        if (composeView != null) {
            return // Overlay is already shown
        }

        val newLifecycle = com.vibe.app.ui.Lifecycle()
        newLifecycle.performRestore()
        newLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        composeView = ComposeView(context).apply {
            setViewTreeLifecycleOwner(newLifecycle)
            setViewTreeSavedStateRegistryOwner(newLifecycle)
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MonitoringActiveOverlay()
            }
        }
        lifecycle = newLifecycle

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
        newLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)

        coroutineScope.launch {
            delay(2000) // 2 seconds
            hideOverlay()
        }
    }

    fun showOverlay(analysisResult: MessageAnalysisResult) {
        if (composeView != null) {
            return // Overlay is already shown
        }

        val newLifecycle = com.vibe.app.ui.Lifecycle()
        newLifecycle.performRestore()
        newLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        composeView = ComposeView(context).apply {
            setViewTreeLifecycleOwner(newLifecycle)
            setViewTreeSavedStateRegistryOwner(newLifecycle)
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                VibeOverlay(analysisResult)
            }
        }
        lifecycle = newLifecycle

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = analysisResult.bounds.left
            y = analysisResult.bounds.top - 100 // Position above the message
        }

        windowManager.addView(composeView, params)
        newLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    fun hideOverlay() {
        composeView?.let {
            lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
            windowManager.removeView(it)
            lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            composeView = null
            lifecycle = null
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
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.Black.copy(alpha = 0.7f), shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(
            text = "Vibe Check: ${analysisResult.sentiment}",
            color = Color.White
        )
    }
}