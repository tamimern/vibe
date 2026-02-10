package com.vibe.app.data

/**
 * Vibe Levels & VP (from md/02-vibe-levels-climbing.md).
 * Level thresholds and titles.
 */
object VibeLevels {
    val LEVEL_THRESHOLDS = listOf(
        0,     // Level 1: Vibe Rookie
        100,   // Level 2: Vibe Explorer
        300,   // Level 3: Vibe Seeker
        600,   // Level 4: Vibe Builder
        1000,  // Level 5: Vibe Warrior
        1500,  // Level 6: Vibe Champion
        2200,  // Level 7: Vibe Master
        3000,  // Level 8: Vibe Legend
        4000,  // Level 9: Vibe Guardian
        5500   // Level 10: Vibe Sage
    )

    val LEVEL_TITLES_EN = listOf(
        "Vibe Rookie", "Vibe Explorer", "Vibe Seeker", "Vibe Builder", "Vibe Warrior",
        "Vibe Champion", "Vibe Master", "Vibe Legend", "Vibe Guardian", "Vibe Sage"
    )

    val LEVEL_TITLES_HE = listOf(
        "רוקי ויב", "חוקר ויב", "מחפש ויב", "בונה ויב", "לוחם ויב",
        "אלוף ויב", "מאסטר ויב", "אגדה", "שומר ויב", "חכם ויב"
    )

    val LEVEL_EMOJIS = listOf(
        "⚪", "🔵", "🟢", "🟡", "🟠", "🔴", "🟣", "🌟", "💎", "👑"
    )

    fun levelFromTotalVP(totalVP: Int): LevelInfo {
        var level = 1
        var threshold = 0
        var nextThreshold = LEVEL_THRESHOLDS.getOrNull(1) ?: 100
        for (i in LEVEL_THRESHOLDS.indices.reversed()) {
            if (totalVP >= LEVEL_THRESHOLDS[i]) {
                level = i + 1
                threshold = LEVEL_THRESHOLDS[i]
                nextThreshold = LEVEL_THRESHOLDS.getOrNull(i + 1) ?: threshold
                break
            }
        }
        val pointsInLevel = totalVP - threshold
        val pointsNeededForNext = nextThreshold - threshold
        val progress = if (pointsNeededForNext > 0) (pointsInLevel.toFloat() / pointsNeededForNext).coerceIn(0f, 1f) else 1f
        val pointsToNext = (nextThreshold - totalVP).coerceAtLeast(0)
        return LevelInfo(
            level = level,
            titleEn = LEVEL_TITLES_EN.getOrNull(level - 1) ?: "Vibe",
            titleHe = LEVEL_TITLES_HE.getOrNull(level - 1) ?: "ויב",
            emoji = LEVEL_EMOJIS.getOrNull(level - 1) ?: "⚪",
            totalVP = totalVP,
            threshold = threshold,
            nextThreshold = nextThreshold,
            progress = progress,
            pointsToNext = pointsToNext
        )
    }
}

data class LevelInfo(
    val level: Int,
    val titleEn: String,
    val titleHe: String,
    val emoji: String,
    val totalVP: Int,
    val threshold: Int,
    val nextThreshold: Int,
    val progress: Float,
    val pointsToNext: Int
)

/** VP earning rules (simplified for on-device). */
object VibePoints {
    const val POSITIVE_MESSAGE = 5
    const val ALERT_AVOIDED = 15
    const val HELPFUL_RESPONSE = 10
    const val SUPPORTIVE_MESSAGE = 5
    const val LEVEL_UP_BONUS = 50
}
