# Telegram User Monitor (QR Login)

Monitors all your Telegram chats/groups using your user account (not a bot). Uses QR login and flags/deletes messages with violent terms.

> Bots cannot read your private chats or groups unless they are added. A userbot (this project) logs in as you and can monitor your dialogs. Secret chats are not supported by any API.

## Prerequisites

- Python 3.10+ (Windows has `py` launcher)
- Telegram API ID and Hash from `https://my.telegram.org/apps` (create an app)

## Setup

```powershell
cd telegram-user-monitor
Copy-Item .\\env.example .\\.env
# Edit .env and set TELEGRAM_API_ID and TELEGRAM_API_HASH

py -m venv .venv
.\\.venv\\Scripts\\Activate.ps1
pip install -r requirements.txt
```

## Run and Login (QR)

```powershell
python monitor.py
```

- A QR will print in the terminal and `login_qr.png` will be saved.
- On your phone: Telegram → Settings → Devices → Link Desktop Device → scan the QR.
- After login, the script will start monitoring all your dialogs.

## What it does

- Listens to all new messages (non-secret chats).
- If a message contains violent terms (see `VIOLENCE_TERMS` in `monitor.py`):
  - Tries to delete it (if your account has permission, e.g., admin in a group).
  - Otherwise sends a warning in the chat.

## Notes

- Deletion requires admin rights in groups/channels.
- Secret chats are not supported by Telegram's API; they cannot be monitored.
- You can tune the terms in `monitor.py`.

## Stop

Press Ctrl+C in the terminal.


