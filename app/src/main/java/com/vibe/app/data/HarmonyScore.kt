package com.vibe.app.data

import androidx.compose.ui.graphics.Color

/**
 * Harmony Score bands and display (from md/01-harmony-score.md, md/07-floating-bubble.md).
 */
object HarmonyScore {
    const val NEUTRAL_BASE = 75

    fun band(score: Int): ScoreBand = when {
        score >= 90 -> ScoreBand.Excellent
        score >= 70 -> ScoreBand.Good
        score >= 50 -> ScoreBand.Okay
        score >= 30 -> ScoreBand.Tense
        else -> ScoreBand.Critical
    }

    fun statusEn(score: Int): String = when (band(score)) {
        ScoreBand.Excellent -> "Excellent!"
        ScoreBand.Good -> "Good"
        ScoreBand.Okay -> "OK"
        ScoreBand.Tense -> "Tense"
        ScoreBand.Critical -> "Critical"
    }

    fun statusHe(score: Int): String = when (band(score)) {
        ScoreBand.Excellent -> "מעולה!"
        ScoreBand.Good -> "טוב"
        ScoreBand.Okay -> "בסדר"
        ScoreBand.Tense -> "מתוח"
        ScoreBand.Critical -> "קריטי"
    }

    fun emoji(score: Int): String = when (band(score)) {
        ScoreBand.Excellent -> "🟢"
        ScoreBand.Good -> "🔵"
        ScoreBand.Okay -> "🟠"
        ScoreBand.Tense -> "🔴"
        ScoreBand.Critical -> "🔴"
    }

    /** Border/glow color per spec. */
    fun color(score: Int): Color = when (band(score)) {
        ScoreBand.Excellent -> Color(0xFF10b981)
        ScoreBand.Good -> Color(0xFF06b6d4)
        ScoreBand.Okay -> Color(0xFFf59e0b)
        ScoreBand.Tense -> Color(0xFFef4444)
        ScoreBand.Critical -> Color(0xFF991b1b)
    }
}

enum class ScoreBand {
    Excellent, Good, Okay, Tense, Critical
}
