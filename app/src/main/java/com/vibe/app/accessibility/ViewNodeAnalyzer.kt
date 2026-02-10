package com.vibe.app.accessibility

import android.graphics.Rect

/**
 * Analyzer for chat message sentiment. Supports English and Hebrew per product spec.
 *
 * NOTE: This uses keyword matching only (no AI). For context-aware detection (e.g. "The kill"
 * in gaming vs "I will kill you") you would need to call a backend that uses the project's
 * violence-detector.ts (OpenAI) or an on-device ML model.
 */
class ViewNodeAnalyzer {
    fun analyzeMessage(text: String, direction: MessageDirection, bounds: Rect): MessageAnalysisResult {
        val sentiment: SentimentCategory
        val score: Float
        val normalized = text.trim()

        when {
            // Violence (substring so we don't miss "kill you", "die" in real messages)
            containsWord(normalized, listOf("kill", "die", "להרוג", "אהרוג", "הרג", "להכות", "ארצח")) -> {
                sentiment = SentimentCategory.VIOLENCE
                score = -5.0f
            }
            // Toxic / belittling (substring so "hate", "hateee", "I hate you" all count)
            containsWord(normalized, listOf("hate", "stupid", "idiot", "שנאה", "שונא", "שונאת", "מטומטם", "טיפש", "מפגר", "דפוק")) -> {
                sentiment = SentimentCategory.TOXIC
                score = -5.0f
            }
            // Supportive / positive (substring so "loveee", "luv" still count)
            containsWord(normalized, listOf(
                "love", "great", "amazing", "thanks", "awesome", "cool", "nice", "good", "wonderful",
                "אהבה", "אוהב", "אוהבת", "תודה", "מעולה", "מדהים", "אחלה", "סופר", "מצוין", "נהדר", "כיף", "מקסים"
            )) -> {
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

    /** Substring match (legacy behavior for short words). */
    private fun containsWord(text: String, words: List<String>): Boolean {
        return words.any { word -> text.contains(word, ignoreCase = true) }
    }

    /**
     * Match whole words only to reduce false positives (e.g. "skill" vs "kill", "loveee" vs "love").
     * For Latin script we use word boundaries; for Hebrew we check not followed by letter.
     */
    private fun containsWholeWord(text: String, words: List<String>): Boolean {
        if (text.isBlank()) return false
        return words.any { word ->
            if (word.isEmpty()) return@any false
            val idx = text.indexOf(word, ignoreCase = true)
            if (idx < 0) return@any false
            val before = if (idx == 0) null else text.getOrNull(idx - 1)
            val after = text.getOrNull(idx + word.length)
            val isLetter: (Char?) -> Boolean = { c -> c != null && (c.isLetter() || c in '\u0590'..'\u05FF') }
            !isLetter(before) && !isLetter(after)
        }
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
