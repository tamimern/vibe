# How to Check Logcat - Step by Step

## Step 1: Open Logcat

1. **Look at the bottom of Android Studio**
2. You should see tabs like: "Build", "Run", "Debug", "Logcat"
3. **Click on the "Logcat" tab**
4. If you don't see it, go to: **View → Tool Windows → Logcat**

---

## Step 2: Filter by VibeAccessibilityService

1. **In the Logcat window**, look for a **search/filter box** (usually at the top)
2. **Type**: `VibeAccessibilityService`
3. Press **Enter**
4. This will show only logs from our accessibility service

---

## Step 3: Check if WhatsApp is Detected

1. **Make sure WhatsApp is open** on the emulator (you already have it open! ✅)
2. **Look at Logcat** - you should see:
   - `✅ VibeAccessibilityService connected`
   - `📱 WhatsApp opened - now monitoring`
   - `Service configured for WhatsApp monitoring`

If you see these messages → **The service is working!** ✅

---

## Step 4: Test Message Detection

1. **In WhatsApp**, open any chat (tap on one of the chats)
2. **Send a test message** (type something and send it)
3. **Go back to Logcat**
4. You should see:
   - `💬 Message detected:`
   - `Text: [your message]...`
   - `Direction: INCOMING` or `OUTGOING`
   - `📊 Message analyzed - Sentiment: ...`

---

## Step 5: Test Input Field Monitoring

1. **In WhatsApp**, open a chat
2. **Type something in the input field** (but DON'T send it yet)
3. **Check Logcat** - you should see:
   - `⌨️ Input field changed: [what you typed]...`
   - `📝 Input field changed - should analyze sentiment`

---

## Troubleshooting

### If you don't see any logs:

1. **Check if the service is enabled:**
   - Go to Settings → Accessibility
   - Make sure "vibe" is **ON** (not OFF)

2. **Restart the service:**
   - Turn "vibe" OFF in Accessibility Settings
   - Turn it back ON
   - Open WhatsApp again

3. **Check the filter:**
   - Make sure the Logcat filter is set correctly
   - Try clearing the filter and searching for "Vibe" or "WhatsApp"

4. **Check device selection:**
   - In Logcat, make sure the correct device/emulator is selected
   - Look for a dropdown at the top of Logcat showing your emulator name

### If you see errors:

- Copy the error message and share it with me
- Common errors might be about permissions or service configuration

---

## What You Should See

**When WhatsApp opens:**
```
✅ VibeAccessibilityService connected
Service configured for WhatsApp monitoring
📱 WhatsApp opened - now monitoring
🎯 WhatsApp monitoring started
```

**When you send a message:**
```
💬 Message detected:
   Text: Hello...
   Direction: OUTGOING
   Bounds: Rect(...)
Analyzing message (5 chars, direction: OUTGOING)
📊 Message analyzed - Sentiment: NEUTRAL, Score: 0.5
```

**When you type in input field:**
```
⌨️ Input field changed: Hello...
📝 Input field changed - should analyze sentiment
```

---

## Quick Visual Guide

```
Android Studio
    ↓
[Bottom Panel]
    ↓
[Logcat Tab] ← Click here
    ↓
[Filter Box] ← Type: VibeAccessibilityService
    ↓
[See Logs] ← Watch for messages when you use WhatsApp
```
