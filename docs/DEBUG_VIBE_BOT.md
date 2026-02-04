# Debugging Vibe Bot Connection Issues

## Current Problem
- "Failed to fetch" error in UI
- QR code not appearing in server terminal

## What I Fixed

### 1. Added Extensive Logging
- Server route now logs every step
- WhatsAppAdapter logs connection progress
- Frontend hook logs API calls

### 2. Better Error Messages
- More descriptive error messages
- Error details shown in UI
- Instructions to check server terminal

### 3. Improved Status Polling
- Polls every 2 seconds
- Shows current status in console
- Handles connection timeouts

## How to Debug

### Step 1: Check Server Terminal
When you click "Connect to WhatsApp", you should see:
```
[Vibe API] /connect endpoint called
[Vibe API] Calling vibeBot.connect()...
[WhatsApp] Starting connection process...
[WhatsApp] Creating auth folder: auth/whatsapp-bot
[WhatsApp] Auth folder created/verified
[WhatsApp] Loading auth state...
[WhatsApp] Auth state loaded
[WhatsApp] Creating WhatsApp socket...
[WhatsApp] Socket created successfully
[WhatsApp] Connection setup complete, waiting for QR/connection...
[WhatsApp] Connection update: { connection: 'connecting', hasQR: true, qrLength: 123 }
[WhatsApp] QR code received, emitting event
```

### Step 2: Check Browser Console
You should see:
```
[useVibeBot] Connect called
[useVibeBot] Sending POST to http://localhost:4000/api/vibe/connect
[useVibeBot] Response status: 200 OK
[useVibeBot] Success response: { message: 'Connecting...', status: 'CONNECTING' }
[useVibeBot] Polling status (attempt 1)...
```

### Step 3: Common Issues

**Issue: "Failed to fetch"**
- Server not running? Check: `npm run dev:server`
- Wrong port? Check API_URL in `src/config.ts` (should be `http://localhost:4000`)
- CORS issue? Check server logs

**Issue: No QR code in terminal**
- Check if `printQRInTerminal: true` is set (it is)
- Check if Baileys is installed: `npm list @whiskeysockets/baileys`
- Check server terminal for errors

**Issue: Connection stuck on "CONNECTING"**
- QR code might be generated but not scanned
- Check server terminal for QR code
- Try disconnecting and reconnecting

## Next Steps

1. **Restart the server** to pick up new logging:
   ```bash
   # Stop current server (Ctrl+C)
   npm run dev:server
   ```

2. **Try connecting again** and watch both:
   - Server terminal (for QR code and backend logs)
   - Browser console (for frontend logs)

3. **Share the logs** from both places so I can see exactly where it's failing
