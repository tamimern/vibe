# VibeAccessibilityService Implementation

## Overview
The `VibeAccessibilityService` is the core component that monitors WhatsApp to detect messages and analyze sentiment in real-time.

## Features Implemented

### ✅ WhatsApp Detection
- Detects when WhatsApp (`com.whatsapp`) comes to foreground
- Monitors window state changes using `TYPE_WINDOW_STATE_CHANGED` events
- Tracks foreground state to avoid unnecessary processing

### ✅ Message Bubble Detection
- Identifies message bubbles using multiple heuristics:
  - TextView nodes with text content
  - Visible nodes with reasonable bounds (not too small/large)
  - Nodes within RecyclerView/ListView containers (typical WhatsApp structure)
  - Clickable nodes (messages are typically selectable)
- Recursively searches the accessibility tree to find all message bubbles

### ✅ Message Direction Detection
- Determines if messages are **incoming** (left side) or **outgoing** (right side)
- Uses screen position heuristic: messages on right side (>60% of screen width) are outgoing
- This helps distinguish between messages from the user vs. others

### ✅ Input Field Monitoring
- Detects EditText nodes that represent the WhatsApp input field
- Monitors real-time text changes for sentiment analysis
- Will trigger "Speed Bump" intervention if toxic/aggressive content is detected

### ✅ Privacy Compliance
- **NO raw text storage** - text is immediately converted to sentiment tokens
- All analysis happens locally
- `ViewNodeAnalyzer` ensures text is discarded after sentiment extraction

## Architecture

```
VibeAccessibilityService
├── onServiceConnected() - Initializes service configuration
├── onAccessibilityEvent() - Main event handler
│   ├── handleWindowStateChanged() - Detects WhatsApp open/close
│   ├── handleWindowContentChanged() - Detects new messages
│   └── handleTextChanged() - Monitors input field
├── findMessageBubbles() - Recursively searches for message bubbles
├── isMessageBubble() - Heuristic to identify message bubbles
├── determineMessageDirection() - Left/Right detection
└── processMessageBubble() - Extracts and analyzes messages

ViewNodeAnalyzer
├── analyzeMessage() - Converts text to sentiment tokens
└── analyzeSentimentPlaceholder() - Placeholder sentiment logic (TODO: ML integration)
```

## Configuration

### AndroidManifest.xml
The service must be declared with:
- `BIND_ACCESSIBILITY_SERVICE` permission
- `accessibility_service_config.xml` meta-data
- Intent filter for `android.accessibilityservice.AccessibilityService`

### accessibility_service_config.xml
- Monitors only `com.whatsapp` package
- Listens for window state/content changes and text changes
- Retrieves window content for analysis

## Next Steps (TODOs)

1. **Sentiment Analysis Integration**
   - Replace `analyzeSentimentPlaceholder()` with TensorFlow Lite / ML Kit
   - Implement proper sentiment scoring (0.0-1.0)

2. **NudgeEngine Integration**
   - Connect `onInputFieldChanged()` to trigger "Speed Bump" overlay
   - Implement intervention logic (Score > 0.8 triggers overlay)

3. **Vibe Score Calculation**
   - Implement rolling 7-day average
   - Formula: `VibeScore = (PositiveCount * 2) - (ToxicCount * 5) / TotalMessages`

4. **OverlayManager Integration**
   - Connect to show VibeBubble (floating indicator)
   - Show Nudge Card when toxic content detected

5. **Database Integration**
   - Store sentiment tokens (not raw text) in encrypted Room database
   - Track Vibe Score over time

## Testing

To test the service:

1. **Enable Accessibility Service**
   - Go to Settings > Accessibility > Vibe
   - Enable the service
   - Grant all required permissions

2. **Open WhatsApp**
   - The service should log: `📱 WhatsApp opened - now monitoring`

3. **Send/Receive Messages**
   - Check logs for: `💬 Message detected`
   - Verify direction detection (incoming vs outgoing)

4. **Type in Input Field**
   - Check logs for: `⌨️ Input field changed`
   - Verify real-time monitoring

## Known Limitations

- **WhatsApp UI Changes**: WhatsApp frequently updates their UI. The heuristics may need adjustment for different WhatsApp versions.
- **Message Bubble Detection**: The current heuristics work for typical WhatsApp layouts but may need refinement based on testing.
- **Performance**: Recursive tree traversal on every content change could impact performance. Consider debouncing or optimization.

## Privacy Notes

⚠️ **CRITICAL**: This service must NEVER:
- Store raw message text
- Transmit raw text to external servers
- Log full message content

✅ **ONLY** store/transmit:
- Sentiment tokens (category + score)
- Timestamps
- Message direction (incoming/outgoing)
- Metadata (no PII)
