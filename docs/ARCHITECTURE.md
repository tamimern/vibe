# Project Architecture - Two-Level Separation

## Overview

This project follows a **two-level architecture** that separates content analysis logic from platform-specific implementations. This design allows us to easily add new messaging platforms while keeping the violence detection logic consistent and reusable.

## Architecture Levels

```
┌─────────────────────────────────────────────────────────┐
│                    Application Layer                     │
│              (UI, API Routes, Controllers)               │
└─────────────────────────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────┐
│              Level 2: Platform Connection Layer          │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │   WhatsApp   │  │   Telegram   │  │   Signal     │  │
│  │   Adapter    │  │   Adapter    │  │   Adapter    │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└─────────────────────────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────┐
│         Level 1: Content Analysis Layer                  │
│  ┌──────────────────────────────────────────────────┐   │
│  │         Violence Detection Service               │   │
│  │  - Message Analysis                              │   │
│  │  - Pattern Detection                             │   │
│  │  - Conversation Context Analysis                  │   │
│  └──────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────┐
│              External Services & Storage                 │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │    OpenAI    │  │   Database   │  │   Analytics   │  │
│  │   (AI/ML)    │  │   (History)  │  │   (Reports)  │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└─────────────────────────────────────────────────────────┘
```

---

## Level 1: Content Analysis Layer

**Purpose:** Platform-agnostic content analysis and violence detection

**Responsibilities:**
- Analyze individual messages for violent content
- Detect patterns in conversations over time
- Provide violence detection results with confidence scores
- Maintain conversation context and history
- Generate analysis reports

**Key Components:**

### 1.1 Violence Detection Service
- **Input:** Message text, sender info, conversation context
- **Output:** Detection result (hasViolence, category, confidence, matches)
- **Methods:**
  - `analyzeMessage(message: GenericMessage): Promise<AnalysisResult>`
  - `detectPatterns(conversationHistory: Message[]): Promise<PatternResult>`
  - `getConversationAnalysis(chatId: string): Promise<ConversationAnalysis>`

### 1.2 Message Processor
- Processes messages through the analysis pipeline
- Handles message normalization and preprocessing
- Manages conversation context

### 1.3 Pattern Detector
- Detects escalation patterns
- Identifies repeated violent behavior
- Tracks conversation trends

**Interface:**
```typescript
interface GenericMessage {
  platform: 'whatsapp' | 'telegram' | 'signal' | string;
  messageId: string;
  chatId: string;
  senderId: string;
  senderName?: string;
  text: string;
  timestamp: number;
  metadata?: Record<string, any>;
}

interface AnalysisResult {
  hasViolence: boolean;
  category?: 'physical' | 'verbal' | 'threat' | 'pattern' | string;
  confidence: number; // 0.0 to 1.0
  matches: string[];
  explanation?: string;
  recommendation?: string;
}

interface PatternResult {
  hasPattern: boolean;
  patternType?: 'escalation' | 'repetition' | 'intensity' | string;
  severity: 'low' | 'medium' | 'high';
  evidence: string[];
}
```

---

## Level 2: Platform Connection Layer

**Purpose:** Platform-specific implementations for connecting to messaging services

**Responsibilities:**
- Handle platform-specific authentication
- Connect to messaging platforms (WhatsApp, Telegram, etc.)
- Receive messages from platforms
- Send messages/responses to platforms
- Manage platform-specific sessions
- Convert platform messages to generic format

**Key Components:**

### 2.1 Platform Adapter Interface
All platform adapters must implement this interface:

```typescript
interface IPlatformAdapter {
  name: string;
  displayName: string;
  
  // Connection
  connect(): Promise<void>;
  disconnect(): Promise<void>;
  isConnected(): boolean;
  
  // Message handling
  onMessage(callback: (message: GenericMessage) => void): void;
  sendMessage(chatId: string, text: string, options?: SendOptions): Promise<void>;
  
  // QR/Login
  generateQR(): Promise<string | null>;
  getConnectionStatus(): PlatformStatus;
  
  // Group management (if supported)
  isGroupMessage(message: GenericMessage): boolean;
  canModerate(chatId: string): Promise<boolean>;
}
```

### 2.2 Platform Implementations

#### WhatsApp Adapter
- Uses WhatsApp Business API (official)
- Handles webhook setup
- Manages Business API credentials
- Supports group message monitoring

#### Telegram Adapter
- Uses Telethon (user account) or Bot API
- Handles QR code login
- Manages 2FA authentication
- Supports both user and bot modes

#### Future Platforms
- Signal Adapter
- Discord Adapter
- etc.

---

## Data Flow

### Message Flow (Incoming)
```
Platform Message
    ↓
Platform Adapter (Level 2)
    ↓
Convert to GenericMessage
    ↓
Content Analysis Layer (Level 1)
    ↓
Violence Detection
    ↓
Analysis Result
    ↓
Platform Adapter (Level 2)
    ↓
Send Response/Warning
```

### Example Flow:
1. **WhatsApp Group Message Received**
   - WhatsApp Adapter receives webhook
   - Converts to `GenericMessage` format
   - Passes to Content Analysis Layer

2. **Content Analysis**
   - Violence Detection Service analyzes message
   - Checks conversation history for patterns
   - Returns `AnalysisResult`

3. **Action Taken**
   - If violence detected, Platform Adapter sends warning
   - Logs incident to database
   - Updates conversation analysis

---

## Directory Structure

```
project-root/
├── src/
│   ├── services/              # Level 1: Content Analysis
│   │   ├── violence-detector.ts
│   │   ├── pattern-detector.ts
│   │   ├── conversation-analyzer.ts
│   │   └── message-processor.ts
│   │
│   ├── platforms/              # Level 2: Platform Connections
│   │   ├── interfaces/
│   │   │   └── IPlatformAdapter.ts
│   │   ├── whatsapp/
│   │   │   ├── WhatsAppAdapter.ts
│   │   │   └── WhatsAppBusinessAPI.ts
│   │   ├── telegram/
│   │   │   ├── TelegramAdapter.ts
│   │   │   └── TelegramUserManager.ts
│   │   └── index.ts
│   │
│   ├── handlers/               # Message Handlers
│   │   └── message-handler.ts
│   │
│   └── index.ts                # Main Application
│
├── server/                     # Backend API
│   ├── routes/
│   │   └── platforms.ts
│   └── index.ts
│
└── ARCHITECTURE.md             # This file
```

---

## Benefits of This Architecture

### 1. **Separation of Concerns**
- Content analysis logic is independent of platform
- Platform-specific code is isolated
- Easy to test each layer independently

### 2. **Extensibility**
- Add new platforms by implementing `IPlatformAdapter`
- No need to rewrite violence detection logic
- New platforms automatically get all analysis features

### 3. **Maintainability**
- Changes to violence detection affect all platforms
- Platform-specific bugs don't affect analysis logic
- Clear boundaries between components

### 4. **Reusability**
- Violence detection can be used by any platform
- Analysis logic can be shared across projects
- Easy to create platform-agnostic tests

---

## Implementation Strategy

### Phase 1: Refactor Existing Code
1. Extract violence detection logic from platform-specific code
2. Create `ViolenceDetector` service (Level 1)
3. Create `IPlatformAdapter` interface (Level 2)
4. Refactor WhatsApp code to use adapter pattern
5. Refactor Telegram code to use adapter pattern

### Phase 2: Create Unified Handler
1. Create `MessageHandler` that works with any platform
2. Connect Level 1 and Level 2
3. Implement unified message processing pipeline

### Phase 3: Add New Platforms
1. Implement adapter for new platform
2. Register adapter in platform manager
3. Test with existing analysis logic

---

## Example Usage

```typescript
// Level 2: Platform Connection
const whatsappAdapter = new WhatsAppAdapter(config);
const telegramAdapter = new TelegramAdapter(config);

// Level 1: Content Analysis
const violenceDetector = new ViolenceDetector(openaiConfig);
const patternDetector = new PatternDetector();

// Unified Handler
const messageHandler = new MessageHandler(
  [whatsappAdapter, telegramAdapter],
  violenceDetector,
  patternDetector
);

// Setup
whatsappAdapter.onMessage((message) => {
  messageHandler.processMessage(message);
});

telegramAdapter.onMessage((message) => {
  messageHandler.processMessage(message);
});
```

---

## Next Steps

1. **Create Level 1 Services**
   - `ViolenceDetector` service
   - `PatternDetector` service
   - `ConversationAnalyzer` service

2. **Create Level 2 Interfaces**
   - `IPlatformAdapter` interface
   - Platform manager/registry

3. **Refactor Existing Code**
   - Move WhatsApp logic to adapter
   - Move Telegram logic to adapter
   - Extract violence detection to Level 1

4. **Create Unified Handler**
   - Connect Level 1 and Level 2
   - Implement message processing pipeline

---

## Notes

- **Level 1 is platform-agnostic** - it doesn't know about WhatsApp, Telegram, etc.
- **Level 2 is platform-specific** - each adapter handles one platform
- **Communication between levels** uses generic interfaces
- **New platforms** only need to implement Level 2 adapter
- **Violence detection improvements** automatically benefit all platforms
