# WhatsApp monitoring – how it works and how to test

Vibe monitors **WhatsApp** on the same device via an **Accessibility Service**. It does **not** store or send your messages; analysis runs locally.

## What is monitored

1. **When WhatsApp is open**  
   - A status notification: “Vibe – Monitoring WhatsApp”.  
   - Logs: `WhatsApp opened - now monitoring`.

2. **Message bubbles in the chat**  
   - Detects new messages (incoming and outgoing).  
   - Analyzes sentiment (toxic, aggressive, supportive, neutral) and violence (threat, hate, aggression).  
   - For **incoming** messages with violence, shows an alert notification.

3. **Compose box**  
   - Detects when you type in the “Type a message” field (for future “speed bump” / nudge features).

## How to test monitoring

1. **Enable Vibe**  
   - Settings → Accessibility → Vibe → **ON**.  
   - Allow notifications when the app asks (see [HOW_TO_ENABLE_VIBE_NOTIFICATIONS.md](../HOW_TO_ENABLE_VIBE_NOTIFICATIONS.md) if the toggle is blocked).

2. **Test the violence alert in the app**  
   - Open **Vibe** and tap **“Test violence alert”** (below “Send test notification”).  
   - You should get a **“Vibe: Violence detected”** notification (and often a heads-up). This confirms the Vibe Alerts channel works before testing with WhatsApp.

3. **Open WhatsApp**  
   - You should see a toast: “Vibe is monitoring WhatsApp” and the status notification.

4. **Open a chat**  
   - Open any conversation (1:1 or group).

5. **Trigger messages**  
   - Send a message from this device, or have someone send you a message (or use another device in the same chat).

6. **Check Logcat**  
   - In Android Studio: **View → Tool Windows → Logcat**.  
   - Filter by: `VibeAccessibilityService`.  
   - You should see lines like:  
     - `Message detected:`  
     - `Text: …`  
     - `Direction: INCOMING` or `OUTGOING`  
     - `Message analyzed - Sentiment: … Score: …`  
   - For violence keywords in an **incoming** message you should also get:  
     - `Violence detected in incoming message - showing notification`  
     - and a notification on the device.

7. **Test violence alert with WhatsApp**  
   - In a group (or a chat where someone else can send), have someone send a message that contains words like “hate”, “kill”, “attack”, “fight”, or the Hebrew keywords used in the analyzer.  
   - You should get a “Vibe: Violence detected” notification only when the message is classified as toxic or aggressive.

## If you don’t see “Message detected”

- **WhatsApp version**  
  Detection uses view hierarchy (e.g. RecyclerView, message-like containers). If your WhatsApp build uses a different UI, some messages might be missed.  
  - Try opening the chat, scrolling a bit, then sending a new message.  
  - Check Logcat for any `VibeAccessibilityService` errors.

- **Logcat filter**  
  Use exactly: `VibeAccessibilityService` (tag filter).  
  Ensure the device/emulator is selected and that WhatsApp is in the foreground when you send/receive.

- **Accessibility**  
  Confirm Vibe is still **ON** under Settings → Accessibility.

## Privacy

- Raw message text is **not** stored.  
- Analysis (sentiment, violence) is done in memory and only results (e.g. “violence: threat”) are used for notifications and logs.  
- No message content is sent off the device.

## Next steps (development)

- **Speed bump / nudge**: use input-field monitoring to warn before sending toxic or aggressive messages.  
- **Better detection**: tune `isMessageBubble()` and parent checks for your WhatsApp version if needed.  
- **ML sentiment**: replace placeholder sentiment with TensorFlow Lite or ML Kit if you add a model.
