package com.vibe.app.accessibility

import android.graphics.Rect

class ViewNodeAnalyzer {
    fun analyzeMessage(text: String, direction: MessageDirection, bounds: Rect): MessageAnalysisResult {
        // Placeholder for sentiment analysis logic
        val sentiment = if (text.contains("hate", ignoreCase = true)) {
            SentimentCategory.TOXIC
        } else {
            SentimentCategory.NEUTRAL
        }
        return MessageAnalysisResult(
            sentiment = sentiment,
            sentimentScore = if (sentiment == SentimentCategory.TOXIC) 0.9f else 0.5f,
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
    EXCLUSIVE
}
