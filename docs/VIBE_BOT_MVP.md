# Vibe Bot MVP - Quick Start Guide

## Overview

MVP implementation of Vibe Bot that monitors WhatsApp groups for violent content.

## Architecture

- **Level 1**: Content Analysis Layer (`src/services/violence-detector.ts`)
  - Detects violence using keywords (clear violence)
  - Optional AI analysis with OpenAI

- **Level 2**: Platform Connection Layer (`src/platforms/whatsapp/WhatsAppAdapter.ts`)
  - Uses Baileys to connect to WhatsApp
  - Handles group joining via invite links
  - Monitors and sends messages

## Setup

1. **Install dependencies** (if not already installed):
```bash
npm install
```

2. **Configure environment** (optional - for AI analysis):
```bash
# Add to .env file
OPENAI_API_KEY=your_openai_api_key_here
```

3. **Build the project**:
```bash
npm run build
```

## Usage

### Via API

1. **Connect to WhatsApp**:
```bash
POST http://localhost:4000/api/vibe/connect
```

2. **Scan QR Code** (if needed):
   - Check server logs for QR code
   - Scan with WhatsApp on your phone

3. **Join a Group**:
```bash
POST http://localhost:4000/api/vibe/join-group
Content-Type: application/json

{
  "groupLink": "https://chat.whatsapp.com/YOUR_INVITE_CODE"
}
```

4. **Check Status**:
```bash
GET http://localhost:4000/api/vibe/status
```

### How It Works

1. Bot connects to WhatsApp using Baileys
2. User provides WhatsApp group invite link
3. Bot joins the group
4. Bot monitors all messages in the group
5. When violence is detected:
   - Bot sends: "I recognize violence in {message text}"
   - Message is replied to the original message

## Violence Detection

- **Step 1**: Keyword matching (clear violence)
  - Detects terms like: kill, murder, attack, etc.
  - If keywords found → triggers response

- **Step 2**: AI Analysis (if OpenAI API key provided)
  - Additional context analysis
  - Better categorization

## Response Format

When violence is detected, bot sends:
```
I recognize violence in {original message text}
```

## API Endpoints

- `GET /api/vibe/status` - Get bot connection status
- `POST /api/vibe/connect` - Connect to WhatsApp
- `POST /api/vibe/join-group` - Join group via invite link
  - Body: `{ "groupLink": "https://chat.whatsapp.com/..." }`
- `POST /api/vibe/disconnect` - Disconnect from WhatsApp

## Testing

1. Start the server:
```bash
npm run dev:server
```

2. Connect to WhatsApp (scan QR if needed)

3. Get a WhatsApp group invite link

4. Join the group:
```bash
curl -X POST http://localhost:4000/api/vibe/join-group \
  -H "Content-Type: application/json" \
  -d '{"groupLink": "https://chat.whatsapp.com/YOUR_CODE"}'
```

5. Send a test message with violent content to the group
6. Bot should respond: "I recognize violence in {your message}"

## Notes

- Bot only monitors **group messages** (not private chats)
- Bot responds to **all detected violence** (no rate limiting in MVP)
- Response is sent as a **reply** to the original message
- Uses **Baileys** (user account, not Business API)
