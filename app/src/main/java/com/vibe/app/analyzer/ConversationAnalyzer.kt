package com.vibe.app.analyzer

import com.vibe.app.accessibility.MessageAnalysisResult

class ConversationAnalyzer {

    private val messageHistory = mutableListOf<MessageAnalysisResult>()

    fun analyze(newMessages: List<MessageAnalysisResult>): List<MessageAnalysisResult> {
        messageHistory.addAll(newMessages)
        val results = mutableListOf<MessageAnalysisResult>()

        // TODO: Implement subtle violence detection (Loud Silence, Overtaking Space, etc.)

        return results
    }
}