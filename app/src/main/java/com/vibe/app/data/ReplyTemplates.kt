package com.vibe.app.data

import com.vibe.app.accessibility.SentimentCategory

/**
 * Reply suggestions for problematic incoming messages (md/04-reply-problematic-message).
 */
object ReplyTemplates {
    fun getSuggestedReply(category: SentimentCategory, isHebrew: Boolean): String = when (category) {
        SentimentCategory.VIOLENCE,
        SentimentCategory.TOXIC -> if (isHebrew)
            "אני לא נוח לי עם השיחה הזו."
        else
            "I'm not comfortable with this conversation."
        SentimentCategory.AGGRESSIVE -> if (isHebrew)
            "בוא נדבר בלי להעליב."
        else
            "Let's talk without putting each other down."
        else -> if (isHebrew)
            "אני מעדיף/ה שנשמור על כבוד."
        else
            "I'd rather we keep things respectful."
    }
}
