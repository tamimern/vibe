# 🤖 How to Use Vibe Bot - Simple Guide

## What is Vibe Bot?

Vibe Bot monitors WhatsApp groups and automatically responds when it detects violent content.

---

## 🎯 3 Simple Steps

### 1️⃣ **Start the Server**

Open terminal and type:
```bash
npm run dev:server
```

**What happens:**
- Server starts on port 4000
- You'll see: `API listening on 0.0.0.0:4000`
- **Keep this terminal open!** (QR code appears here)

---

### 2️⃣ **Connect Your WhatsApp**

**In the Browser:**
1. Go to: `http://localhost:5173` (or your frontend URL)
2. Navigate to Dashboard
3. Scroll down to **"Vibe Bot - Group Monitor"** section
4. Click **"Connect to WhatsApp"** button

**On Your Phone:**
1. Open WhatsApp
2. Go to: **Settings** → **Linked Devices** → **Link a Device**
3. **Look at the server terminal** (where you ran `npm run dev:server`)
4. You'll see a QR code printed there
5. Scan it with your phone

**Wait for:**
- Status changes to **"Connected to WhatsApp"** ✅

---

### 3️⃣ **Add a Group to Monitor**

**Get Group Link:**
1. Open WhatsApp on your phone
2. Go to the group you want to monitor
3. Tap the group name at the top
4. Scroll down → Tap **"Invite via Link"**
5. Tap **"Copy Link"**
6. You'll get: `https://chat.whatsapp.com/ABC123XYZ`

**Add to Bot:**
1. In the browser, find the **"WhatsApp Group Invite Link"** input field
2. Paste your link
3. Click **"Join Group"**
4. You'll see: **"Successfully joined group!"** ✅

---

## ✅ Test It!

1. Go to the WhatsApp group on your phone
2. Send a message with violent words, like:
   - "I want to kill someone"
   - "Let's attack them"
3. **Bot automatically responds:**
   - "I recognize violence in {your message}"

---

## 📍 Where to Find Things

| What | Where |
|------|-------|
| **QR Code** | Server terminal (not browser!) |
| **Connect Button** | Browser → Dashboard → "Vibe Bot - Group Monitor" |
| **Status** | Same section, shows "Connected" when ready |
| **Group Link** | WhatsApp → Group → Group Info → Invite via Link |
| **Bot Response** | In the WhatsApp group chat |

---

## 🔧 Troubleshooting

**Problem: Can't see QR code**
- ✅ Check the **server terminal** (not browser console)
- ✅ Make sure server is running (`npm run dev:server`)
- ✅ Try clicking "Connect" again

**Problem: Can't join group**
- ✅ Make sure status shows "Connected" first
- ✅ Check that the invite link is valid
- ✅ Make sure group allows new members

**Problem: Bot not responding**
- ✅ Check server terminal for errors
- ✅ Make sure bot joined the group successfully
- ✅ Try sending a message with clear violence keywords: kill, attack, murder

---

## 🎬 Quick Summary

```
Terminal: npm run dev:server
Browser:  Click "Connect" → Scan QR (from terminal)
Phone:    Get group link → Paste in browser → Click "Join"
Test:     Send violent message → Bot responds!
```

---

## 💡 Tips

- **Keep the server terminal visible** - QR code appears there
- **Use a test group first** - Create a test group with yourself
- **Check server logs** - They show what's happening
- **Bot only monitors groups** - Not private chats

---

That's it! 🎉
