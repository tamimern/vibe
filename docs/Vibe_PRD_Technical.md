# Project Vibe: MVP Technical Specification & Architecture

## 1. Project Overview
Vibe is an AI-driven digital empathy officer for Gen Alpha. It acts as an Android Accessibility Service layer over WhatsApp to monitor social sentiment, prevent cyberbullying via real-time nudges, and provide parents with a "Vibe Score" without compromising the child's message privacy.

## 2. Core Technical Stack
- *Platform:* Android (Kotlin)
- *Primary API:* Android Accessibility Service (Level 3/4 Monitoring)
- *UI Layer:* System Alert Window (Overlays)
- *On-Device NLP:* TensorFlow Lite / Google ML Kit / Localized Transformer model
- *Local DB:* Room / SQLCipher (Encrypted)
- *Backend (Optional for MVP):* Firebase (for Metadata-only reporting)

## 3. Implementation Requirements: Accessibility Service
The core of Vibe is an AccessibilityService that monitors com.whatsapp.

### Functional Specs:
- *Event Listeners:* - TYPE_WINDOW_STATE_CHANGED: Detect when WhatsApp is in foreground.
    - TYPE_WINDOW_CONTENT_CHANGED: Detect new messages or typing events.
- *Scraping Logic:* - Identify TextView nodes inside WhatsApp message bubbles.
    - Determine directionality (Left/Right) to distinguish between Incoming and Outgoing messages.
    - Monitor EditText content in the WhatsApp input field in real-time.
- *Privacy Barrier:* - *MUST NOT* store raw text strings. 
    - *MUST* process strings locally and convert them into "Sentiment Tokens" immediately.

## 4. NLP & Intervention Engine (The "Vibe Logic")
- *Sentiment Categories:* Toxic, Aggressive, Supportive, Neutral, Exclusive.
- *The "Speed Bump" Logic:*
    - If EditText sentiment is Toxic or Aggressive (Score > 0.8), trigger InterventionOverlay.
- *The "Vibe Score" Heuristic:*
    - Calculate a rolling 7-day average based on message sentiment frequency.
    - Formula: VibeScore = (PositiveCount * 2) - (ToxicCount * 5) / TotalMessages.

## 5. UI Components (Overlays)
- *VibeBubble (FloatingActionButton):* - A semi-transparent Overlay that changes color based on the current chat's health.
- *Nudge Card (The Speed Bump):* - A custom View drawn via WindowManager.
    - Contains: Warning message, AI Rephrasing suggestion, "Edit" button, and "Send Anyway" button.
- *Accessibility Setup Flow:* - A clean, step-by-step onboarding guide to help the user enable Accessibility and Overlay permissions.

## 6. Security & Permissions
- *Manifest Permissions:*
    - BIND_ACCESSIBILITY_SERVICE
    - SYSTEM_ALERT_WINDOW
    - INTERNET (Only for metadata sync)
- *Data Policy:* - Zero logging of PII (Personally Identifiable Information).
    - Metadata synced with parents includes: Score, Badge IDs, and Timestamp. *No Transcripts.*

## 7. Initial Project Structure
/app
  /src/main/java/com/vibe/app
    /accessibility
      - VibeAccessibilityService.kt (The Core)
      - ViewNodeAnalyzer.kt (Scraping logic)
    /nlp
      - SentimentAnalyzer.kt (TFLite integration)
      - NudgeEngine.kt (Decision logic)
    /ui
      - OverlayManager.kt (Drawing the Speed Bump)
      - DashboardActivity.kt (The "Victory Screen")
    /data
      - VibeDatabase.kt (Encrypted local storage)