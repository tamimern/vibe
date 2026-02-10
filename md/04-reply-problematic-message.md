# Feature Requirements: Reply to Problematic Message (Vibe Group Response)

## 💬 Feature Overview

**Feature Name:** Reply to Problematic Message / Vibe Group Response  
**Priority:** P1 (Important Safety Feature)  
**Target Release:** Phase 2  
**Estimated Effort:** 2-3 weeks  

### Description
An automated group intervention system that responds to toxic messages with supportive, educational messages from the "Vibe" assistant. When a harmful message is detected in a group chat, the system can post a gentle reminder about healthy communication, helping to de-escalate situations and educate all group members.

### User Value
- **De-escalation:** Interrupts toxic conversation flow before it escalates
- **Education:** Teaches the whole group about healthy communication in context
- **Normalization:** Makes it socially acceptable to call out harmful behavior
- **Support:** Provides language for bystanders to intervene safely

---

## 🎯 User Stories

### As a Teen User
```
✓ I want the system to respond to harmful messages in my group
  So that the conversation stays healthy

✓ I want the response to be educational, not preachy
  So that it doesn't make the situation worse

✓ I want bystanders to see it's okay to speak up
  So that we can all support each other

✓ I want the option to disable this feature
  So that I control my chat experience

✓ I want the response to be in the same language as the chat
  So that everyone understands
```

### As a Parent
```
✓ I want my child's groups to have automatic support
  So that peer pressure is redirected positively

✓ I want to see if responses are helping
  So that I can gauge the group environment

✓ I want my child to learn bystander intervention
  So that they become an upstander
```

### As a Group Admin
```
✓ I want to set the response level (none/low/medium/high)
  So that the group has appropriate moderation

✓ I want statistics on group health
  So that I can manage the group better
```

---

## 🎨 UI/UX Specifications

### Response Message Design

#### In WhatsApp/Messaging App
```
┌────────────────────────────────────────────┐
│ Roni                               14:24   │
│ ┌────────────────────────────────────────┐ │
│ │ החנון בטח רק יצא לישון 😴            │ │
│ └────────────────────────────────────────┘ │
│                                            │
│ 🌟 Vibe                            14:24   │
│ ┌────────────────────────────────────────┐ │
│ │ 💙 שימו לב, ההודעה הזו עשויה להקטין   │ │
│ │ מישהו. בואו נשמור על אווירה טובה!     │ │
│ │                                         │ │
│ │ 💡 כולנו עייפים לפעמים, זה לא אומר   │ │
│ │ שמישהו "חנון"                          │ │
│ │                                         │ │
│ │ [מד האווירה ירד ל-62%]                │ │
│ └────────────────────────────────────────┘ │
└────────────────────────────────────────────┘
```

#### English Version
```
┌────────────────────────────────────────────┐
│ Roni                               2:24 PM │
│ ┌────────────────────────────────────────┐ │
│ │ The nerd probably just went to sleep 😴│ │
│ └────────────────────────────────────────┘ │
│                                            │
│ 🌟 Vibe                            2:24 PM │
│ ┌────────────────────────────────────────┐ │
│ │ 💙 Heads up, this message might         │ │
│ │ belittle someone. Let's keep good vibes!│ │
│ │                                         │ │
│ │ 💡 We all get tired sometimes, that     │ │
│ │ doesn't make someone a "nerd"           │ │
│ │                                         │ │
│ │ [Vibe meter dropped to 62%]            │ │
│ └────────────────────────────────────────┘ │
└────────────────────────────────────────────┘
```

### Response Types & Templates

| Situation | Response Template (Hebrew) | Response Template (English) |
|-----------|---------------------------|---------------------------|
| **Belittling** | 💙 שימו לב, ההודעה הזו עשויה להקטין מישהו. בואו נשמור על אווירה טובה! | 💙 Heads up, this message might belittle someone. Let's keep good vibes! |
| **Manipulation** | 🤔 נראה שיש פה ניסיון להשפיע על מישהו. תקשורת ישירה עובדת טוב יותר! | 🤔 Looks like there's an attempt to pressure someone. Direct communication works better! |
| **Gaslighting** | ⚠️ זיכרונות שונים זה בסדר, אבל בואו לא נתעלם מרגשות של אנשים | ⚠️ Different memories are okay, but let's not dismiss people's feelings |
| **Excessive Messages** | 📱 הרבה הודעות ברצף - תנו למישהו זמן לענות! כולם עסוקים לפעמים | 📱 Many messages in a row - give someone time to respond! Everyone's busy sometimes |
| **Name Calling** | 🛑 שמות גנאי פוגעים. בואו נדבר בכבוד גם כשאנחנו לא מסכימים | 🛑 Name-calling hurts. Let's speak respectfully even when we disagree |

### Tone & Voice Guidelines

**Do's:**
- ✅ Gentle, non-judgmental language
- ✅ Educational insight
- ✅ Actionable suggestion
- ✅ Emoji for warmth (💙, 💡, 🌟)
- ✅ Reference harmony score impact

**Don'ts:**
- ❌ Preachy or parental tone
- ❌ Shaming the sender
- ❌ Overly formal language
- ❌ Multiple responses to same person
- ❌ Interrupting fun conversations

---

## ⚙️ Technical Requirements

### Triggering Logic

```javascript
const shouldPostGroupResponse = (messageAnalysis, groupContext) => {
  const {
    toxicity_score,
    toxicity_categories,
    conversation_history,
    group_settings,
    user_history
  } = messageAnalysis;
  
  // Rule 1: Only trigger on high confidence
  if (toxicity_score < 0.80) return false;
  
  // Rule 2: Check group settings
  if (group_settings.response_level === 'none') return false;
  
  // Rule 3: Don't spam - max 1 response per 15 minutes
  const lastResponse = getLastResponseTime(groupContext.conversation_id);
  const timeSinceLastResponse = Date.now() - lastResponse;
  if (timeSinceLastResponse < 15 * 60 * 1000) return false;
  
  // Rule 4: Don't respond to same person repeatedly (max 2 per day)
  const todayResponsesToUser = getTodayResponseCount(
    groupContext.conversation_id,
    messageAnalysis.user_id
  );
  if (todayResponsesToUser >= 2) return false;
  
  // Rule 5: Critical severity always triggers
  if (messageAnalysis.severity === 'critical') return true;
  
  // Rule 6: Check escalation pattern
  const isEscalating = detectEscalation(conversation_history);
  if (isEscalating && toxicity_score > 0.75) return true;
  
  // Rule 7: Standard threshold for high severity
  if (messageAnalysis.severity === 'high' && toxicity_score > 0.80) {
    return true;
  }
  
  return false;
};
```

### Response Generation

```javascript
class VibeResponseGenerator {
  constructor() {
    this.templates = loadResponseTemplates();
  }
  
  async generateResponse(messageAnalysis, language = 'he') {
    const {
      toxicity_categories,
      severity,
      original_message_context,
      harmony_score_impact
    } = messageAnalysis;
    
    // Get primary category
    const primaryCategory = toxicity_categories
      .sort((a, b) => b.confidence - a.confidence)[0];
    
    // Select appropriate template
    const template = this.selectTemplate(primaryCategory.name, severity);
    
    // Customize template with context
    const customized = await this.customizeTemplate(
      template,
      original_message_context,
      language
    );
    
    // Add harmony score impact
    const withScore = this.addHarmonyScoreNote(
      customized,
      harmony_score_impact,
      language
    );
    
    return {
      message: withScore,
      type: 'vibe_group_response',
      category: primaryCategory.name,
      language: language,
      sender_name: language === 'he' ? 'Vibe' : 'Vibe',
      sender_icon: '🌟'
    };
  }
  
  selectTemplate(category, severity) {
    const templates = {
      belittling: {
        low: this.templates.belittling.gentle,
        medium: this.templates.belittling.moderate,
        high: this.templates.belittling.strong,
        critical: this.templates.belittling.urgent
      },
      manipulation: {
        low: this.templates.manipulation.gentle,
        medium: this.templates.manipulation.moderate,
        high: this.templates.manipulation.strong,
        critical: this.templates.manipulation.urgent
      },
      // ... more categories
    };
    
    return templates[category]?.[severity] || templates.belittling.moderate;
  }
  
  async customizeTemplate(template, context, language) {
    // Use LLM to slightly customize template based on context
    // while maintaining safety and tone
    const prompt = `
      Template: "${template}"
      Context: ${context.brief_summary}
      Language: ${language}
      
      Customize this template slightly to fit the context better,
      while maintaining the exact same tone and safety guidelines.
      Keep it brief (max 2-3 sentences).
    `;
    
    const customized = await this.llm.generate(prompt);
    
    // Validate customization didn't introduce problems
    const isValid = await this.validateResponse(customized);
    
    return isValid ? customized : template; // Fallback to template
  }
  
  addHarmonyScoreNote(message, scoreImpact, language) {
    if (Math.abs(scoreImpact) < 5) return message;
    
    const scoreNote = language === 'he'
      ? `\n\n[מד האווירה ירד ל-${scoreImpact}%]`
      : `\n\n[Vibe meter dropped to ${scoreImpact}%]`;
    
    return message + scoreNote;
  }
}
```

### Integration with Message Flow

```javascript
// When message is sent
const onMessageSent = async (message, conversationId) => {
  // 1. Analyze message for toxicity
  const analysis = await analyzeToxicity(message);
  
  // 2. Check if group response should be posted
  const groupContext = await getGroupContext(conversationId);
  const shouldRespond = shouldPostGroupResponse(analysis, groupContext);
  
  if (shouldRespond) {
    // 3. Generate appropriate response
    const response = await vibeResponseGenerator.generateResponse(
      analysis,
      groupContext.language
    );
    
    // 4. Post response to group (after small delay for natural feel)
    await delay(2000); // 2 second delay
    await postMessageToGroup(conversationId, response);
    
    // 5. Log intervention
    await logGroupIntervention({
      conversation_id: conversationId,
      original_message_hash: hashMessage(message.text),
      response: response,
      toxicity_analysis: analysis,
      timestamp: Date.now()
    });
    
    // 6. Update harmony score
    await updateHarmonyScore(conversationId, -analysis.severity_impact);
  }
};
```

---

## 🗄️ Database Schema

```sql
-- Group response interventions
CREATE TABLE group_response_interventions (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  conversation_id VARCHAR(255) NOT NULL,
  original_message_id VARCHAR(255),
  original_message_hash VARCHAR(64),
  original_sender_id UUID REFERENCES users(id),
  response_message TEXT NOT NULL,
  response_category VARCHAR(50),
  severity VARCHAR(20),
  toxicity_score DECIMAL(5,4),
  harmony_score_before INTEGER,
  harmony_score_after INTEGER,
  language VARCHAR(5),
  posted_at TIMESTAMP DEFAULT NOW(),
  INDEX idx_conversation (conversation_id, posted_at DESC),
  INDEX idx_sender (original_sender_id, posted_at DESC)
);

-- Group settings for response behavior
CREATE TABLE group_response_settings (
  conversation_id VARCHAR(255) PRIMARY KEY,
  response_level VARCHAR(20) CHECK (response_level IN ('none', 'low', 'medium', 'high')) DEFAULT 'medium',
  enabled BOOLEAN DEFAULT true,
  language VARCHAR(5) DEFAULT 'he',
  custom_templates JSONB,
  max_responses_per_hour INTEGER DEFAULT 4,
  updated_by UUID REFERENCES users(id),
  updated_at TIMESTAMP DEFAULT NOW()
);

-- Group response effectiveness tracking
CREATE TABLE group_response_outcomes (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  intervention_id UUID REFERENCES group_response_interventions(id),
  conversation_id VARCHAR(255),
  
  -- Did conversation improve after response?
  toxicity_decreased BOOLEAN,
  harmony_score_recovered BOOLEAN,
  conversation_ended BOOLEAN,
  
  -- How many messages after intervention?
  messages_until_recovery INTEGER,
  time_until_recovery_ms INTEGER,
  
  -- Did anyone react to the Vibe response?
  vibe_message_liked BOOLEAN,
  vibe_message_discussed BOOLEAN,
  
  -- User behavior changes
  original_sender_sent_apology BOOLEAN,
  bystanders_intervened BOOLEAN,
  
  measured_at TIMESTAMP DEFAULT NOW(),
  INDEX idx_intervention (intervention_id)
);
```

---

## 🔌 API Specifications

### Post Group Response (Internal)

```http
POST /api/v1/internal/groups/{conversation_id}/vibe-response

Headers:
  X-Internal-Secret: {service_secret}
  Content-Type: application/json

Body:
{
  "original_message_id": "msg_123",
  "original_message_hash": "sha256_hash",
  "original_sender_id": "user_uuid",
  "toxicity_analysis": {
    "score": 0.89,
    "categories": [
      {"name": "belittling", "confidence": 0.89}
    ],
    "severity": "high"
  },
  "response_message": "💙 שימו לב, ההודעה הזו עשויה להקטין מישהו...",
  "language": "he"
}

Response 200:
{
  "success": true,
  "intervention_id": "uuid",
  "message_posted": true,
  "harmony_score_updated": true,
  "new_harmony_score": 62
}
```

### Update Group Settings

```http
PUT /api/v1/groups/{conversation_id}/settings/responses

Headers:
  Authorization: Bearer {jwt_token}
  Content-Type: application/json

Body:
{
  "response_level": "medium", // none, low, medium, high
  "enabled": true,
  "language": "he",
  "max_responses_per_hour": 4
}

Response 200:
{
  "success": true,
  "settings": {
    "response_level": "medium",
    "enabled": true,
    "language": "he",
    "max_responses_per_hour": 4
  }
}
```

### Get Group Intervention History

```http
GET /api/v1/groups/{conversation_id}/interventions/history

Query Parameters:
  - from: ISO timestamp (default: 30 days ago)
  - to: ISO timestamp (default: now)
  - limit: 1-100 (default: 50)

Headers:
  Authorization: Bearer {jwt_token}

Response 200:
{
  "conversation_id": "chat_123",
  "total_interventions": 12,
  "period": {
    "from": "2026-01-08T00:00:00Z",
    "to": "2026-02-08T00:00:00Z"
  },
  "interventions": [
    {
      "id": "uuid",
      "posted_at": "2026-02-08T14:24:00Z",
      "category": "belittling",
      "severity": "high",
      "harmony_impact": -18,
      "outcome": {
        "conversation_improved": true,
        "recovery_time_seconds": 120,
        "bystanders_intervened": true
      }
    }
  ],
  "summary": {
    "effectiveness_rate": 0.75,
    "average_recovery_time_seconds": 180,
    "most_common_category": "belittling"
  }
}
```

---

## 🧪 Testing Requirements

### Unit Tests

```javascript
describe('Group Response System', () => {
  test('should trigger response for high toxicity', () => {
    const analysis = { toxicity_score: 0.89, severity: 'high' };
    const context = createDefaultGroupContext();
    expect(shouldPostGroupResponse(analysis, context)).toBe(true);
  });

  test('should not spam responses', () => {
    const analysis = { toxicity_score: 0.89, severity: 'high' };
    const context = createGroupContext({ lastResponseTime: Date.now() - 5 * 60 * 1000 });
    expect(shouldPostGroupResponse(analysis, context)).toBe(false);
  });

  test('should respect group settings', () => {
    const analysis = { toxicity_score: 0.89, severity: 'high' };
    const context = createGroupContext({ responseLevel: 'none' });
    expect(shouldPostGroupResponse(analysis, context)).toBe(false);
  });

  test('should generate appropriate response for category', async () => {
    const analysis = {
      toxicity_categories: [{ name: 'belittling', confidence: 0.89 }],
      severity: 'high'
    };
    const response = await generateResponse(analysis, 'he');
    expect(response.message).toContain('הקטין');
    expect(response.message).toContain('💙');
  });
});
```

### Integration Tests

```javascript
describe('Group Response Flow', () => {
  test('should post response after toxic message', async () => {
    const message = createToxicMessage('You\'re so stupid');
    const conversationId = 'test-group-1';
    
    await onMessageSent(message, conversationId);
    
    // Wait for async processing
    await delay(3000);
    
    const messages = await getGroupMessages(conversationId);
    const vibeResponse = messages.find(m => m.sender_name === 'Vibe');
    
    expect(vibeResponse).toBeDefined();
    expect(vibeResponse.message).toContain('belittle');
  });

  test('should update harmony score after response', async () => {
    const conversationId = 'test-group-2';
    const scoreBefore = await getHarmonyScore(conversationId);
    
    await postVibeResponse(conversationId, createHighSeverityResponse());
    
    const scoreAfter = await getHarmonyScore(conversationId);
    expect(scoreAfter).toBeLessThan(scoreBefore);
  });
});
```

---

## 🎯 Success Metrics

### Product Metrics

**Effectiveness:**
- Conversation improvement rate: > 70% after response
- Average recovery time: < 3 minutes
- Re-toxicity rate: < 20% (same person doesn't send toxic again)
- Bystander intervention increase: +40%

**Engagement:**
- Vibe messages liked/acknowledged: > 30%
- Group members discussing Vibe suggestions: > 15%
- Groups keeping responses enabled: > 85%

**Behavior Change:**
- Reduction in toxic messages after intervention: -60%
- Increase in supportive messages: +35%
- Improved harmony score trends: +20%

### Technical Metrics

- Response generation time: < 1 second
- Message posting latency: < 2 seconds
- False intervention rate: < 5%
- API uptime: 99.9%

---

## 🚧 Edge Cases & Considerations

### Spam Prevention

```javascript
const ANTI_SPAM_RULES = {
  max_per_conversation_per_hour: 4,
  max_per_user_per_day: 2,
  min_time_between_responses: 15 * 60 * 1000, // 15 minutes
  cooldown_after_ignored: 60 * 60 * 1000, // 1 hour
};
```

### Group Size Considerations

- **Small groups (2-5):** More lenient, focus on education
- **Medium groups (6-20):** Standard intervention
- **Large groups (20+):** Higher threshold, avoid spam

### Cultural Sensitivity

- Different norms for Hebrew vs. English groups
- Adjust severity thresholds by cultural context
- Respect group-specific dynamics

---

## 🔒 Privacy & Permissions

### User Control
- Users can disable responses in their groups (if admin)
- Users can report inappropriate Vibe responses
- Users can request response history deletion

### Data Storage
- ✅ Store response text (anonymized)
- ✅ Store effectiveness metrics
- ❌ Don't store original message content
- ❌ Don't identify specific users in analytics

---

## 🔄 Future Enhancements (V2+)

1. **Adaptive Responses**
   - Learn group's communication style
   - Customize tone per group culture

2. **Peer-to-Peer Prompts**
   - Suggest to bystanders: "Want to say something supportive?"
   - Enable peer intervention

3. **Restorative Messages**
   - Follow-up responses to check group health
   - Celebrate improvements

4. **Multi-Modal Detection**
   - Analyze images, voice messages
   - Detect toxic memes

---

## ✅ Definition of Done

- [ ] Triggering logic implemented and tested
- [ ] Response templates created for all categories (Hebrew + English)
- [ ] Generation system producing appropriate responses
- [ ] Integration with messaging flow complete
- [ ] Group settings UI implemented
- [ ] Analytics tracking interventions and outcomes
- [ ] Spam prevention working correctly
- [ ] Privacy review passed
- [ ] 70%+ effectiveness rate in testing
- [ ] User acceptance testing with 20+ groups
- [ ] Documentation complete

---

**Document Version:** 1.0  
**Author:** Product Team  
**Last Updated:** February 2026  
**Status:** Ready for Development
