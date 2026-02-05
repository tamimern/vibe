package com.vibe.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vibe.app.accessibility.SentimentCategory

@Composable
fun VibeMeter(
    sentiment: SentimentCategory,
    score: Int
) {
    val gradient = when {
        score > 70 -> Brush.linearGradient(
            colors = listOf(Color(0xFF60A5FA), Color(0xFF34D399)) // Electric Blue to Mint Green
        )
        else -> Brush.linearGradient(
            colors = listOf(Color(0xFFFB923C), Color(0xFFA78BFA)) // Orange to Purple
        )
    }

    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(gradient)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        if (score > 70) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Vibe Meter",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        } else {
            Text(
                text = score.toString(),
                color = Color.White
            )
        }
    }
}