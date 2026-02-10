package com.vibe.app.data

import android.content.Context
import android.content.SharedPreferences

/**
 * Persists VP, level, streak, and session stats for dashboard (on-device only).
 */
class VibePreferences(context: Context) {
    private val prefs: SharedPreferences = context.getApplicationContext()
        .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var totalVibePoints: Int
        get() = prefs.getInt(KEY_TOTAL_VP, 0)
        set(value) = prefs.edit().putInt(KEY_TOTAL_VP, value).apply()

    var lastActiveDate: String
        get() = prefs.getString(KEY_LAST_ACTIVE_DATE, "") ?: ""
        set(value) = prefs.edit().putString(KEY_LAST_ACTIVE_DATE, value).apply()

    var streakDays: Int
        get() = prefs.getInt(KEY_STREAK_DAYS, 0)
        set(value) = prefs.edit().putInt(KEY_STREAK_DAYS, value).apply()

    /** VP earned in current week (reset weekly). */
    var vpThisWeek: Int
        get() = prefs.getInt(KEY_VP_THIS_WEEK, 0)
        set(value) = prefs.edit().putInt(KEY_VP_THIS_WEEK, value).apply()

    var weekStartDate: String
        get() = prefs.getString(KEY_WEEK_START, "") ?: ""
        set(value) = prefs.edit().putString(KEY_WEEK_START, value).apply()

    var sparksEarned: Int
        get() = prefs.getInt(KEY_SPARKS, 0)
        set(value) = prefs.edit().putInt(KEY_SPARKS, value).apply()

    var speedBumpsHit: Int
        get() = prefs.getInt(KEY_SPEED_BUMPS, 0)
        set(value) = prefs.edit().putInt(KEY_SPEED_BUMPS, value).apply()

    var alertsAvoided: Int
        get() = prefs.getInt(KEY_ALERTS_AVOIDED, 0)
        set(value) = prefs.edit().putInt(KEY_ALERTS_AVOIDED, value).apply()

    var positiveMessagesCount: Int
        get() = prefs.getInt(KEY_POSITIVE_MSGS, 0)
        set(value) = prefs.edit().putInt(KEY_POSITIVE_MSGS, value).apply()

    var isHebrew: Boolean
        get() = prefs.getBoolean(KEY_HEBREW, true)
        set(value) = prefs.edit().putBoolean(KEY_HEBREW, value).apply()

    /** Harmony score 0–100; affected by both sent and received messages. */
    var harmonyScore: Int
        get() = prefs.getInt(KEY_HARMONY_SCORE, 100).coerceIn(0, 100)
        set(value) = prefs.edit().putInt(KEY_HARMONY_SCORE, value.coerceIn(0, 100)).apply()

    fun addVP(amount: Int) {
        if (amount <= 0) return
        totalVibePoints += amount
        vpThisWeek += amount
        ensureWeekStarted()
    }

    private fun ensureWeekStarted() {
        val today = todayDate()
        if (weekStartDate.isEmpty()) weekStartDate = today
        if (isNewWeek(weekStartDate, today)) {
            vpThisWeek = 0
            weekStartDate = today
        }
    }

    fun recordActivity() {
        val today = todayDate()
        if (lastActiveDate.isEmpty()) {
            streakDays = 1
        } else if (lastActiveDate != today) {
            val yesterday = yesterdayDate()
            if (lastActiveDate == yesterday) streakDays += 1
            else streakDays = 1
        }
        lastActiveDate = today
    }

    fun levelInfo(): LevelInfo = VibeLevels.levelFromTotalVP(totalVibePoints)

    companion object {
        private const val PREFS_NAME = "vibe_prefs"
        private const val KEY_TOTAL_VP = "total_vp"
        private const val KEY_LAST_ACTIVE_DATE = "last_active_date"
        private const val KEY_STREAK_DAYS = "streak_days"
        private const val KEY_VP_THIS_WEEK = "vp_this_week"
        private const val KEY_WEEK_START = "week_start"
        private const val KEY_SPARKS = "sparks"
        private const val KEY_SPEED_BUMPS = "speed_bumps"
        private const val KEY_ALERTS_AVOIDED = "alerts_avoided"
        private const val KEY_POSITIVE_MSGS = "positive_msgs"
        private const val KEY_HEBREW = "is_hebrew"
        private const val KEY_HARMONY_SCORE = "harmony_score"

        fun todayDate(): String = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US).format(java.util.Date())
        fun yesterdayDate(): String {
            val c = java.util.Calendar.getInstance()
            c.add(java.util.Calendar.DAY_OF_YEAR, -1)
            return java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US).format(c.time)
        }
        fun isNewWeek(weekStart: String, today: String): Boolean {
            if (weekStart.isEmpty()) return true
            val fmt = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US)
            val start = try { fmt.parse(weekStart)?.time ?: 0L } catch (_: Exception) { return true }
            val end = try { fmt.parse(today)?.time ?: 0L } catch (_: Exception) { return false }
            val days = (end - start) / (24 * 60 * 60 * 1000)
            return days >= 7
        }
    }
}
