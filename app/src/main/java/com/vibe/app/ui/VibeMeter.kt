package com.vibe.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun VibeMeter(
    score: Int,
    onClick: () -> Unit
) {
    val (color, _) = when {
        score >= 80 -> Color(0xFF34D399) to Brush.linearGradient(
            colors = listOf(Color(0xFF34D399), Color(0xFF60A5FA))
        )
        score >= 60 -> Color(0xFF60A5FA) to Brush.linearGradient(
            colors = listOf(Color(0xFF60A5FA), Color(0xFF34D399))
        )
        score >= 40 -> Color(0xFFFB923C) to Brush.linearGradient(
            colors = listOf(Color(0xFFFB923C), Color(0xFFB652F3))
        )
        else -> Color(0xFFEF4444) to Brush.linearGradient(
            colors = listOf(Color(0xFFEF4444), Color(0xFFFB923C))
        )
    }

    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .background(Color.Black.copy(alpha = 0.5f))
            .border(1.dp, color, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = score / 100f,
            modifier = Modifier.size(64.dp),
            color = color,
            strokeWidth = 2.dp
        )
        Text(
            text = score.toString(),
            color = Color.White
        )
    }
}
