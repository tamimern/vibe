# WhatsApp Business Bot - Violence Monitor

A WhatsApp Business API bot that monitors group messages for violent content and takes action.

## Features

- Uses official WhatsApp Business API
- Can be added to WhatsApp groups
- Monitors all messages in groups
- Detects violent content using AI analysis
- Takes automated actions (warnings, moderation)

## Prerequisites

- WhatsApp Business Account
- WhatsApp Business API access (Meta Business)
- Node.js 18+
- API credentials from Meta

## Setup

1. Install dependencies:
```bash
npm install
```

2. Configure `.env` file:
```bash
cp .env.example .env
# Edit .env with your credentials
```

3. Run the bot:
```bash
npm start
```

## Configuration

See `.env.example` for required configuration.

## How It Works

1. Bot is added to a WhatsApp group
2. Bot monitors all messages in the group
3. When a message is detected, it's analyzed for violent content
4. If violence is detected, bot takes action (warns, reports, etc.)
