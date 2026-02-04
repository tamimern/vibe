# Telegram Bot MVP

Minimal Telegram bot using long polling via `telegraf`.

## Setup

1) Create a bot with `@BotFather` in Telegram and copy the HTTP API token.
2) Create a `.env` file in this folder with:

```
BOT_TOKEN=YOUR_TELEGRAM_BOT_TOKEN_HERE
```

Alternatively, copy from `env.example` and rename to `.env`.

## Install

```
npm install
```

## Run (local polling)

```
npm start
```

Then open your bot in Telegram, send `/start` and any text to see echoes.

## QR links

- Start chat QR: send `/qr` in the bot chat to receive a QR that opens a 1:1 chat.
- Add-to-group QR: send `/groupqr` to receive a QR that opens the “add bot to group” flow.

## Group moderation (violence filter)

The bot flags and attempts to remove messages containing potentially violent terms.

To let the bot read and moderate group messages:

1) In `@BotFather`, run `/setprivacy` → choose your bot → `Disable` (so the bot can read all messages in groups).
2) Add the bot to your group and make it an admin with the permission to delete messages.

Notes:
- If the bot can’t delete, it will still warn in the chat.
- You can adjust the keywords list in `index.js` (`violenceTerms`).



