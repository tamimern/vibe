# Feature Requirements: Speed Bump (Suggest Rephrase)

## 🛑 Feature Overview

**Feature Name:** Speed Bump / Rephrase Suggestion  
**Priority:** P0 (Core Safety Feature)  
**Target Release:** MVP (Phase 1)  
**Estimated Effort:** 4-5 weeks  

### Description
A real-time intervention system that detects potentially harmful messages before they are sent and suggests alternative, healthier phrasings. Acts as a "speed bump" to give users a moment to reconsider toxic communication.

### User Value
- **Prevention:** Stops harmful messages before damage is done
- **Education:** Teaches better communication in real-time
- **Reflection:** Creates a pause for self-awareness
- **Safety:** Protects both sender and receiver from negative interactions

---

## 🎯 User Stories

### As a Teen User
```
✓ I want to be warned when my message might hurt someone
  So that I don't accidentally damage relationships

✓ I want to see alternative ways to phrase my message
  So that I can express myself without being hurtful

✓ I want the option to edit or send anyway
  So that I maintain control over my communication

✓ I want to understand why my message was flagged
  So that I can learn better communication patterns

✓ I want the intervention to be private
  So that I don't feel publicly shamed
```

### As a Parent
```
✓ I want my child to receive gentle guidance on toxic messages
  So that they learn healthy communication

✓ I want to know if my child frequently ignores warnings
  So that I can have conversations about digital behavior

✓ I want the system to be educational, not punitive
  So that my child doesn't feel attacked
```

---

## 🎨 UI/UX Specifications

### Speed Bump Modal Design

#### Desktop/Web Version
```
┌─────────────────────────────────────────────────────────┐
│                    [Soft gradient background]            │
│                                                          │
│                    ⏸️ Wait a moment...                   │
│                                                          │
│   "Did you really mean to say that?"                     │
│                                                          │
│   ┌───────────────────────────────────────────────┐    │
│   │ Your message:                                  │    │
│   │ "אתה ממש מטומטם למה לא הבנת את זה"          │    │
│   │                                                │    │
│   │ 🚨 This message might:                         │    │
│   │    • Belittle someone (הקטנה)                │    │
│   │    • Hurt feelings                             │    │
│   └───────────────────────────────────────────────┘    │
│                                                          │
│   ┌───────────────────────────────────────────────┐    │
│   │ 💡 Maybe try this instead:                     │    │
│   │                                                │    │
│   │ "אולי לא הסברתי טוב, בוא ננסה שוב?"          │    │
│   │                                                │    │
│   │ [Copy suggestion] ✏️                           │    │
│   └───────────────────────────────────────────────┘    │
│                                                          │
│   ┌─────────────────────┐  ┌──────────────────────┐   │
│   │ ✏️ Edit Message      │  │ Send Anyway →        │   │
│   │ (Earn 15 VP)         │  │                      │   │
│   └─────────────────────────┘  └──────────────────────┘   │
│                                                          │
│            "Most people choose to edit (87%)"            │
└─────────────────────────────────────────────────────────┘
```

#### Mobile Version (Compact)
```
┌──────────────────────────────┐
│  ⏸️ Wait a moment...          │
├──────────────────────────────┤
│  Your message might hurt     │
│  someone                     │
│                              │
│  🚨 Detected:                │
│  • Belittling language       │
│                              │
│  💡 Try instead:             │
│  ┌────────────────────────┐ │
│  │ "אולי לא הסברתי טוב,  │ │
│  │  בוא ננסה שוב?"       │ │
│  │  [Copy] ✏️             │ │
│  └────────────────────────┘ │
│                              │
│  ┌──────────┐  ┌──────────┐ │
│  │ Edit (+15)│  │Send Anyway││
│  └──────────┘  └──────────┘ │
└──────────────────────────────┘
```

### Trigger Conditions

| Toxicity Category | Threshold | Example Triggers |
|-------------------|-----------|------------------|
| **Belittling (הקטנה)** | Confidence > 0.75 | "You're stupid", "אתה מטומטם" |
| **Manipulation** | Confidence > 0.70 | "If you really cared...", "אם אתה באמת חבר..." |
| **Gaslighting** | Confidence > 0.80 | "That never happened", "זה לא קרה" |
| **Obsessive** | Confidence > 0.65 | "Why haven't you replied???", "למה לא עניתה???" |
| **Passive Aggressive** | Confidence > 0.70 | "Fine whatever...", "בסדר גמור..." |
| **Threats** | Confidence > 0.90 | "I'll make you regret...", "תצטער על..." |
| **Excessive Profanity** | Count > 2 | Multiple curse words in one message |

### Animation Sequence

```javascript
const SpeedBumpAnimation = () => {
  return (
    <AnimatePresence>
      <motion.div
        initial={{ opacity: 0, scale: 0.9, y: 20 }}
        animate={{ opacity: 1, scale: 1, y: 0 }}
        exit={{ opacity: 0, scale: 0.9, y: -20 }}
        transition={{ 
          type: "spring", 
          stiffness: 300, 
          damping: 25 
        }}
        className="speed-bump-modal"
      >
        {/* Subtle shake on appear for attention */}
        <motion.div
          animate={{ 
            x: [0, -5, 5, -5, 5, 0],
            transition: { duration: 0.4, delay: 0.2 }
          }}
          className="icon-container"
        >
          ⏸️
        </motion.div>
        
        {/* Content with staggered appearance */}
        <motion.div
          variants={staggerChildren}
          initial="hidden"
          animate="visible"
        >
          {children}
        </motion.div>
      </motion.div>
      
      {/* Background blur overlay */}
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
        className="backdrop-blur"
        onClick={onBackdropClick}
      />
    </AnimatePresence>
  );
};
```

### Color Psychology

- **Background:** Soft orange gradient (#FEF3C7 → #FED7AA) - Warning but not aggressive
- **Icon:** Pause emoji (⏸️) - Suggests temporary stop, not permanent block
- **Accent:** Purple (#A855F7) - Calm, thoughtful
- **CTA Primary:** Green (#10B981) - "Edit Message" = positive action
- **CTA Secondary:** Gray (#6B7280) - "Send Anyway" = discouraged but allowed

---

## ⚙️ Technical Requirements

### Real-Time Detection Pipeline

```
┌──────────────┐      ┌──────────────┐      ┌──────────────┐
│  User Types  │─────>│  ML Service  │─────>│ Speed Bump   │
│   Message    │      │  (Toxicity)  │      │   UI         │
└──────────────┘      └──────────────┘      └──────────────┘
       │                      │                      │
       │                      │                      │
       v                      v                      v
   Debounced           Confidence           Modal Shown
   (500ms delay)       Score > 0.75        (if flagged)
```

### ML Model Architecture

#### Primary Toxicity Classifier

```python
# Model: Fine-tuned BERT for Hebrew/English
class ToxicityClassifier:
    def __init__(self):
        self.model = BertForSequenceClassification.from_pretrained(
            'google/muril-base-cased',  # Multilingual BERT
            num_labels=7  # 7 toxicity categories
        )
        self.tokenizer = BertTokenizer.from_pretrained('google/muril-base-cased')
        
    def predict(self, text, language='he'):
        """
        Returns:
        {
            'is_toxic': bool,
            'confidence': float,
            'categories': [
                {'name': 'belittling', 'confidence': 0.89},
                {'name': 'manipulation', 'confidence': 0.12},
                ...
            ],
            'severity': 'low' | 'medium' | 'high' | 'critical'
        }
        """
        inputs = self.tokenizer(text, return_tensors='pt', 
                                max_length=512, truncation=True)
        outputs = self.model(**inputs)
        logits = outputs.logits
        probabilities = torch.softmax(logits, dim=1)
        
        # Get top categories
        top_categories = self._get_top_categories(probabilities)
        
        # Determine if should trigger Speed Bump
        should_trigger = any(cat['confidence'] > 0.75 
                            for cat in top_categories)
        
        return {
            'is_toxic': should_trigger,
            'confidence': max(cat['confidence'] for cat in top_categories),
            'categories': top_categories,
            'severity': self._calculate_severity(top_categories)
        }
```

#### Rephrase Suggestion Generator

```python
# Model: Fine-tuned GPT for constructive rephrasing
class RephraseGenerator:
    def __init__(self):
        self.client = OpenAI(api_key=os.getenv('OPENAI_API_KEY'))
        
    def generate_suggestions(self, toxic_text, categories, language='he'):
        """
        Generates 1-3 alternative phrasings
        """
        prompt = self._build_prompt(toxic_text, categories, language)
        
        response = self.client.chat.completions.create(
            model="gpt-4",
            messages=[
                {"role": "system", "content": self._get_system_prompt(language)},
                {"role": "user", "content": prompt}
            ],
            temperature=0.7,
            max_tokens=200,
            n=2  # Generate 2 alternatives
        )
        
        suggestions = [choice.message.content 
                      for choice in response.choices]
        
        return {
            'suggestions': suggestions,
            'reasoning': self._explain_changes(toxic_text, suggestions[0])
        }
    
    def _get_system_prompt(self, language):
        if language == 'he':
            return """אתה עוזר תקשורת חיובי לבני נוער.
            תפקידך לקחת הודעות בעייתיות ולהציע ניסוחים טובים יותר שמביעים את אותו רעיון
            בצורה מכבדת ובונה.
            כללים:
            - שמור על הכוונה המקורית של ההודעה
            - השתמש בשפה טבעית של בני נוער
            - היה חיובי אבל לא מלאכותי
            - אל תשתמש בשפה פורמלית מדי"""
        else:
            return """You are a positive communication assistant for teenagers.
            Your job is to take problematic messages and suggest better phrasings
            that express the same idea in a respectful, constructive way.
            Rules:
            - Maintain the original intent
            - Use natural teen language
            - Be positive but not artificial
            - Don't be overly formal"""
    
    def _build_prompt(self, text, categories, language):
        categories_str = ', '.join([c['name'] for c in categories 
                                   if c['confidence'] > 0.5])
        
        if language == 'he':
            return f"""ההודעה הבאה מכילה: {categories_str}

הודעה מקורית: "{text}"

הצע ניסוח אחד טוב יותר שמביע את אותו רעיון בצורה יותר בונה.
תן רק את ההודעה המתוקנת, בלי הסברים."""
        else:
            return f"""The following message contains: {categories_str}

Original message: "{text}"

Suggest one better phrasing that expresses the same idea more constructively.
Provide only the rephrased message, no explanations."""
```

### Client-Side Integration

```typescript
// React Hook for Speed Bump
const useSpeedBump = () => {
  const [isChecking, setIsChecking] = useState(false);
  const [speedBumpData, setSpeedBumpData] = useState(null);
  
  const checkMessage = useCallback(
    debounce(async (text: string) => {
      if (text.length < 10) return; // Don't check very short messages
      
      setIsChecking(true);
      
      try {
        const response = await fetch('/api/v1/toxicity/check', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ text, language: currentLanguage })
        });
        
        const data = await response.json();
        
        if (data.is_toxic) {
          setSpeedBumpData(data);
        }
      } catch (error) {
        console.error('Speed bump check failed:', error);
        // Fail open - don't block message on error
      } finally {
        setIsChecking(false);
      }
    }, 500),
    [currentLanguage]
  );
  
  const dismissSpeedBump = (action: 'edited' | 'sent_anyway') => {
    // Log user decision for ML training
    logSpeedBumpOutcome({
      messageHash: hashMessage(speedBumpData.original_text),
      categories: speedBumpData.categories,
      action,
      timestamp: Date.now()
    });
    
    setSpeedBumpData(null);
  };
  
  return { checkMessage, isChecking, speedBumpData, dismissSpeedBump };
};
```

### Message Send Flow

```typescript
const handleSendMessage = async (text: string) => {
  // 1. Check for toxicity
  const toxicityCheck = await checkMessage(text);
  
  if (toxicityCheck.is_toxic) {
    // 2. Show Speed Bump
    const userDecision = await showSpeedBump(toxicityCheck);
    
    if (userDecision === 'edit') {
      // User chose to edit
      return; // Keep in draft state
    } else if (userDecision === 'sent_anyway') {
      // User chose to send anyway
      await logOverride(toxicityCheck);
      // Continue to send
    }
  }
  
  // 3. Send message (either approved or non-toxic)
  await sendMessage(text);
};
```

---

## 🗄️ Database Schema

```sql
-- Speed Bump interventions log
CREATE TABLE speed_bump_interventions (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID REFERENCES users(id),
  conversation_id VARCHAR(255),
  message_hash VARCHAR(64) NOT NULL, -- SHA-256 of message (not plaintext)
  detected_categories JSONB NOT NULL,
  highest_confidence DECIMAL(5,4),
  severity VARCHAR(20) CHECK (severity IN ('low', 'medium', 'high', 'critical')),
  user_action VARCHAR(20) CHECK (user_action IN ('edited', 'sent_anyway', 'cancelled')),
  time_to_decision_ms INTEGER, -- How long user took to decide
  suggestion_used BOOLEAN,
  suggestion_modified BOOLEAN,
  created_at TIMESTAMP DEFAULT NOW(),
  INDEX idx_user_interventions (user_id, created_at DESC),
  INDEX idx_user_action (user_id, user_action),
  INDEX idx_categories (detected_categories)
);

-- Rephrase suggestions generated
CREATE TABLE rephrase_suggestions (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  intervention_id UUID REFERENCES speed_bump_interventions(id),
  original_hash VARCHAR(64),
  suggestion_text_hash VARCHAR(64), -- Don't store plaintext
  was_accepted BOOLEAN DEFAULT false,
  was_modified BOOLEAN DEFAULT false,
  generation_time_ms INTEGER,
  model_version VARCHAR(50),
  created_at TIMESTAMP DEFAULT NOW()
);

-- User Speed Bump stats (for parental dashboard)
CREATE TABLE user_speed_bump_stats (
  user_id UUID PRIMARY KEY REFERENCES users(id),
  total_interventions INTEGER DEFAULT 0,
  interventions_edited INTEGER DEFAULT 0,
  interventions_sent_anyway INTEGER DEFAULT 0,
  last_intervention_at TIMESTAMP,
  intervention_rate DECIMAL(5,4), -- Interventions per message sent
  edit_acceptance_rate DECIMAL(5,4), -- % of times user chose to edit
  updated_at TIMESTAMP DEFAULT NOW()
);

-- Model performance tracking
CREATE TABLE ml_model_performance (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  model_version VARCHAR(50),
  message_hash VARCHAR(64),
  predicted_toxic BOOLEAN,
  predicted_confidence DECIMAL(5,4),
  user_feedback VARCHAR(20), -- 'correct', 'false_positive', 'false_negative'
  feedback_provided_at TIMESTAMP,
  INDEX idx_model_version (model_version, feedback_provided_at)
);
```

---

## 🔌 API Specifications

### Check Message Toxicity

```http
POST /api/v1/toxicity/check

Headers:
  Authorization: Bearer {jwt_token}
  Content-Type: application/json

Body:
{
  "text": "אתה ממש מטומטם למה לא הבנת את זה",
  "language": "he",
  "context": {
    "conversation_id": "chat_123",
    "conversation_type": "group",
    "participant_count": 5
  }
}

Response 200 (Toxic):
{
  "is_toxic": true,
  "confidence": 0.89,
  "categories": [
    {
      "name": "belittling",
      "confidence": 0.89,
      "explanation": "Message contains language that may diminish someone"
    },
    {
      "name": "aggression",
      "confidence": 0.45,
      "explanation": "Mild aggressive tone detected"
    }
  ],
  "severity": "high",
  "suggestions": [
    {
      "text": "אולי לא הסברתי טוב, בוא ננסה שוב?",
      "reasoning": "Transforms blame into self-reflection and invitation to collaborate",
      "confidence": 0.92
    },
    {
      "text": "בוא נעבור על זה ביחד, אולי יותר ברור",
      "reasoning": "Focuses on problem-solving rather than criticism",
      "confidence": 0.88
    }
  ],
  "intervention_id": "uuid",
  "processing_time_ms": 245
}

Response 200 (Not Toxic):
{
  "is_toxic": false,
  "confidence": 0.12,
  "categories": [],
  "severity": "none",
  "processing_time_ms": 180
}

Response 429 (Rate Limited):
{
  "error": "RATE_LIMIT_EXCEEDED",
  "message": "Too many checks in a short time",
  "retry_after_seconds": 5
}
```

### Log User Decision

```http
POST /api/v1/toxicity/intervention/{intervention_id}/outcome

Headers:
  Authorization: Bearer {jwt_token}
  Content-Type: application/json

Body:
{
  "action": "edited", // or "sent_anyway" or "cancelled"
  "time_to_decision_ms": 8430,
  "suggestion_used": true,
  "suggestion_modified": false,
  "final_message_hash": "sha256_hash" // Optional
}

Response 200:
{
  "success": true,
  "vibe_points_awarded": 15, // If action was "edited"
  "new_total_vp": 1265
}
```

### Provide Feedback (Optional)

```http
POST /api/v1/toxicity/feedback

Headers:
  Authorization: Bearer {jwt_token}
  Content-Type: application/json

Body:
{
  "intervention_id": "uuid",
  "feedback": "false_positive", // or "correct" or "false_negative"
  "comment": "This was actually a joke between friends"
}

Response 200:
{
  "success": true,
  "message": "Thank you for helping us improve"
}
```

---

## 🧪 Testing Requirements

### Unit Tests

```javascript
describe('Toxicity Detection', () => {
  test('should detect belittling language', async () => {
    const result = await checkToxicity('You\'re so stupid');
    expect(result.is_toxic).toBe(true);
    expect(result.categories).toContainEqual(
      expect.objectContaining({ name: 'belittling' })
    );
  });

  test('should not flag friendly banter', async () => {
    const result = await checkToxicity('lol you\'re crazy 😂');
    expect(result.is_toxic).toBe(false);
  });

  test('should handle Hebrew correctly', async () => {
    const result = await checkToxicity('אתה מטומטם', 'he');
    expect(result.is_toxic).toBe(true);
  });

  test('should generate appropriate suggestions', async () => {
    const suggestions = await generateSuggestions(
      'You never listen to me!',
      [{ name: 'manipulation', confidence: 0.8 }]
    );
    expect(suggestions.length).toBeGreaterThan(0);
    expect(suggestions[0].text).not.toContain('never');
  });
});
```

### False Positive Testing

```javascript
// Test cases that should NOT trigger
const FALSE_POSITIVE_TESTS = [
  "You're crazy talented at this! 🔥",
  "That's insane how good you are",
  "I'm dying laughing 😂",
  "This is sick! (positive)",
  "אתה משוגע איזה כיף!" // Hebrew positive slang
];

describe('False Positive Prevention', () => {
  FALSE_POSITIVE_TESTS.forEach(text => {
    test(`should not flag: "${text}"`, async () => {
      const result = await checkToxicity(text);
      expect(result.is_toxic).toBe(false);
    });
  });
});
```

### Performance Tests

```javascript
describe('Speed Bump Performance', () => {
  test('should detect toxicity in < 300ms', async () => {
    const start = Date.now();
    await checkToxicity('You\'re so dumb');
    const duration = Date.now() - start;
    expect(duration).toBeLessThan(300);
  });

  test('should handle 100 concurrent checks', async () => {
    const promises = Array(100).fill().map(() =>
      checkToxicity('Test message')
    );
    await expect(Promise.all(promises)).resolves.toBeDefined();
  });
});
```

### A/B Testing

```javascript
// Test different intervention styles
const AB_TEST_VARIATIONS = {
  A: { // Gentle approach
    title: "Wait a moment...",
    tone: "suggestive"
  },
  B: { // Direct approach
    title: "This message may be hurtful",
    tone: "direct"
  },
  C: { // Peer pressure approach
    title: "87% of users would edit this",
    tone: "social_proof"
  }
};

// Track which variation performs best
// Metrics: Edit rate, user satisfaction, speed to decision
```

---

## 🎯 Success Metrics

### Product Metrics

**Intervention Effectiveness:**
- Edit rate: > 70% of users choose "Edit Message"
- Re-intervention rate: < 10% (users don't try to send toxic again after editing)
- User satisfaction: > 4/5 stars when prompted for feedback
- False positive rate: < 5% based on user feedback

**Behavior Change:**
- Reduction in toxic messages sent: 60% decrease after 30 days
- Users avoiding toxic language proactively: +45%
- Improved conversation harmony scores: +15%

**Engagement:**
- Users reading suggestions: 85%
- Users accepting AI suggestions: 40%
- Users modifying AI suggestions: 35%
- Time to decision: Average 8-12 seconds

### Technical Metrics

- Detection latency (P95): < 300ms
- Suggestion generation time (P95): < 500ms
- Model accuracy: > 85% precision, > 80% recall
- API uptime: 99.95%
- False positive rate: < 5%
- False negative rate: < 15%

---

## 🚧 Edge Cases & Handling

### Context-Dependent Messages

```javascript
// Challenge: Same words, different intent
const CONTEXT_EXAMPLES = {
  sarcasm: {
    message: "Oh great, that's just perfect 🙄",
    context: "Recent message was about something bad",
    should_trigger: false // Sarcasm, not actual toxicity
  },
  
  inside_joke: {
    message: "You're such an idiot lol",
    context: "Between close friends with positive history",
    should_trigger: false // Friendly banter
  },
  
  venting: {
    message: "I'm so angry at this situation!",
    context: "About external event, not person",
    should_trigger: false // Self-expression, not attacking
  },
  
  actual_toxicity: {
    message: "You're such an idiot",
    context: "Directed at person, no positive indicators",
    should_trigger: true // Actual belittling
  }
};

// Solution: Context-aware model
const checkWithContext = async (message, conversationHistory) => {
  const context_embedding = await getContextEmbedding(conversationHistory);
  const prediction = await model.predict(message, context_embedding);
  return prediction;
};
```

### Multi-Language & Code-Switching

```javascript
// Handle messages mixing Hebrew and English
const MIXED_LANGUAGE = {
  example: "אתה ממש stupid honestly",
  solution: "Detect language per word/phrase, analyze separately"
};
```

### Emoji-Heavy Messages

```javascript
// Emojis can completely change meaning
const EMOJI_CONTEXT = {
  toxic_with_emoji: "You're so dumb 😂", // Might be joking
  toxic_without: "You're so dumb", // More likely serious
  solution: "Emoji sentiment analysis as modifier"
};
```

---

## 🔒 Privacy & Ethics

### Ethical Considerations

1. **User Autonomy**
   - Always allow "Send Anyway" option
   - Never block messages completely
   - Explain reasoning transparently

2. **No Shaming**
   - Private intervention (not visible to others)
   - Positive framing ("Maybe try this...")
   - No judgment language

3. **Cultural Sensitivity**
   - Different cultures have different norms
   - Hebrew slang vs. English slang
   - Age-appropriate detection

4. **Avoiding Bias**
   - Equal detection across languages
   - No discrimination by communication style
   - Regular bias audits

### Privacy Protection

```javascript
const PRIVACY_RULES = {
  message_storage: {
    plaintext: false, // Never store original messages
    hashed: true, // Only SHA-256 hashes
    retention: '90 days', // Then delete
  },
  
  ml_training: {
    anonymization: 'required',
    opt_in: true,
    user_control: 'can delete their data anytime'
  },
  
  parental_access: {
    view_counts: true, // Parent can see # of interventions
    view_content: false, // Parent CANNOT see message content
    view_categories: true // Parent can see types (e.g., "belittling")
  }
};
```

---

## 🔄 Future Enhancements (V2+)

1. **Emotion Detection**
   - Detect user's emotional state from typing patterns
   - Adjust intervention tone accordingly

2. **Personalized Suggestions**
   - Learn user's communication style
   - Generate suggestions that sound like them

3. **Conversation Repair**
   - Suggest follow-up messages to repair harm
   - "It looks like that message upset someone. Want to reach out?"

4. **Proactive Warnings**
   - Detect when conversation is getting heated
   - Intervene before toxic message is typed

5. **Multi-Party Context**
   - Consider all participants' perspectives
   - Detect bullying patterns across multiple messages

---

## ✅ Definition of Done

- [ ] ML model trained on 50K+ labeled messages per language
- [ ] Detection accuracy: >85% precision, >80% recall
- [ ] Suggestion generation working for all categories
- [ ] UI components built and animated smoothly
- [ ] API endpoints tested and documented
- [ ] Latency: Detection <300ms, Suggestions <500ms
- [ ] Privacy review passed
- [ ] Bias audit completed
- [ ] False positive rate <5%
- [ ] Edit acceptance rate >70% in user testing
- [ ] Localized in Hebrew and English
- [ ] Analytics tracking implemented
- [ ] Parent dashboard integration complete
- [ ] 100+ teen user testing sessions completed

---

**Document Version:** 1.0  
**Author:** Product Team  
**Last Updated:** February 2026  
**Status:** Ready for Development
