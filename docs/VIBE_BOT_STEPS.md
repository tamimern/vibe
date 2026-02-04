# Vibe Bot - Step by Step Guide

## 🚀 Quick Start

### Step 1: Run the Server

Open a terminal and run:
```bash
npm run dev:server
```

You should see:
```
API listening on 0.0.0.0:4000
```

**Keep this terminal open!** The QR code will appear here.

---

### Step 2: Connect Your WhatsApp Account

**Option A: Using the UI (Recommended)**
1. Open your browser: `http://localhost:5173` (or your frontend URL)
2. Go to Dashboard
3. Look for "Vibe Bot - Group Monitor" section
4. Click **"Connect to WhatsApp"** button
5. **Check the server terminal** - you'll see a QR code printed there
6. Open WhatsApp on your phone
7. Go to: **Settings → Linked Devices → Link a Device**
8. Scan the QR code from the terminal
9. Wait for "Connected" status in the UI

**Option B: Using API (Advanced)**
```bash
# In a new terminal
curl -X POST http://localhost:4000/api/vibe/connect
```

Then check the server terminal for QR code and scan it.

---

### Step 3: Join a WhatsApp Group

**Once you see "Connected" status:**

1. **Get a group invite link:**
   - Open WhatsApp on your phone
   - Go to the group you want to monitor
   - Tap group name → **"Invite via Link"** → **"Copy Link"**
   - You'll get a link like: `https://chat.whatsapp.com/ABC123XYZ`

2. **Paste the link in the UI:**
   - In the "Vibe Bot - Group Monitor" section
   - Paste the link in the input field
   - Click **"Join Group"**

   **OR using API:**
   ```bash
   curl -X POST http://localhost:4000/api/vibe/join-group \
     -H "Content-Type: application/json" \
     -d '{"groupLink": "https://chat.whatsapp.com/YOUR_LINK_HERE"}'
   ```

3. **Success!** You should see: "Successfully joined group!"

---

### Step 4: Test It!

1. Go to the WhatsApp group on your phone
2. Send a test message with violent keywords, for example:
   - "I want to kill someone"
   - "Let's attack them"
   - "This is violent"

3. **The bot will automatically respond:**
   - Bot sends: "I recognize violence in {your message}"
   - It replies to your message

---

## 📋 Summary

```
1. Run server → npm run dev:server
2. Connect WhatsApp → Click "Connect" → Scan QR (in terminal)
3. Join group → Paste invite link → Click "Join Group"
4. Test → Send violent message → Bot responds automatically
```

---

## 🔍 Troubleshooting

**QR code not showing?**
- Check the server terminal (not browser console)
- Make sure server is running on port 4000
- Try disconnecting and reconnecting

**Can't join group?**
- Make sure you're connected first (status shows "Connected")
- Check that the invite link is valid
- Make sure the group allows new members

**Bot not responding?**
- Check server logs for errors
- Make sure bot successfully joined the group
- Verify the message contains violent keywords (kill, attack, murder, etc.)

**Connection lost?**
- WhatsApp may have logged you out
- Click "Disconnect" then "Connect" again
- Scan QR code again

---

## 📱 Where to Find Things

- **QR Code**: Server terminal (where you ran `npm run dev:server`)
- **Status**: UI Dashboard → "Vibe Bot - Group Monitor" section
- **Group Link**: WhatsApp → Group → Group Info → Invite via Link
- **Bot Response**: In the WhatsApp group chat

---

## 🎯 What the Bot Does

- ✅ Monitors all messages in the group
- ✅ Detects violent keywords (kill, attack, murder, etc.)
- ✅ Sends automatic response: "I recognize violence in {message}"
- ✅ Only monitors groups (not private chats)
- ✅ Responds to every detected violence (no rate limiting in MVP)

---

## 📞 Need Help?

Check the server terminal logs for detailed information about what's happening!
