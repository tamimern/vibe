# Feature Requirements: Dashboard for Parents

## 👨‍👩‍👧 Feature Overview

**Feature Name:** Parent/Guardian Dashboard  
**Priority:** P1 (Important Feature)  
**Target Release:** Phase 2  
**Estimated Effort:** 3-4 weeks  

### Description
A comprehensive monitoring and insight dashboard for parents/guardians to track their child's digital communication health, view trends, receive alerts, and access educational resources. Designed to balance safety monitoring with respect for teen privacy.

### User Value
- **Peace of Mind:** Parents can see their child is communicating healthily
- **Early Warning:** Alerts for concerning patterns before they escalate
- **Educational:** Insights help parents understand teen digital behavior
- **Collaborative:** Tools for parent-child conversations about online safety
- **Non-Invasive:** Respects teen privacy while ensuring safety

---

## 🎯 User Stories

### As a Parent
```
✓ I want to see my child's overall communication health
  So that I know they're safe online

✓ I want to see trends over time, not individual messages
  So that I respect their privacy while staying informed

✓ I want alerts for serious concerns
  So that I can intervene when truly necessary

✓ I want educational insights
  So that I can have informed conversations with my child

✓ I want to set appropriate boundaries
  So that the tool supports my family values

✓ I want to see positive progress
  So that I can encourage and celebrate improvements

✓ I want conversation starters
  So that I can talk to my child about digital wellbeing

✓ I want to control my access level
  So that my child has appropriate privacy as they mature
```

---

## 🎨 UI/UX Specifications

### Main Parent Dashboard

```
┌─────────────────────────────────────────────────────────────┐
│  [VIBE Parent Portal]                         [Settings ⚙️] │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  CHILDREN                                                   │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  [Sarah (13)] [David (15)] [+ Add Child]             │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  ╔═══════════════════════════════════════════════════════╗ │
│  ║  SARAH'S COMMUNICATION HEALTH - THIS WEEK             ║ │
│  ╠═══════════════════════════════════════════════════════╣ │
│  ║                                                       ║ │
│  ║   ┌────────────┐  ┌────────────┐  ┌────────────┐    ║ │
│  ║   │ Overall    │  │ Trend      │  │ Activity   │    ║ │
│  ║   │   🟢       │  │   ⬆️       │  │  Active    │    ║ │
│  ║   │  HEALTHY   │  │ Improving  │  │  7 days    │    ║ │
│  ║   └────────────┘  └────────────┘  └────────────┘    ║ │
│  ║                                                       ║ │
│  ╚═══════════════════════════════════════════════════════╝ │
│                                                             │
│  KEY METRICS                                                │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Communication Quality                                │  │
│  │  ████████████████████░ 85%                           │  │
│  │  ⬆ +5% from last week                                │  │
│  │                                                       │  │
│  │  Positive Interactions: 18 this week                  │  │
│  │  Growth Areas: 3 situations handled well             │  │
│  │  Alerts Avoided: 4 (Sarah chose to rephrase)         │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  RECENT ACTIVITY                                            │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  ✅ Feb 8, 2:30 PM                                    │  │
│  │     Sarah earned 15 VP for choosing to rephrase       │  │
│  │     a message after a Speed Bump alert                │  │
│  │                                                       │  │
│  │  🏆 Feb 7, 4:15 PM                                    │  │
│  │     Achievement unlocked: "Week Warrior"              │  │
│  │     Sarah maintained a 7-day positive streak          │  │
│  │                                                       │  │
│  │  💬 Feb 6, 6:00 PM                                    │  │
│  │     Sarah improved group vibe by 12% with             │  │
│  │     supportive messages                               │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  ⚠️ ALERTS & CONCERNS                    [View All (0)]    │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  🎉 Great news! No concerning patterns this week      │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  QUICK ACTIONS                                              │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  [📊 View Detailed Report]  [💬 Conversation Tips]   │  │
│  │  [📚 Parent Resources]      [⚙️ Adjust Settings]     │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Detailed Insights Report

```
┌─────────────────────────────────────────────────────────────┐
│  📊 SARAH'S DETAILED REPORT - LAST 30 DAYS                   │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  COMMUNICATION HEALTH SCORE                                 │
│  ┌──────────────────────────────────────────────────────┐  │
│  │   100│                          ••••                  │  │
│  │    75│              ••••••••••••                      │  │
│  │    50│        ••••••                                  │  │
│  │    25│                                                │  │
│  │     0└────────────────────────────────>               │  │
│  │       Jan 8        Jan 23       Feb 8                 │  │
│  │                                                       │  │
│  │  Current: 85% (Healthy) • Avg: 78% • Trend: ⬆ +9%   │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  BEHAVIORAL PATTERNS                                        │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  ✅ Strengths                                         │  │
│  │     • Consistently positive in group chats            │  │
│  │     • Responds thoughtfully to Speed Bumps            │  │
│  │     • Active bystander (intervenes when others        │  │
│  │       need support)                                   │  │
│  │                                                       │  │
│  │  🎯 Growth Areas                                      │  │
│  │     • Tuesday mornings show lower communication       │  │
│  │       quality (possibly stressed before school?)      │  │
│  │     • Occasional rapid-fire messaging (may indicate   │  │
│  │       anxiety when waiting for responses)             │  │
│  │                                                       │  │
│  │  💡 Recommendations                                   │  │
│  │     • Consider checking in on Tuesday mornings        │  │
│  │     • Discuss healthy response expectations           │  │
│  │     • Celebrate her growth as an upstander            │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  INTERVENTION SUMMARY                                       │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Total Speed Bumps Shown: 6                           │  │
│  │  ├─ Sarah chose to edit: 5 (83%)                      │  │
│  │  ├─ Sarah sent anyway: 1 (17%)                        │  │
│  │  └─ Severity: All "Medium" or lower                   │  │
│  │                                                       │  │
│  │  Categories Detected:                                 │  │
│  │  • Frustration/Venting: 3 instances                   │  │
│  │  • Impatience: 2 instances                            │  │
│  │  • Mild sarcasm: 1 instance                           │  │
│  │                                                       │  │
│  │  💚 Great job! Sarah is learning to pause and         │  │
│  │     rephrase when upset.                              │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  SOCIAL ENGAGEMENT                                          │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Active Conversations: 8                              │  │
│  │  ├─ Private chats: 5 (all healthy)                    │  │
│  │  └─ Group chats: 3 (avg 82% harmony score)            │  │
│  │                                                       │  │
│  │  Peer Interactions:                                   │  │
│  │  • Messages sent: ~150/week                           │  │
│  │  • Average response time: 8 minutes                   │  │
│  │  • Most active: 6-10 PM (normal for age)              │  │
│  │  • Quiet hours respected: Yes ✓                       │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  COMPARISON TO PEERS                                        │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Sarah's communication health is better than          │  │
│  │  78% of peers in her age group                        │  │
│  │                                                       │  │
│  │  Top 25% in: Positive messaging, Alert response       │  │
│  │  Average in: Message frequency, Response time         │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Alerts & Notifications

```
┌─────────────────────────────────────────────────────────────┐
│  ⚠️ ALERTS & NOTIFICATIONS                                   │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  CURRENT ALERTS                                             │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  🎉 No active alerts - everything looks good!         │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  ALERT HISTORY                                              │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  ⚠️ Jan 28 - Increased Frustration Pattern            │  │
│  │     Status: RESOLVED                                  │  │
│  │                                                       │  │
│  │     Detection: Sarah had 4 frustrated messages        │  │
│  │     in 2 days, unusual for her baseline               │  │
│  │                                                       │  │
│  │     Resolution: Pattern normalized after weekend.     │  │
│  │     May have been related to midterm exams.           │  │
│  │                                                       │  │
│  │     [View Details] [Mark as Discussed]                │  │
│  │                                                       │  │
│  ├──────────────────────────────────────────────────────┤  │
│  │  ℹ️ Jan 15 - New Group Chat Joined                    │  │
│  │     Status: MONITORING                                │  │
│  │                                                       │  │
│  │     Sarah joined "Study Crew" group                   │  │
│  │     Current group health: 81% (Healthy)               │  │
│  │     Sarah's participation: Positive                   │  │
│  │                                                       │  │
│  │     [View Group Health]                               │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  ALERT SETTINGS                                             │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Notify me when:                                      │  │
│  │  ☑ High severity intervention (score > 0.85)          │  │
│  │  ☑ Pattern of concerning behavior (3+ in 7 days)      │  │
│  │  ☑ Dramatic drop in communication health (-20%)       │  │
│  │  ☑ Child consistently ignores warnings (5+ in row)    │  │
│  │  ☐ Any Speed Bump shown (may be too frequent)         │  │
│  │  ☑ Achievement milestones                             │  │
│  │                                                       │  │
│  │  Notification Method:                                 │  │
│  │  ☑ Email  ☑ Push Notification  ☐ SMS                 │  │
│  │                                                       │  │
│  │  [Save Settings]                                      │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Conversation Starters

```
┌─────────────────────────────────────────────────────────────┐
│  💬 CONVERSATION STARTERS WITH YOUR TEEN                     │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Based on Sarah's recent activity, here are suggested       │
│  conversation topics:                                       │
│                                                             │
│  ✨ CELEBRATE SUCCESSES                                     │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  "I noticed you earned the Week Warrior achievement!  │  │
│  │   That's awesome that you've been keeping such        │  │
│  │   positive vibes. What helps you stay consistent?"    │  │
│  │                                                       │  │
│  │  💡 Why this works: Reinforces positive behavior      │  │
│  │     without feeling like surveillance                 │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  🎯 GROWTH OPPORTUNITIES                                    │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  "I've noticed Tuesday mornings can be stressful      │  │
│  │   with getting ready for school. Is there anything    │  │
│  │   I can do to help those mornings go smoother?"       │  │
│  │                                                       │  │
│  │  💡 Why this works: Addresses pattern without         │  │
│  │     mentioning monitoring, offers support             │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  📚 TEACHING MOMENTS                                        │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  "I saw an article about how teens handle peer        │  │
│  │   pressure online. Have you ever felt pressured       │  │
│  │   to respond to messages immediately?"                │  │
│  │                                                       │  │
│  │  💡 Why this works: Opens dialogue about healthy      │  │
│  │     boundaries using external reference               │  │
│  │                                                       │  │
│  │  [Read Article]                                       │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  🤝 GENERAL CHECK-IN                                        │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  "How are you feeling about your friend groups       │  │
│  │   lately? Everyone treating each other well?"         │  │
│  │                                                       │  │
│  │  💡 Why this works: Open-ended, shows you care        │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  DO's AND DON'Ts                                            │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  ✅ DO: Focus on feelings and wellbeing               │  │
│  │  ✅ DO: Celebrate improvements                        │  │
│  │  ✅ DO: Offer support, not judgment                   │  │
│  │  ✅ DO: Ask open-ended questions                      │  │
│  │                                                       │  │
│  │  ❌ DON'T: Quote specific metrics or dates            │  │
│  │  ❌ DON'T: Make it feel like interrogation            │  │
│  │  ❌ DON'T: Violate their privacy unnecessarily        │  │
│  │  ❌ DON'T: Use monitoring as punishment threat        │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Parent Resources

```
┌─────────────────────────────────────────────────────────────┐
│  📚 PARENT EDUCATION & RESOURCES                             │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  RECOMMENDED FOR YOU                                        │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Based on Sarah's age (13) and recent patterns:       │  │
│  │                                                       │  │
│  │  📖 Understanding Teen Digital Communication          │  │
│  │     Learn about normal vs. concerning patterns        │  │
│  │     Duration: 10 min read                             │  │
│  │     [Start Reading]                                   │  │
│  │                                                       │  │
│  │  🎥 Managing Tuesday Morning Stress                   │  │
│  │     Expert tips on reducing morning anxiety           │  │
│  │     Duration: 5 min video                             │  │
│  │     [Watch Video]                                     │  │
│  │                                                       │  │
│  │  💬 How to Talk About Online Boundaries               │  │
│  │     Scripts for difficult conversations               │  │
│  │     Duration: 15 min interactive                      │  │
│  │     [Start Lesson]                                    │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  TOPIC LIBRARY                                              │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  🔹 Digital Wellbeing                                 │  │
│  │  🔹 Cyberbullying Prevention                          │  │
│  │  🔹 Healthy Online Relationships                      │  │
│  │  🔹 Setting Boundaries                                │  │
│  │  🔹 Privacy & Safety                                  │  │
│  │  🔹 Screen Time Balance                               │  │
│  │  🔹 Social Media Literacy                             │  │
│  │  🔹 Supporting Your Teen                              │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  EXPERT WEBINARS                                            │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  UPCOMING:                                            │  │
│  │  Feb 15, 7:00 PM - "Raising Digitally Resilient      │  │
│  │                     Teens"                            │  │
│  │  With Dr. Rachel Cohen, Child Psychologist            │  │
│  │  [Register]                                           │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│  COMMUNITY FORUM                                            │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Connect with other parents, share experiences        │  │
│  │  [Join Discussion]                                    │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## ⚙️ Technical Requirements

### Data Access & Privacy

```javascript
class ParentDashboardService {
  async getChildData(parentId, childId, timeRange = '7d') {
    // Verify parent-child relationship
    const relationship = await this.verifyParentChildLink(parentId, childId);
    if (!relationship.verified) {
      throw new Error('UNAUTHORIZED_ACCESS');
    }
    
    // Check child's privacy settings
    const privacySettings = await this.getChildPrivacySettings(childId);
    
    // Filter data based on privacy settings
    const data = await this.aggregateChildData(childId, timeRange);
    const filtered = this.applyPrivacyFilters(data, privacySettings);
    
    return filtered;
  }
  
  async aggregateChildData(childId, timeRange) {
    // Get aggregated stats ONLY, never individual messages
    const [
      healthScore,
      interventionStats,
      activitySummary,
      trends,
      achievements,
      alerts
    ] = await Promise.all([
      this.getHealthScore(childId, timeRange),
      this.getInterventionStats(childId, timeRange),
      this.getActivitySummary(childId, timeRange),
      this.getTrends(childId, timeRange),
      this.getAchievements(childId),
      this.getActiveAlerts(parentId, childId)
    ]);
    
    return {
      overview: {
        health_score: healthScore.current,
        trend: healthScore.trend,
        activity_status: activitySummary.status
      },
      stats: {
        positive_messages: interventionStats.positive_count,
        alerts_avoided: interventionStats.avoided_count,
        growth_areas: interventionStats.improvements
      },
      trends: {
        health_over_time: trends.health_chart,
        weekly_comparison: trends.week_over_week,
        patterns: trends.detected_patterns
      },
      achievements: achievements.recent,
      alerts: alerts.active
    };
  }
  
  applyPrivacyFilters(data, settings) {
    // Remove data that child has chosen to hide
    if (!settings.show_alerts_avoided) {
      delete data.stats.alerts_avoided;
    }
    
    if (!settings.show_achievement_details) {
      data.achievements = { count: data.achievements.length };
    }
    
    // NEVER include message content
    delete data.message_content;
    delete data.conversation_details;
    
    return data;
  }
}
```

### Alert System

```javascript
class ParentAlertSystem {
  async evaluateAlerts(childId) {
    const checks = await Promise.all([
      this.checkSeverityPattern(childId),
      this.checkHealthDrops(childId),
      this.checkIgnoredWarnings(childId),
      this.checkNewConcerns(childId)
    ]);
    
    const alerts = checks.filter(c => c.shouldAlert);
    
    // Send notifications based on parent settings
    for (const alert of alerts) {
      await this.notifyParent(alert);
    }
    
    return alerts;
  }
  
  async checkSeverityPattern(childId) {
    // Check for high-severity interventions
    const highSeverityCount = await db.query(`
      SELECT COUNT(*) as count
      FROM speed_bump_interventions
      WHERE user_id = $1
        AND severity = 'high'
        AND created_at > NOW() - INTERVAL '7 days'
    `, [childId]);
    
    if (highSeverityCount.rows[0].count >= 3) {
      return {
        shouldAlert: true,
        type: 'SEVERITY_PATTERN',
        severity: 'medium',
        title: 'Increased Concerning Messages',
        description: `Your child has received ${highSeverityCount.rows[0].count} high-severity alerts this week.`,
        recommendation: 'Consider having a conversation about what might be causing stress.',
        conversation_starters: [
          "I've noticed you might be feeling frustrated lately. Want to talk about it?",
          "How are things going with your friends?"
        ]
      };
    }
    
    return { shouldAlert: false };
  }
  
  async checkHealthDrops(childId) {
    const current = await this.getHealthScore(childId, '7d');
    const previous = await this.getHealthScore(childId, '14d');
    
    const percentChange = ((current - previous) / previous) * 100;
    
    if (percentChange < -20) {
      return {
        shouldAlert: true,
        type: 'HEALTH_DROP',
        severity: 'medium',
        title: 'Communication Health Declined',
        description: `Health score dropped ${Math.abs(percentChange).toFixed(0)}% this week.`,
        recommendation: 'This may indicate increased stress or conflict. Check in with your child.',
        possibleCauses: [
          'School stress (exams, projects)',
          'Friend group changes',
          'Personal challenges'
        ]
      };
    }
    
    return { shouldAlert: false };
  }
  
  async notifyParent(alert) {
    const parent = await this.getParentInfo(alert.childId);
    
    // Check notification preferences
    if (parent.notifications.email) {
      await this.sendEmailNotification(parent.email, alert);
    }
    
    if (parent.notifications.push) {
      await this.sendPushNotification(parent.device_tokens, alert);
    }
    
    // Store alert in database
    await this.storeAlert(alert);
  }
}
```

### Insights Generation

```javascript
class InsightsEngine {
  async generateInsights(childId, timeRange) {
    const [
      behavioralPatterns,
      strengths,
      growthAreas,
      peerComparison
    ] = await Promise.all([
      this.detectBehavioralPatterns(childId, timeRange),
      this.identifyStrengths(childId, timeRange),
      this.identifyGrowthAreas(childId, timeRange),
      this.compareToPeers(childId, timeRange)
    ]);
    
    return {
      patterns: behavioralPatterns,
      strengths: strengths,
      growth_areas: growthAreas,
      recommendations: this.generateRecommendations(
        strengths,
        growthAreas,
        behavioralPatterns
      ),
      peer_comparison: peerComparison
    };
  }
  
  async detectBehavioralPatterns(childId, timeRange) {
    // Detect time-of-day patterns
    const timePatterns = await db.query(`
      SELECT 
        EXTRACT(HOUR FROM created_at) as hour,
        AVG(harmony_score) as avg_harmony
      FROM harmony_scores
      WHERE user_id = $1
        AND created_at > NOW() - INTERVAL $2
      GROUP BY EXTRACT(HOUR FROM created_at)
      ORDER BY avg_harmony ASC
    `, [childId, timeRange]);
    
    // Detect day-of-week patterns
    const dayPatterns = await db.query(`
      SELECT 
        EXTRACT(DOW FROM created_at) as dow,
        AVG(harmony_score) as avg_harmony,
        COUNT(*) as message_count
      FROM harmony_scores
      WHERE user_id = $1
        AND created_at > NOW() - INTERVAL $2
      GROUP BY EXTRACT(DOW FROM created_at)
    `, [childId, timeRange]);
    
    return {
      time_of_day: this.analyzeTimePatterns(timePatterns.rows),
      day_of_week: this.analyzeDayPatterns(dayPatterns.rows)
    };
  }
  
  generateRecommendations(strengths, growthAreas, patterns) {
    const recommendations = [];
    
    // If there's a pattern of low scores at specific times
    if (patterns.time_of_day.low_periods.length > 0) {
      const period = patterns.time_of_day.low_periods[0];
      recommendations.push({
        type: 'TIME_PATTERN',
        priority: 'medium',
        text: `Consider checking in during ${period.description} when communication quality tends to dip`,
        action: 'Schedule a regular check-in during this time'
      });
    }
    
    // If child is good at editing messages after Speed Bumps
    if (strengths.includes('RESPONSIVE_TO_INTERVENTIONS')) {
      recommendations.push({
        type: 'CELEBRATION',
        priority: 'low',
        text: 'Your child is learning to pause and rethink messages - celebrate this growth!',
        action: 'Acknowledge their thoughtfulness in conversation'
      });
    }
    
    return recommendations;
  }
}
```

---

## 🗄️ Database Schema

```sql
-- Parent-child relationships
CREATE TABLE parent_child_links (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  parent_id UUID REFERENCES users(id),
  child_id UUID REFERENCES users(id),
  relationship_type VARCHAR(20) CHECK (relationship_type IN ('parent', 'guardian', 'other')),
  verified BOOLEAN DEFAULT false,
  verification_code VARCHAR(10),
  verification_expires_at TIMESTAMP,
  created_at TIMESTAMP DEFAULT NOW(),
  access_level VARCHAR(20) DEFAULT 'standard', -- standard, limited, full
  UNIQUE(parent_id, child_id),
  INDEX idx_parent (parent_id),
  INDEX idx_child (child_id)
);

-- Child privacy settings for parent access
CREATE TABLE child_parent_privacy_settings (
  child_id UUID PRIMARY KEY REFERENCES users(id),
  show_vp_total BOOLEAN DEFAULT true,
  show_level BOOLEAN DEFAULT true,
  show_streak BOOLEAN DEFAULT true,
  show_positive_messages BOOLEAN DEFAULT true,
  show_alerts_avoided BOOLEAN DEFAULT false,  -- More sensitive
  show_achievement_details BOOLEAN DEFAULT true,
  show_conversation_names BOOLEAN DEFAULT false, -- Who they chat with
  updated_at TIMESTAMP DEFAULT NOW()
);

-- Parent alerts
CREATE TABLE parent_alerts (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  parent_id UUID REFERENCES users(id),
  child_id UUID REFERENCES users(id),
  alert_type VARCHAR(50),
  severity VARCHAR(20) CHECK (severity IN ('info', 'low', 'medium', 'high', 'critical')),
  title TEXT,
  description TEXT,
  recommendation TEXT,
  metadata JSONB,
  status VARCHAR(20) DEFAULT 'active', -- active, acknowledged, resolved, dismissed
  created_at TIMESTAMP DEFAULT NOW(),
  acknowledged_at TIMESTAMP,
  resolved_at TIMESTAMP,
  INDEX idx_parent_active (parent_id, status, created_at DESC),
  INDEX idx_child_alerts (child_id, created_at DESC)
);

-- Parent notification preferences
CREATE TABLE parent_notification_settings (
  parent_id UUID PRIMARY KEY REFERENCES users(id),
  email_enabled BOOLEAN DEFAULT true,
  push_enabled BOOLEAN DEFAULT true,
  sms_enabled BOOLEAN DEFAULT false,
  
  notify_high_severity BOOLEAN DEFAULT true,
  notify_pattern_detected BOOLEAN DEFAULT true,
  notify_health_drop BOOLEAN DEFAULT true,
  notify_ignored_warnings BOOLEAN DEFAULT true,
  notify_achievements BOOLEAN DEFAULT true,
  notify_weekly_summary BOOLEAN DEFAULT true,
  
  quiet_hours_start TIME,
  quiet_hours_end TIME,
  
  updated_at TIMESTAMP DEFAULT NOW()
);

-- Dashboard snapshots for parents (aggregated data only)
CREATE TABLE parent_dashboard_snapshots (
  parent_id UUID REFERENCES users(id),
  child_id UUID REFERENCES users(id),
  snapshot_date DATE DEFAULT CURRENT_DATE,
  
  health_score INTEGER,
  health_trend VARCHAR(20), -- improving, stable, declining
  activity_status VARCHAR(20), -- active, inactive
  
  positive_messages_count INTEGER,
  alerts_avoided_count INTEGER,
  intervention_count INTEGER,
  achievement_count INTEGER,
  
  snapshot_data JSONB,
  
  PRIMARY KEY (parent_id, child_id, snapshot_date),
  INDEX idx_parent_recent (parent_id, snapshot_date DESC)
);
```

---

## 🔌 API Specifications

### Get Parent Dashboard

```http
GET /api/v1/parent/dashboard

Headers:
  Authorization: Bearer {jwt_token}

Query Parameters:
  - child_id: UUID (optional, shows all children if not specified)
  - time_range: 7d, 30d (default: 7d)

Response 200:
{
  "parent_id": "uuid",
  "children": [
    {
      "child_id": "uuid",
      "name": "Sarah",
      "age": 13,
      "overview": {
        "health_score": 85,
        "health_status": "healthy",
        "trend": "improving",
        "activity_status": "active",
        "streak_days": 7
      },
      "stats_this_week": {
        "positive_messages": 18,
        "alerts_avoided": 4,
        "growth_areas_count": 3,
        "vp_earned": 125,
        "vp_change_percent": 11.6
      },
      "recent_activity": [
        {
          "type": "alert_avoided",
          "timestamp": "2026-02-08T14:30:00Z",
          "description": "Chose to rephrase after Speed Bump",
          "vp_earned": 15
        },
        {
          "type": "achievement",
          "timestamp": "2026-02-07T16:15:00Z",
          "description": "Unlocked 'Week Warrior' achievement",
          "icon": "🔥"
        }
      ],
      "active_alerts": [],
      "insights": {
        "strengths": [
          "Consistently positive in group chats",
          "Responds thoughtfully to interventions"
        ],
        "growth_areas": [
          "Tuesday mornings show lower quality"
        ],
        "recommendations": [
          "Consider checking in on Tuesday mornings",
          "Celebrate growth as an upstander"
        ]
      }
    }
  ]
}
```

### Get Detailed Child Report

```http
GET /api/v1/parent/child/{child_id}/report

Headers:
  Authorization: Bearer {jwt_token}

Query Parameters:
  - time_range: 7d, 30d, 90d (default: 30d)

Response 200:
{
  "child_id": "uuid",
  "period": {
    "from": "2026-01-08T00:00:00Z",
    "to": "2026-02-08T00:00:00Z"
  },
  "health_score": {
    "current": 85,
    "average": 78,
    "trend": "improving",
    "change_percent": 9.0,
    "chart_data": [
      {"date": "2026-01-08", "score": 70},
      {"date": "2026-02-08", "score": 85}
    ]
  },
  "behavioral_patterns": {
    "strengths": [
      "Consistently positive in group chats",
      "Active bystander (intervenes when others need support)"
    ],
    "growth_areas": [
      "Tuesday mornings show lower communication quality",
      "Occasional rapid-fire messaging when anxious"
    ],
    "time_patterns": {
      "most_active": "6-10 PM",
      "best_quality": "Weekends",
      "challenging_times": "Tuesday 7-9 AM"
    }
  },
  "intervention_summary": {
    "total_speed_bumps": 6,
    "chose_to_edit": 5,
    "sent_anyway": 1,
    "edit_rate": 83.3,
    "categories": {
      "frustration": 3,
      "impatience": 2,
      "sarcasm": 1
    },
    "severity_breakdown": {
      "low": 2,
      "medium": 4,
      "high": 0,
      "critical": 0
    }
  },
  "social_engagement": {
    "active_conversations": 8,
    "private_chats": 5,
    "group_chats": 3,
    "messages_per_week": 150,
    "avg_response_time_minutes": 8,
    "most_active_hours": "6-10 PM"
  },
  "peer_comparison": {
    "percentile": 78,
    "top_areas": ["Positive messaging", "Alert response"],
    "average_areas": ["Message frequency", "Response time"]
  },
  "conversation_starters": [
    {
      "type": "celebrate",
      "text": "I noticed you earned the Week Warrior achievement!..."
    },
    {
      "type": "support",
      "text": "Tuesday mornings seem stressful. Can I help?..."
    }
  ]
}
```

### Get Active Alerts

```http
GET /api/v1/parent/alerts

Headers:
  Authorization: Bearer {jwt_token}

Query Parameters:
  - child_id: UUID (optional)
  - status: active, acknowledged, resolved, all (default: active)
  - severity: info, low, medium, high, critical (optional)

Response 200:
{
  "alerts": [
    {
      "id": "uuid",
      "child_id": "uuid",
      "child_name": "Sarah",
      "type": "SEVERITY_PATTERN",
      "severity": "medium",
      "title": "Increased Concerning Messages",
      "description": "Your child has received 3 high-severity alerts this week.",
      "recommendation": "Consider having a conversation about what might be causing stress.",
      "conversation_starters": [
        "I've noticed you might be feeling frustrated lately...",
        "How are things going with your friends?"
      ],
      "created_at": "2026-02-08T10:00:00Z",
      "status": "active",
      "actions": [
        {
          "label": "Mark as Acknowledged",
          "action": "acknowledge"
        },
        {
          "label": "Mark as Discussed",
          "action": "resolve"
        },
        {
          "label": "Dismiss",
          "action": "dismiss"
        }
      ]
    }
  ],
  "summary": {
    "active_count": 1,
    "high_severity_count": 0,
    "medium_severity_count": 1
  }
}
```

---

## 🧪 Testing Requirements

### Unit Tests

```javascript
describe('Parent Dashboard Service', () => {
  test('should verify parent-child relationship', async () => {
    const parentId = 'parent-1';
    const childId = 'child-1';
    
    const verified = await verifyParentChildLink(parentId, childId);
    expect(verified).toBe(true);
  });

  test('should apply privacy filters correctly', () => {
    const data = createFullChildData();
    const settings = { show_alerts_avoided: false };
    
    const filtered = applyPrivacyFilters(data, settings);
    expect(filtered.stats.alerts_avoided).toBeUndefined();
  });

  test('should never expose message content', async () => {
    const data = await getChildData('parent-1', 'child-1');
    expect(data.message_content).toBeUndefined();
    expect(data.conversation_details).toBeUndefined();
  });

  test('should generate appropriate alerts', async () => {
    const alerts = await evaluateAlerts('child-with-pattern');
    expect(alerts.length).toBeGreaterThan(0);
    expect(alerts[0]).toHaveProperty('conversation_starters');
  });
});
```

---

## 🎯 Success Metrics

### Product Metrics

**Engagement:**
- Parents checking dashboard: > 60% weekly
- Average session duration: 3-5 minutes
- Alert response time: < 24 hours

**Effectiveness:**
- Parents reporting improved communication with teens: > 70%
- Parents feeling informed but not invasive: > 80%
- Teens comfortable with parent access level: > 65%

**Education:**
- Parents using conversation starters: > 45%
- Parents completing educational resources: > 30%
- Parents adjusting settings appropriately: > 50%

### Technical Metrics

- Dashboard load time: < 1 second
- Alert generation latency: < 5 minutes
- Data aggregation accuracy: 100%
- Privacy compliance: 100%

---

## 🔒 Privacy & Ethics

### Ethical Guidelines

1. **Privacy First**
   - Aggregate data only, never individual messages
   - Teen controls what parent sees
   - Clear communication about monitoring

2. **Trust Building**
   - Dashboard is tool for support, not surveillance
   - Focus on patterns, not policing
   - Encourage parent-child dialogue

3. **Age-Appropriate**
   - More visibility for younger teens (13-14)
   - Gradual privacy increase as child matures
   - Option to remove monitoring at 18

4. **Transparency**
   - Teen knows exactly what parent can see
   - Teen receives notification when parent views dashboard
   - Teen can request privacy review

---

## ✅ Definition of Done

- [ ] Parent dashboard displaying all aggregated data
- [ ] Privacy filters working correctly
- [ ] Alert system functional and accurate
- [ ] Conversation starters generating appropriately
- [ ] Parent resources library complete
- [ ] No message content exposed to parents
- [ ] Teen privacy controls functional
- [ ] Notification system working
- [ ] Weekly summary emails sending
- [ ] Mobile responsive design
- [ ] Localized in Hebrew and English
- [ ] Parent user testing shows 80%+ satisfaction
- [ ] Teen acceptance of privacy model > 65%

---

**Document Version:** 1.0  
**Author:** Product Team  
**Last Updated:** February 2026  
**Status:** Ready for Development
