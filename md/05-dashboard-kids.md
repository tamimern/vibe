# Feature Requirements: Dashboard for Kids/Teens

## 📊 Feature Overview

**Feature Name:** Teen User Dashboard  
**Priority:** P0 (Core Feature)  
**Target Release:** MVP (Phase 1)  
**Estimated Effort:** 3-4 weeks  

### Description
A comprehensive, gamified dashboard that shows teens their communication statistics, progress, achievements, and insights. Designed to be motivating, visual, and educational rather than punitive or surveillance-focused.

### User Value
- **Self-Awareness:** See patterns in their own communication
- **Motivation:** Gamification encourages positive behavior
- **Progress Tracking:** Visual representation of improvement
- **Achievement System:** Celebrate milestones and good communication
- **Learning:** Insights help them understand and improve

---

## 🎯 User Stories

### As a Teen User
```
✓ I want to see my Vibe score and level
  So that I can track my progress

✓ I want to see what actions earned me points
  So that I know what to do more of

✓ I want to see my communication trends over time
  So that I can improve

✓ I want to unlock achievements
  So that I feel rewarded for good behavior

✓ I want the dashboard to feel like a game, not a report card
  So that it's fun to check

✓ I want to see my streak and how to maintain it
  So that I stay motivated

✓ I want suggestions for how to improve
  So that I can level up faster

✓ I want to control what my parents can see
  So that I have some privacy
```

---

## 🎨 UI/UX Specifications

### Main Dashboard Layout

```
┌─────────────────────────────────────────────────────────────┐
│  [👤 Profile]              VIBE DASHBOARD          [⚙️ Settings]│
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌───────────────────────────────────────────────────────┐ │
│  │  👑 Level 5: Vibe Warrior                             │ │
│  │  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 83%           │ │
│  │  1,250 / 1,500 VP                                     │ │
│  │  🎯 250 VP to reach Vibe Champion!                    │ │
│  └───────────────────────────────────────────────────────┘ │
│                                                             │
│  ┌──────────────┬──────────────┬──────────────┬──────────┐ │
│  │  📊 Stats    │  🏆 Achieve  │  📈 Trends   │  💡 Tips │ │
│  └──────────────┴──────────────┴──────────────┴──────────┘ │
│                                                             │
│  ╔═══════════════════════════════════════════════════════╗ │
│  ║  THIS WEEK                                            ║ │
│  ╠═══════════════════════════════════════════════════════╣ │
│  ║  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ║ │
│  ║  │ +125 VP     │  │ 7 Day       │  │ 15 Positive │  ║ │
│  ║  │ Earned      │  │ Streak 🔥   │  │ Messages    │  ║ │
│  ║  │ ⬆ +12%      │  │ Keep it up! │  │ ⬆ +3 today  │  ║ │
│  ║  └─────────────┘  └─────────────┘  └─────────────┘  ║ │
│  ║                                                       ║ │
│  ║  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ║ │
│  ║  │ 3 Alerts    │  │ 2 Edited    │  │ Avg Harmony │  ║ │
│  ║  │ Avoided     │  │ Messages    │  │ 78%         │  ║ │
│  ║  │ Great job!  │  │ +30 VP      │  │ Good vibes  │  ║ │
│  ║  └─────────────┘  └─────────────┘  └─────────────┘  ║ │
│  ╚═══════════════════════════════════════════════════════╝ │
│                                                             │
│  ╔═══════════════════════════════════════════════════════╗ │
│  ║  YOUR VIBE JOURNEY                                    ║ │
│  ╠═══════════════════════════════════════════════════════╣ │
│  ║   100%│         •••─────•────────•                    ║ │
│  ║    75%│    ••••────•───•──•─•────•••                  ║ │
│  ║    50%│  ••────────────────────────────               ║ │
│  ║    25%│                                                ║ │
│  ║     0%└─────────────────────────────────>              ║ │
│  ║        Mon  Tue  Wed  Thu  Fri  Sat  Sun              ║ │
│  ╚═══════════════════════════════════════════════════════╝ │
│                                                             │
│  ╔═══════════════════════════════════════════════════════╗ │
│  ║  RECENT ACHIEVEMENTS                      [See All >] ║ │
│  ╠═══════════════════════════════════════════════════════╣ │
│  ║  🔥 7-Day Streak            Unlocked 1 day ago        ║ │
│  ║  ✨ Positive Vibes x50      Unlocked 3 days ago       ║ │
│  ║  🛡️ Alert Avoider           Unlocked 1 week ago       ║ │
│  ╚═══════════════════════════════════════════════════════╝ │
│                                                             │
│  ╔═══════════════════════════════════════════════════════╗ │
│  ║  QUICK ACTIONS                                        ║ │
│  ╠═══════════════════════════════════════════════════════╣ │
│  ║  [✨ Send Positive Message]  [📊 View Full Stats]    ║ │
│  ║  [🎯 Check Daily Goal]       [🏆 Browse Achievements]║ │
│  ╚═══════════════════════════════════════════════════════╝ │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Stats Tab

```
┌─────────────────────────────────────────────────────────────┐
│  📊 DETAILED STATISTICS                                      │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  TIME PERIOD: [This Week ▼]                          │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  COMMUNICATION QUALITY                                      │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Positive Messages    ████████████████░░ 85%  (34/40)│  │
│  │  Neutral Messages     ████████░░░░░░░░░ 10%  (4/40)  │  │
│  │  Flagged Messages     █░░░░░░░░░░░░░░░░  5%  (2/40)  │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  VIBE POINTS BREAKDOWN                                      │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Positive Messages         +85 VP                     │  │
│  │  Alerts Avoided            +45 VP                     │  │
│  │  Stickers Sent             +15 VP                     │  │
│  │  Daily Login Bonus         +35 VP (7 days)            │  │
│  │  Streak Bonus              +14 VP                     │  │
│  │  ────────────────────────────────                     │  │
│  │  TOTAL                     +194 VP this week           │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  CONVERSATION HEALTH                                        │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Private Chats (3 active)                             │  │
│  │  • Chat with Alex         ██████████ 92% harmony      │  │
│  │  • Chat with Sam          ████████░░ 78% harmony      │  │
│  │  • Chat with Jordan       █████████░ 85% harmony      │  │
│  │                                                        │  │
│  │  Group Chats (2 active)                               │  │
│  │  • Squad                  ███████░░░ 68% harmony      │  │
│  │  • Study Group            █████████░ 88% harmony      │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  YOUR IMPACT                                                │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Times you improved group vibe: 12 times              │  │
│  │  People who reacted positively: 23 reactions          │  │
│  │  Messages that made someone's day: 8 messages         │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Achievements Tab

```
┌─────────────────────────────────────────────────────────────┐
│  🏆 ACHIEVEMENTS                        12/50 Unlocked       │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  [All] [Communication] [Streaks] [Social] [Special]  │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  UNLOCKED                                                   │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  🔥 Week Warrior                    ✅ Unlocked       │  │
│  │  Maintain 7-day streak                                │  │
│  │  Unlocked: Feb 8, 2026              +25 VP            │  │
│  ├──────────────────────────────────────────────────────┤  │
│  │  ✨ Positive Vibes                  ✅ Unlocked       │  │
│  │  Send 50 positive messages                            │  │
│  │  Unlocked: Feb 5, 2026              +25 VP            │  │
│  │  Progress: ████████████████████ 50/50                 │  │
│  ├──────────────────────────────────────────────────────┤  │
│  │  🛡️ Alert Avoider                   ✅ Unlocked       │  │
│  │  Edit 10 messages after Speed Bump                    │  │
│  │  Unlocked: Feb 1, 2026              +25 VP            │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  IN PROGRESS                                                │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  💎 Diamond Words                   🔒 Locked         │  │
│  │  Send 100 zero-toxicity messages                      │  │
│  │  Progress: ████████████░░░░░░░░ 78/100                │  │
│  │  Reward: +50 VP + Diamond badge                       │  │
│  ├──────────────────────────────────────────────────────┤  │
│  │  🌟 Vibe Champion                   🔒 Locked         │  │
│  │  Reach Level 6                                        │  │
│  │  Progress: ████████████████░░░░ 1250/1500 VP          │  │
│  │  Reward: +100 VP + Champion title                     │  │
│  ├──────────────────────────────────────────────────────┤  │
│  │  🎯 Perfect Month                   🔒 Locked         │  │
│  │  30-day streak without missing a day                  │  │
│  │  Progress: ███░░░░░░░░░░░░░░░░░ 7/30 days             │  │
│  │  Reward: +100 VP + Exclusive badge                    │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Trends Tab

```
┌─────────────────────────────────────────────────────────────┐
│  📈 TRENDS & INSIGHTS                                        │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  YOUR PROGRESS OVER TIME                                    │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Vibe Points: Last 30 Days                            │  │
│  │   2000│                                    ••••       │  │
│  │   1500│                          ••••••••••           │  │
│  │   1000│               •••••••••••                     │  │
│  │    500│    •••••••••••                                │  │
│  │      0└────────────────────────────────────>          │  │
│  │        Jan 8         Jan 23        Feb 8              │  │
│  │                                                        │  │
│  │  📊 +650 VP gained this month (+52% growth)           │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  COMMUNICATION PATTERNS                                     │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Most Active Time: 6-10 PM (78% of messages)          │  │
│  │  Best Vibe Day: Saturday (avg 85% harmony)            │  │
│  │  Improvement Area: Tuesday mornings                   │  │
│  │  Strongest Platform: Group chats                      │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  WEEKLY COMPARISON                                          │
│  ┌──────────────────────────────────────────────────────┐  │
│  │              This Week    Last Week    Change         │  │
│  │  VP Earned     +125         +112       +11.6% ⬆      │  │
│  │  Positive Msg   15           12        +25.0% ⬆      │  │
│  │  Alerts         3            5         -40.0% ⬇      │  │
│  │  Avg Harmony    78%          74%       +5.4%  ⬆      │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  INSIGHTS & RECOMMENDATIONS                                 │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  💡 Your positive messages increased this week!       │  │
│  │     Keep it up to reach Level 6 faster.               │  │
│  │                                                        │  │
│  │  🎯 You're close to the "Diamond Words" achievement   │  │
│  │     Just 22 more positive messages to go!             │  │
│  │                                                        │  │
│  │  ⚠️ Your harmony score dips on Tuesday mornings       │  │
│  │     Try starting the day with positive energy!        │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Tips Tab

```
┌─────────────────────────────────────────────────────────────┐
│  💡 TIPS & CHALLENGES                                        │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  DAILY CHALLENGE                                            │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  🎯 Today's Challenge: Spread Positivity               │  │
│  │                                                        │  │
│  │  Send 3 encouraging messages to friends               │  │
│  │  Progress: ██████░░░░░░░░░░ 2/3                       │  │
│  │  Reward: +15 VP                                        │  │
│  │  Time left: 8 hours                                    │  │
│  │                                                        │  │
│  │  [Start Challenge]                                     │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  PERSONALIZED TIPS                                          │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Based on your communication patterns:                │  │
│  │                                                        │  │
│  │  ✨ Try using "I feel" statements                     │  │
│  │     Instead of "You always...", try "I feel...        │  │
│  │     when..." to express yourself without blame.       │  │
│  │                                                        │  │
│  │  💬 Pause before responding                           │  │
│  │     When you feel upset, wait 30 seconds before       │  │
│  │     typing. This prevents reactive messages.          │  │
│  │                                                        │  │
│  │  🎭 Use emojis mindfully                              │  │
│  │     Positive emojis (😊 🎉 ✨) can boost message      │  │
│  │     positivity by 20%!                                │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  COMMUNICATION LESSONS                                      │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  📚 Lesson 1: Active Listening              [Start]   │  │
│  │     Learn how to truly hear what others say           │  │
│  │     Duration: 3 min • Reward: +10 VP                  │  │
│  ├──────────────────────────────────────────────────────┤  │
│  │  📚 Lesson 2: Managing Conflict             [Start]   │  │
│  │     Turn arguments into conversations                 │  │
│  │     Duration: 5 min • Reward: +15 VP                  │  │
│  ├──────────────────────────────────────────────────────┤  │
│  │  📚 Lesson 3: Expressing Emotions           [Locked]  │  │
│  │     How to talk about feelings constructively         │  │
│  │     Unlock at Level 6                                 │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## ⚙️ Technical Requirements

### Data Aggregation

```javascript
class DashboardDataService {
  async getUserDashboardData(userId, timeRange = '7d') {
    const [
      userProgression,
      stats,
      achievements,
      trends,
      conversations
    ] = await Promise.all([
      this.getUserProgression(userId),
      this.getUserStats(userId, timeRange),
      this.getUserAchievements(userId),
      this.getUserTrends(userId, timeRange),
      this.getUserConversations(userId)
    ]);
    
    return {
      profile: {
        level: userProgression.current_level,
        level_title: userProgression.level_title,
        vibe_points: userProgression.total_vibe_points,
        progress_to_next: userProgression.progress_percent,
        points_to_next: userProgression.points_to_next_level,
        streak_days: userProgression.streak_days
      },
      
      stats_this_week: {
        vp_earned: stats.vp_earned,
        vp_change_percent: stats.vp_change_from_last_week,
        positive_messages: stats.positive_message_count,
        alerts_avoided: stats.alerts_avoided,
        messages_edited: stats.messages_edited,
        average_harmony: stats.average_harmony_score
      },
      
      achievements: {
        total_unlocked: achievements.unlocked.length,
        total_available: achievements.total,
        recently_unlocked: achievements.unlocked.slice(-3),
        in_progress: achievements.in_progress,
        next_to_unlock: achievements.closest_to_unlock
      },
      
      trends: {
        vp_chart_data: trends.vp_over_time,
        harmony_chart_data: trends.harmony_over_time,
        weekly_comparison: trends.week_over_week,
        patterns: trends.detected_patterns,
        insights: trends.personalized_insights
      },
      
      conversations: conversations.map(c => ({
        id: c.id,
        name: c.name,
        type: c.type,
        harmony_score: c.current_harmony_score,
        trend: c.harmony_trend
      }))
    };
  }
  
  async getUserStats(userId, timeRange) {
    const startDate = this.calculateStartDate(timeRange);
    
    const query = `
      SELECT 
        COUNT(*) FILTER (WHERE action_type = 'POSITIVE_MESSAGE') as positive_messages,
        COUNT(*) FILTER (WHERE action_type = 'ALERT_AVOIDED') as alerts_avoided,
        SUM(points_change) FILTER (WHERE points_change > 0) as vp_earned,
        AVG(harmony_score) as avg_harmony
      FROM vibe_point_transactions vpt
      LEFT JOIN harmony_scores hs ON vpt.user_id = hs.user_id
      WHERE vpt.user_id = $1 
        AND vpt.created_at >= $2
    `;
    
    const result = await db.query(query, [userId, startDate]);
    
    // Calculate change from previous period
    const previousPeriod = await this.getUserStats(
      userId, 
      this.calculatePreviousPeriod(timeRange)
    );
    
    return {
      ...result.rows[0],
      vp_change_from_last_week: this.calculatePercentChange(
        result.rows[0].vp_earned,
        previousPeriod.vp_earned
      )
    };
  }
}
```

### Real-Time Updates

```javascript
// WebSocket connection for live dashboard updates
socket.on('dashboard:update', (data) => {
  // Update relevant sections
  if (data.type === 'vp_earned') {
    updateVPDisplay(data.new_total);
    showVPAnimation(data.amount);
  }
  
  if (data.type === 'achievement_unlocked') {
    showAchievementNotification(data.achievement);
    refreshAchievementsList();
  }
  
  if (data.type === 'level_up') {
    showLevelUpCelebration(data.new_level);
    updateProgressBar(data.progress);
  }
  
  if (data.type === 'harmony_update') {
    updateHarmonyChart(data.conversation_id, data.new_score);
  }
});
```

### Caching Strategy

```javascript
const CACHE_CONFIG = {
  user_profile: { ttl: 300 }, // 5 minutes
  stats_current_week: { ttl: 60 }, // 1 minute
  stats_historical: { ttl: 3600 }, // 1 hour
  achievements: { ttl: 600 }, // 10 minutes
  trends: { ttl: 1800 }, // 30 minutes
};

// Invalidate cache on relevant events
const invalidateDashboardCache = (userId, eventType) => {
  switch(eventType) {
    case 'points_earned':
      cache.del(`user:${userId}:stats`);
      cache.del(`user:${userId}:profile`);
      break;
    case 'achievement_unlocked':
      cache.del(`user:${userId}:achievements`);
      break;
    case 'level_up':
      cache.del(`user:${userId}:profile`);
      break;
  }
};
```

---

## 🗄️ Database Schema

```sql
-- Dashboard snapshots for fast loading
CREATE TABLE dashboard_snapshots (
  user_id UUID PRIMARY KEY REFERENCES users(id),
  snapshot_data JSONB NOT NULL,
  last_updated TIMESTAMP DEFAULT NOW(),
  INDEX idx_last_updated (last_updated)
);

-- Refresh snapshots periodically
CREATE OR REPLACE FUNCTION refresh_dashboard_snapshot(p_user_id UUID)
RETURNS void AS $$
BEGIN
  INSERT INTO dashboard_snapshots (user_id, snapshot_data, last_updated)
  VALUES (
    p_user_id,
    jsonb_build_object(
      'vp_total', (SELECT total_vibe_points FROM user_progression WHERE user_id = p_user_id),
      'level', (SELECT current_level FROM user_progression WHERE user_id = p_user_id),
      'streak', (SELECT streak_days FROM user_progression WHERE user_id = p_user_id)
      -- ... more aggregated data
    ),
    NOW()
  )
  ON CONFLICT (user_id) 
  DO UPDATE SET 
    snapshot_data = EXCLUDED.snapshot_data,
    last_updated = NOW();
END;
$$ LANGUAGE plpgsql;

-- Daily challenges
CREATE TABLE daily_challenges (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  date DATE NOT NULL UNIQUE,
  challenge_type VARCHAR(50),
  title_he TEXT,
  title_en TEXT,
  description_he TEXT,
  description_en TEXT,
  goal_count INTEGER,
  reward_vp INTEGER,
  INDEX idx_date (date DESC)
);

-- User challenge progress
CREATE TABLE user_daily_challenges (
  user_id UUID REFERENCES users(id),
  challenge_id UUID REFERENCES daily_challenges(id),
  progress INTEGER DEFAULT 0,
  completed BOOLEAN DEFAULT false,
  completed_at TIMESTAMP,
  PRIMARY KEY (user_id, challenge_id),
  INDEX idx_user_active (user_id, completed)
);

-- Personalized tips/recommendations
CREATE TABLE user_recommendations (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID REFERENCES users(id),
  recommendation_type VARCHAR(50),
  title TEXT,
  content TEXT,
  priority INTEGER DEFAULT 0,
  shown BOOLEAN DEFAULT false,
  shown_at TIMESTAMP,
  dismissed BOOLEAN DEFAULT false,
  created_at TIMESTAMP DEFAULT NOW(),
  INDEX idx_user_pending (user_id, shown, dismissed)
);
```

---

## 🔌 API Specifications

### Get Dashboard Data

```http
GET /api/v1/dashboard

Headers:
  Authorization: Bearer {jwt_token}
  Accept-Language: he, en

Query Parameters:
  - time_range: 7d, 30d, 90d, all_time (default: 7d)
  - include: stats,achievements,trends,conversations (comma-separated)

Response 200:
{
  "user_id": "uuid",
  "profile": {
    "username": "cool_user",
    "level": 5,
    "level_title": "Vibe Warrior",
    "level_emoji": "🟠",
    "vibe_points": 1250,
    "progress_to_next": 83.3,
    "points_to_next": 250,
    "streak_days": 7,
    "longest_streak": 14
  },
  "stats_this_week": {
    "vp_earned": 125,
    "vp_change_percent": 11.6,
    "positive_messages": 15,
    "alerts_avoided": 3,
    "messages_edited": 2,
    "average_harmony": 78
  },
  "achievements": {
    "total_unlocked": 12,
    "total_available": 50,
    "recently_unlocked": [
      {
        "id": "week_warrior",
        "name": "Week Warrior",
        "description": "Maintain 7-day streak",
        "icon": "🔥",
        "unlocked_at": "2026-02-08T14:00:00Z",
        "reward_vp": 25
      }
    ],
    "in_progress": [
      {
        "id": "diamond_words",
        "name": "Diamond Words",
        "description": "Send 100 zero-toxicity messages",
        "icon": "💎",
        "progress": 78,
        "goal": 100,
        "reward_vp": 50
      }
    ]
  },
  "trends": {
    "vp_chart": [
      {"date": "2026-01-08", "vp": 600},
      {"date": "2026-01-15", "vp": 850},
      {"date": "2026-02-08", "vp": 1250}
    ],
    "weekly_comparison": {
      "this_week": {
        "vp": 125,
        "positive_messages": 15,
        "alerts": 3
      },
      "last_week": {
        "vp": 112,
        "positive_messages": 12,
        "alerts": 5
      },
      "changes": {
        "vp": "+11.6%",
        "positive_messages": "+25.0%",
        "alerts": "-40.0%"
      }
    },
    "insights": [
      {
        "type": "improvement",
        "message": "Your positive messages increased this week!"
      },
      {
        "type": "achievement_close",
        "message": "You're close to Diamond Words - just 22 more!"
      }
    ]
  },
  "daily_challenge": {
    "id": "uuid",
    "title": "Spread Positivity",
    "description": "Send 3 encouraging messages",
    "progress": 2,
    "goal": 3,
    "reward_vp": 15,
    "expires_at": "2026-02-09T00:00:00Z"
  }
}
```

### Get Achievement Details

```http
GET /api/v1/achievements

Headers:
  Authorization: Bearer {jwt_token}

Query Parameters:
  - status: all, unlocked, locked (default: all)
  - category: all, communication, streaks, social, special

Response 200:
{
  "achievements": [
    {
      "id": "week_warrior",
      "name": "Week Warrior",
      "description": "Maintain 7-day streak",
      "category": "streaks",
      "icon": "🔥",
      "reward_vp": 25,
      "unlocked": true,
      "unlocked_at": "2026-02-08T14:00:00Z",
      "progress": 7,
      "goal": 7
    }
  ],
  "stats": {
    "total_unlocked": 12,
    "total_available": 50,
    "completion_rate": 24.0
  }
}
```

---

## 🧪 Testing Requirements

### Unit Tests

```javascript
describe('Dashboard Data Service', () => {
  test('should aggregate user stats correctly', async () => {
    const userId = 'test-user';
    const data = await getDashboardData(userId, '7d');
    
    expect(data.stats_this_week.vp_earned).toBeGreaterThanOrEqual(0);
    expect(data.profile.level).toBeGreaterThanOrEqual(1);
    expect(data.profile.level).toBeLessThanOrEqual(10);
  });

  test('should calculate week-over-week change', async () => {
    const comparison = await getWeeklyComparison('test-user');
    expect(comparison.changes.vp).toMatch(/^[+-]\d+\.\d+%$/);
  });

  test('should return achievements in correct order', async () => {
    const achievements = await getUserAchievements('test-user');
    const unlocked = achievements.filter(a => a.unlocked);
    
    // Most recent first
    for (let i = 1; i < unlocked.length; i++) {
      expect(unlocked[i-1].unlocked_at >= unlocked[i].unlocked_at).toBe(true);
    }
  });
});
```

### Performance Tests

```javascript
describe('Dashboard Performance', () => {
  test('should load dashboard in < 500ms', async () => {
    const start = Date.now();
    await getDashboardData('test-user');
    const duration = Date.now() - start;
    expect(duration).toBeLessThan(500);
  });

  test('should handle concurrent dashboard requests', async () => {
    const promises = Array(100).fill().map(() =>
      getDashboardData(`user-${Math.random()}`)
    );
    await expect(Promise.all(promises)).resolves.toBeDefined();
  });
});
```

---

## 🎯 Success Metrics

### Product Metrics

**Engagement:**
- Daily dashboard views: > 70% of active users
- Average time on dashboard: 2-3 minutes
- Return rate: > 60% daily
- Feature exploration: Users check all 4 tabs > 40%

**Motivation:**
- Users with active streaks: > 35%
- Achievement unlock rate: Average 1 per week
- Challenge completion: > 50% of daily challenges completed
- Users leveling up: Average 1 level per month

**Behavior Change:**
- Positive correlation between dashboard usage and VP earning
- Users who check dashboard daily earn 30% more VP
- Dashboard users have 25% higher harmony scores

### Technical Metrics

- Dashboard load time (P95): < 500ms
- API response time: < 200ms
- Cache hit rate: > 85%
- Real-time update latency: < 100ms

---

## 🔒 Privacy & Settings

### Privacy Controls

```javascript
const PRIVACY_SETTINGS = {
  parent_visibility: {
    vp_total: true,           // Parent can see
    level: true,              // Parent can see
    streak: true,             // Parent can see
    positive_messages: true,  // Parent can see count only
    alerts_avoided: false,    // Teen can hide
    specific_conversations: false, // Never visible to parent
    achievement_details: true // Parent can see
  },
  
  leaderboard: {
    opt_in: false,            // Default opt-out
    show_real_name: false,    // Use username
    friends_only: true        // Only compare with friends
  },
  
  data_sharing: {
    anonymous_analytics: true,
    ml_training: false        // Opt-in required
  }
};
```

### User Controls

- Toggle parent visibility per metric
- Export personal data
- Delete historical data (keep last 30 days)
- Disable specific features

---

## 🔄 Future Enhancements (V2+)

1. **Social Features**
   - Share achievements with friends
   - Group challenges
   - Friend leaderboards (opt-in)

2. **Personalization**
   - Custom themes unlocked by level
   - Customizable dashboard widgets
   - Personal goals setting

3. **Advanced Analytics**
   - Communication style analysis
   - Emotion tracking
   - Conversation health predictions

4. **Rewards Integration**
   - Spend VP on avatar customization
   - Unlock special stickers/emojis
   - Real-world rewards (with parental approval)

---

## ✅ Definition of Done

- [ ] All dashboard sections implemented and functional
- [ ] Real-time updates working via WebSocket
- [ ] Charts rendering smoothly (60fps)
- [ ] Data aggregation optimized (< 500ms load)
- [ ] Privacy controls functional
- [ ] Achievements system complete
- [ ] Daily challenges generating
- [ ] Trends and insights calculating correctly
- [ ] Mobile responsive design
- [ ] Localized in Hebrew and English
- [ ] User testing shows 80%+ engagement
- [ ] Analytics tracking implemented
- [ ] Caching strategy working (>85% hit rate)

---

**Document Version:** 1.0  
**Author:** Product Team  
**Last Updated:** February 2026  
**Status:** Ready for Development
