package com.vibe.app.data

import com.vibe.app.accessibility.SentimentCategory

/**
 * Local rephrase suggestions for Speed Bump (md/03-speed-bump-suggest-rephrase).
 * Used when we detect toxic/violence - suggest a gentler phrasing.
 */
object RephraseSuggestions {
    fun getSuggestion(original: String, category: SentimentCategory, isHebrew: Boolean): String {
        return when (category) {
            SentimentCategory.VIOLENCE -> if (isHebrew)
                "בוא ננמיך טונים ונדבר בצורה רגועה."
            else
                "Let's take it down a notch and talk calmly."
            SentimentCategory.TOXIC -> if (isHebrew)
                "אולי לא הסברתי טוב, בוא ננסה שוב?"
            else
                "Maybe I didn't explain well—want to try again?"
            SentimentCategory.AGGRESSIVE -> if (isHebrew)
                "אני כועס/ה כרגע, אבל אני רוצה שנבין אחד את השני."
            else
                "I'm upset right now, but I want us to understand each other."
            else -> if (isHebrew)
                "בוא ננסח את זה בצורה שתעזור לשיחה."
            else
                "Let's put it in a way that helps the conversation."
        }
    }

    /** Short label for "this message might: ..." */
    fun getCategoryLabel(category: SentimentCategory, isHebrew: Boolean): String = when (category) {
        SentimentCategory.VIOLENCE -> if (isHebrew) "אלימות / איום" else "Violence / threat"
        SentimentCategory.TOXIC -> if (isHebrew) "הקטנה / עלבון" else "Belittling / hurtful"
        SentimentCategory.AGGRESSIVE -> if (isHebrew) "תוקפנות" else "Aggression"
        else -> if (isHebrew) "שפה פוגענית" else "Hurtful language"
    }
}
