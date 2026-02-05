package com.vibe.app.accessibility

import android.graphics.Rect

class ViewNodeAnalyzer {
    fun analyzeMessage(text: String, direction: MessageDirection, bounds: Rect): MessageAnalysisResult {
        // Placeholder for sentiment analysis logic
        val sentiment: SentimentCategory
        val score: Float

        when {
            text.contains("kill", ignoreCase = true) -> {
                sentiment = SentimentCategory.VIOLENCE
                score = 0.1f // Low score for violence
            }
            text.contains("hate", ignoreCase = true) -> {
                sentiment = SentimentCategory.TOXIC
                score = 0.3f // Medium-low score for toxic
            }
            else -> {
                sentiment = SentimentCategory.NEUTRAL
                score = 0.8f // Higher score for neutral
            }
        }

        return MessageAnalysisResult(
            sentiment = sentiment,
            sentimentScore = score,
            direction = direction,
            bounds = bounds
        )
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
    EXCLUSIVE,
    VIOLENCE
}
