package com.vibe.app.accessibility

import android.graphics.Rect

class ViewNodeAnalyzer {
    fun analyzeMessage(text: String, direction: MessageDirection, bounds: Rect): MessageAnalysisResult {
        val sentiment: SentimentCategory
        val score: Float

        when {
            containsWord(text, listOf("kill", "die")) -> {
                sentiment = SentimentCategory.VIOLENCE
                score = -5.0f
            }
            containsWord(text, listOf("hate", "stupid", "idiot")) -> {
                sentiment = SentimentCategory.TOXIC
                score = -5.0f
            }
            containsWord(text, listOf("love", "great", "amazing", "thanks", "awesome", "cool", "nice")) -> {
                sentiment = SentimentCategory.SUPPORTIVE
                score = 5.0f
            }
            else -> {
                sentiment = SentimentCategory.NEUTRAL
                score = 0.0f
            }
        }

        return MessageAnalysisResult(
            sentiment = sentiment,
            sentimentScore = score,
            direction = direction,
            bounds = bounds
        )
    }

    private fun containsWord(text: String, words: List<String>): Boolean {
        return words.any { word -> text.contains(word, ignoreCase = true) }
    }
}

enum class MessageDirection {
    INCOMING,  // Left side - messages from others
    OUTGOING   // Right side - messages from user
}

data class MessageAnalysisResult(
    val sentiment: SentimentCategory,
    val sentimentScore: Float,
    val timestamp: Long = System.currentTimeMillis(),
    val direction: MessageDirection,
    val bounds: Rect
)

enum class SentimentCategory {
    TOXIC,
    AGGRESSIVE,
    SUPPORTIVE,
    NEUTRAL,
    EXCLUSIVE,
    VIOLENCE
}
