package com.vibe.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vibe.app.data.HarmonyScore

@Composable
fun VibeMeter(
    score: Int,
    onClick: () -> Unit
) {
    val color = HarmonyScore.color(score)
    val emoji = HarmonyScore.emoji(score)

    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .background(Color.Black.copy(alpha = 0.5f))
            .border(2.dp, color, CircleShape)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = score.toString(),
                color = Color.White,
                style = androidx.compose.material3.MaterialTheme.typography.titleLarge
            )
            Text(
                text = emoji,
                color = Color.White
            )
        }
    }
}
