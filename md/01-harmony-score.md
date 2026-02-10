# Feature Requirements: Harmony Score (Vibe Meter)

## 📊 Feature Overview

**Feature Name:** Harmony Score / Vibe Meter  
**Priority:** P0 (Core Feature)  
**Target Release:** MVP (Phase 1)  
**Estimated Effort:** 3-4 weeks  

### Description
The Harmony Score is a real-time numerical representation (0-100) of the current "vibe" or emotional atmosphere in a conversation. It updates dynamically based on message sentiment, toxicity levels, and interaction patterns.

### User Value
- **For Teens:** Immediate visual feedback on conversation health
- **For Parents:** Quick overview of their child's communication environment
- **For Groups:** Collective awareness of group dynamics

---

## 🎯 User Stories

### As a Teen User
```
✓ I want to see the current vibe of my chat in real-time
  So that I can be aware of the conversation atmosphere

✓ I want to see how my messages affect the group vibe
  So that I can contribute positively

✓ I want to understand what makes the vibe go up or down
  So that I can improve my communication

✓ I want the vibe meter to be non-intrusive
  So that it doesn't disrupt my chatting experience
```

### As a Parent
```
✓ I want to see my child's average harmony score over time
  So that I can monitor their digital wellbeing

✓ I want to be alerted if the score drops significantly
  So that I can check in with my child

✓ I want to see score trends (daily/weekly)
  So that I can identify patterns
```

---

## 🎨 UI/UX Specifications

### Visual Design

#### Floating Bubble (Primary Display)
```
┌─────────────────┐
│   ╭─────────╮   │
│   │   85%   │   │  ← Harmony Score (large)
│   │  ━━━━━  │   │  ← Visual bar indicator
│   │ 🟢 Good │   │  ← Status emoji + text
│   ╰─────────╯   │
└─────────────────┘

Position: Top-right of messaging interface
Size: 80x80px (compact) → 200x200px (expanded)
Always on top: Yes (z-index: 9999)
```

#### Score Visualization States

| Score Range | Color | Emoji | Status Text | Animation |
|-------------|-------|-------|-------------|-----------|
| 90-100 | `#10b981` (Green) | 🟢 | "מעולה!" / "Excellent!" | Gentle pulse |
| 70-89 | `#06b6d4` (Cyan) | 🔵 | "טוב" / "Good" | Steady glow |
| 50-69 | `#f59e0b` (Orange) | 🟠 | "בסדר" / "OK" | Slow fade |
| 30-49 | `#ef4444` (Red) | 🔴 | "מתוח" / "Tense" | Subtle shake |
| 0-29 | `#991b1b` (Dark Red) | 🔴 | "קריטי" / "Critical" | Alert pulse |

#### Interaction States

**Collapsed (Default):**
```jsx
<div className="floating-bubble">
  <div className="score">85</div>
  <div className="emoji">🟢</div>
</div>
```

**Expanded (On Tap):**
```jsx
<div className="floating-bubble expanded">
  <div className="header">
    <span className="title">Harmony Score</span>
    <button className="close">×</button>
  </div>
  <div className="score-display">
    <div className="large-score">85%</div>
    <div className="status">🟢 Good Vibes</div>
  </div>
  <div className="mini-chart">
    <!-- Last 10 messages trend -->
  </div>
  <div className="actions">
    <button>📊 Dashboard</button>
    <button>🌟 Summary</button>
    <button>✨ Send Positive</button>
  </div>
</div>
```

### Accessibility
- WCAG 2.1 AA compliant color contrast
- Screen reader support: "Current harmony score is 85 percent, status good"
- Haptic feedback on score changes (mobile)
- Adjustable size for low vision users

---

## ⚙️ Technical Requirements

### Calculation Algorithm

#### Base Formula
```javascript
const calculateHarmonyScore = (conversationContext) => {
  const {
    recentMessages,      // Last 10 messages
    toxicityScores,      // ML model outputs
    sentimentScores,     // Positive/negative sentiment
    responseTime,        // Average response delay
    participationRate,   // % of active members
    emojiPositivity,     // Emoji sentiment
  } = conversationContext;

  // Weighted components
  const toxicityPenalty = calculateToxicityPenalty(toxicityScores);
  const sentimentBonus = calculateSentimentBonus(sentimentScores);
  const engagementScore = calculateEngagement(participationRate);
  const timingScore = calculateTiming(responseTime);
  
  // Base score starts at 75 (neutral)
  let score = 75;
  
  // Apply modifiers
  score -= toxicityPenalty;  // 0-40 penalty
  score += sentimentBonus;    // 0-20 bonus
  score += engagementScore;   // 0-10 bonus
  score -= timingScore;       // 0-5 penalty for obsessive timing
  
  // Clamp between 0-100
  return Math.max(0, Math.min(100, score));
};
```

#### Toxicity Penalty Calculation
```javascript
const calculateToxicityPenalty = (toxicityScores) => {
  // Recent messages have more weight
  const weights = [1.5, 1.4, 1.3, 1.2, 1.1, 1.0, 0.9, 0.8, 0.7, 0.6];
  
  const weightedSum = toxicityScores.reduce((sum, score, index) => {
    return sum + (score * weights[index]);
  }, 0);
  
  const averageToxicity = weightedSum / weights.reduce((a, b) => a + b);
  
  // Map 0-1 toxicity to 0-40 penalty
  return averageToxicity * 40;
};
```

#### Sentiment Bonus Calculation
```javascript
const calculateSentimentBonus = (sentimentScores) => {
  const positiveSentiment = sentimentScores.filter(s => s > 0.5).length;
  const totalMessages = sentimentScores.length;
  const positiveRatio = positiveSentiment / totalMessages;
  
  // Up to 20 points bonus for consistently positive messages
  return positiveRatio * 20;
};
```

### Real-Time Updates

#### WebSocket Event Flow
```
Client                          Server                      ML Service
  |                               |                             |
  |-- User types message -------->|                             |
  |                               |-- Analyze text ------------>|
  |                               |<-- ML predictions ----------|
  |                               |-- Calculate new score ----->|
  |<-- Score update (WebSocket)---|                             |
  |-- Update UI ----------------->|                             |
  |                               |                             |
```

#### WebSocket Message Format
```json
{
  "type": "harmony_score_update",
  "conversation_id": "chat_12345",
  "score": 85,
  "previous_score": 78,
  "change": +7,
  "status": "good",
  "contributing_factors": [
    { "type": "positive_sentiment", "impact": +5 },
    { "type": "engagement", "impact": +2 }
  ],
  "timestamp": "2026-02-08T14:23:45Z"
}
```

### Caching Strategy
```javascript
// Redis cache structure
const cacheKey = `harmony:${conversationId}`;

const cachedData = {
  score: 85,
  lastMessages: [...],  // Last 10 message IDs
  lastCalculated: timestamp,
  ttl: 300  // 5 minutes
};

// Cache invalidation triggers:
// - New message sent
// - Message deleted
// - User joins/leaves
```

### Performance Requirements
- **Calculation Time:** < 50ms per message
- **Update Latency:** < 100ms from message sent to UI update
- **Cache Hit Rate:** > 80%
- **Memory Usage:** < 10MB per active conversation

---

## 🗄️ Database Schema

### PostgreSQL Tables

```sql
-- Harmony score history
CREATE TABLE harmony_scores (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  conversation_id VARCHAR(255) NOT NULL,
  user_id UUID REFERENCES users(id),
  score INTEGER CHECK (score >= 0 AND score <= 100),
  message_id UUID,
  contributing_factors JSONB,
  calculated_at TIMESTAMP DEFAULT NOW(),
  INDEX idx_conversation_time (conversation_id, calculated_at DESC),
  INDEX idx_user_time (user_id, calculated_at DESC)
);

-- Conversation metadata
CREATE TABLE conversations (
  id VARCHAR(255) PRIMARY KEY,
  type VARCHAR(20) CHECK (type IN ('private', 'group')),
  participant_count INTEGER,
  current_harmony_score INTEGER,
  average_score_24h DECIMAL(5,2),
  average_score_7d DECIMAL(5,2),
  last_updated TIMESTAMP DEFAULT NOW()
);

-- Score snapshots for analytics
CREATE TABLE harmony_snapshots (
  id BIGSERIAL PRIMARY KEY,
  conversation_id VARCHAR(255) REFERENCES conversations(id),
  score INTEGER,
  snapshot_time TIMESTAMP DEFAULT NOW(),
  INDEX idx_conv_snapshot (conversation_id, snapshot_time)
);

-- Partition by month for scalability
CREATE TABLE harmony_scores_2026_02 PARTITION OF harmony_scores
FOR VALUES FROM ('2026-02-01') TO ('2026-03-01');
```

### Time-Series Data (InfluxDB Alternative)
```
# InfluxDB schema for high-frequency data
measurement: harmony_score
tags:
  - conversation_id
  - user_id
  - score_range (0-29, 30-49, etc.)
fields:
  - score (integer)
  - toxicity_penalty (float)
  - sentiment_bonus (float)
timestamp: nanosecond precision
```

---

## 🔌 API Specifications

### REST Endpoints

#### Get Current Harmony Score
```http
GET /api/v1/conversations/{conversation_id}/harmony-score

Headers:
  Authorization: Bearer {jwt_token}
  Accept-Language: he, en

Response 200:
{
  "conversation_id": "chat_12345",
  "current_score": 85,
  "status": "good",
  "emoji": "🟢",
  "last_updated": "2026-02-08T14:23:45Z",
  "trend": {
    "direction": "rising",
    "change_1h": +7,
    "change_24h": +12
  },
  "breakdown": {
    "toxicity_penalty": -5,
    "sentiment_bonus": +15,
    "engagement_score": +8,
    "timing_penalty": -2
  }
}
```

#### Get Harmony Score History
```http
GET /api/v1/conversations/{conversation_id}/harmony-score/history

Query Parameters:
  - from: ISO 8601 timestamp (default: 24h ago)
  - to: ISO 8601 timestamp (default: now)
  - interval: 5m, 15m, 1h, 1d (default: 15m)

Response 200:
{
  "conversation_id": "chat_12345",
  "data_points": [
    {
      "timestamp": "2026-02-08T10:00:00Z",
      "score": 78,
      "status": "good"
    },
    {
      "timestamp": "2026-02-08T10:15:00Z",
      "score": 82,
      "status": "good"
    }
    // ... more data points
  ],
  "summary": {
    "min": 45,
    "max": 92,
    "average": 76.5,
    "std_dev": 12.3
  }
}
```

### WebSocket Events

#### Subscribe to Harmony Updates
```javascript
// Client subscribes
socket.emit('subscribe:harmony', {
  conversation_id: 'chat_12345'
});

// Server pushes updates
socket.on('harmony:update', (data) => {
  console.log('New score:', data.score);
});

// Unsubscribe
socket.emit('unsubscribe:harmony', {
  conversation_id: 'chat_12345'
});
```

---

## 🧪 Testing Requirements

### Unit Tests

```javascript
describe('Harmony Score Calculation', () => {
  test('should return 75 for neutral conversation', () => {
    const context = createNeutralContext();
    expect(calculateHarmonyScore(context)).toBe(75);
  });

  test('should penalize toxic messages', () => {
    const context = createContextWithToxicity(0.8);
    expect(calculateHarmonyScore(context)).toBeLessThan(50);
  });

  test('should bonus for positive sentiment', () => {
    const context = createContextWithPositivity(0.9);
    expect(calculateHarmonyScore(context)).toBeGreaterThan(85);
  });

  test('should weight recent messages higher', () => {
    const oldToxic = createContextWithOldToxicity();
    const recentToxic = createContextWithRecentToxicity();
    expect(calculateHarmonyScore(recentToxic))
      .toBeLessThan(calculateHarmonyScore(oldToxic));
  });
});
```

### Integration Tests

```javascript
describe('Harmony Score API', () => {
  test('should return current score for authorized user', async () => {
    const response = await request(app)
      .get('/api/v1/conversations/chat_123/harmony-score')
      .set('Authorization', 'Bearer valid_token')
      .expect(200);
    
    expect(response.body.current_score).toBeGreaterThanOrEqual(0);
    expect(response.body.current_score).toBeLessThanOrEqual(100);
  });

  test('should deny access to unauthorized user', async () => {
    await request(app)
      .get('/api/v1/conversations/chat_123/harmony-score')
      .expect(401);
  });
});
```

### Performance Tests

```javascript
describe('Harmony Score Performance', () => {
  test('should calculate score in < 50ms', async () => {
    const start = Date.now();
    await calculateHarmonyScore(largeContext);
    const duration = Date.now() - start;
    expect(duration).toBeLessThan(50);
  });

  test('should handle 1000 concurrent updates', async () => {
    const promises = Array(1000).fill().map(() => 
      updateHarmonyScore('chat_123')
    );
    await expect(Promise.all(promises)).resolves.toBeDefined();
  });
});
```

---

## 🎯 Success Metrics

### Product Metrics

**Engagement:**
- Users checking harmony score: > 70% daily
- Average checks per session: 5-10
- Time spent viewing expanded view: 10-30 seconds

**Behavior Change:**
- Correlation between score visibility and positive messages: +25%
- Users pausing before sending in low-score situations: +40%
- Groups maintaining 70+ score: 60% of active groups

### Technical Metrics

**Performance:**
- P95 calculation latency: < 50ms
- WebSocket update delivery: < 100ms
- API uptime: 99.9%
- Cache hit rate: > 85%

**Accuracy:**
- User agreement with score: > 75% (via feedback)
- Score prediction accuracy vs. user-reported mood: R² > 0.7

---

## 🚧 Edge Cases & Error Handling

### Edge Cases

1. **New Conversation (No History)**
   - Solution: Start at neutral 75, use platform averages

2. **Single Message Conversations**
   - Solution: Score based on that message + user history

3. **Rapid Message Bursts (10+ messages/minute)**
   - Solution: Batch process, update every 5 seconds max

4. **Multi-Language Conversations**
   - Solution: Analyze each message in detected language

5. **Emoji-Only Messages**
   - Solution: Sentiment analysis on emoji sequences

6. **Deleted Messages**
   - Solution: Recalculate without deleted message, note in history

### Error States

```javascript
const ErrorStates = {
  CALCULATION_FAILED: {
    fallback: 'Use last known score',
    display: 'Score temporarily unavailable',
    retry: true
  },
  
  WEBSOCKET_DISCONNECTED: {
    fallback: 'Poll API every 10s',
    display: 'Updating...',
    retry: true
  },
  
  INSUFFICIENT_DATA: {
    fallback: 'Show estimated score with disclaimer',
    display: '~75% (estimated)',
    retry: false
  },
  
  RATE_LIMIT_EXCEEDED: {
    fallback: 'Cache last score for 60s',
    display: 'Score updates paused',
    retry: true
  }
};
```

---

## 🔒 Privacy & Security

### Data Privacy

**What We Store:**
- ✅ Numerical scores (de-identified)
- ✅ Aggregated trends
- ✅ Contributing factor categories

**What We DON'T Store:**
- ❌ Full message content
- ❌ Participant names
- ❌ Conversation context beyond analysis

### Security Measures

```javascript
// Conversation access control
const verifyAccess = async (userId, conversationId) => {
  const isParticipant = await db.query(
    'SELECT 1 FROM conversation_participants WHERE user_id = $1 AND conversation_id = $2',
    [userId, conversationId]
  );
  
  const isParentMonitoring = await db.query(
    'SELECT 1 FROM parent_child_links WHERE parent_id = $1 AND child_conversation = $2',
    [userId, conversationId]
  );
  
  return isParticipant || isParentMonitoring;
};
```

---

## 📱 Platform-Specific Considerations

### iOS
- Use SwiftUI for smooth animations
- Leverage Combine for reactive updates
- Support Dynamic Type for accessibility
- Use Haptic Engine for score changes

### Android
- Material Design 3 components
- MotionLayout for complex animations
- Accessibility TalkBack support
- Work Manager for background updates

### Web
- CSS animations for performance
- Service Worker for offline support
- Web Socket with fallback to SSE
- Responsive breakpoints

---

## 🔄 Future Enhancements (V2+)

1. **Predictive Scoring**
   - ML model to predict score trajectory
   - Preemptive warnings before score drops

2. **Contextual Insights**
   - "Score dropped because of topic X"
   - Personalized recommendations

3. **Group Comparison**
   - "Your group's score is higher than 80% of similar groups"
   - Leaderboards (opt-in)

4. **Voice/Video Analysis**
   - Tone detection in voice messages
   - Facial expression analysis (with consent)

5. **Integration with Wellbeing APIs**
   - Screen time correlation
   - Sleep quality correlation (with Apple Health / Google Fit)

---

## ✅ Definition of Done

- [ ] Algorithm calculates scores accurately (>75% user agreement)
- [ ] Real-time updates delivered in <100ms
- [ ] UI renders smoothly at 60fps
- [ ] All unit tests pass (>90% coverage)
- [ ] API endpoints documented and tested
- [ ] Privacy review completed
- [ ] Accessibility audit passed (WCAG AA)
- [ ] Load tested at 10,000 concurrent users
- [ ] Localized in Hebrew and English
- [ ] Analytics tracking implemented
- [ ] Error monitoring configured
- [ ] User acceptance testing with 50+ teens

---

**Document Version:** 1.0  
**Author:** Product Team  
**Last Updated:** February 2026  
**Status:** Ready for Development
