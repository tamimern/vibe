# How to Enable USB Debugging on Android Phone

## Step 1: Enable Developer Options

1. **Open Settings** on your phone
2. **Scroll down** and find **"About phone"** (or "About device")
3. **Tap on "About phone"**
4. **Find "Build number"** (or "Build version")
5. **Tap "Build number" 7 times** (you'll see a countdown)
6. After the 7th tap, you'll see: **"You are now a developer!"** ✅

---

## Step 2: Enable USB Debugging

1. **Go back** to the main Settings screen
2. **Look for "Developer options"** (or "Developer settings")
   - It might be under "System" → "Developer options"
   - Or directly in the main Settings list
3. **Tap "Developer options"**
4. **Find "USB debugging"**
5. **Toggle it ON** ✅
6. You might see a warning - **tap "OK"** or **"Allow"**

---

## Step 3: Connect Your Phone

1. **Connect your phone** to your computer using a USB cable
2. **On your phone**, you'll see a popup: **"Allow USB debugging?"**
3. **Check the box**: "Always allow from this computer" (optional but helpful)
4. **Tap "Allow"** or **"OK"**

---

## Step 4: Verify in Android Studio

1. **In Android Studio**, look at the top toolbar
2. **Click the device dropdown** (next to the Run button)
3. **You should see your phone** listed (e.g., "Samsung Galaxy...", "Pixel...", etc.)
4. If you see it → **You're connected!** ✅

---

## Step 5: Run the App on Your Phone

1. **Select your phone** from the device dropdown
2. **Click the green Run button** (or press `Shift+F10`)
3. **The app will install** on your phone
4. **The app will open** automatically

---

## Troubleshooting

### If you don't see "Developer options" in Settings:

- Make sure you tapped "Build number" 7 times
- Try going to Settings → System → Developer options
- Or Settings → About phone → tap Build number 7 times again

### If you don't see "USB debugging" option:

- Make sure Developer options is enabled (Step 1)
- Scroll down in Developer options - it might be further down

### If Android Studio doesn't detect your phone:

1. **Check the USB cable** - make sure it's a data cable (not just charging)
2. **Try a different USB port** on your computer
3. **On your phone**, make sure USB debugging is ON
4. **Unplug and replug** the USB cable
5. **Check if drivers are installed** (Windows might need to install drivers automatically)

### If you see "USB debugging is disabled":

- Go back to Developer options
- Toggle USB debugging OFF, then ON again
- Reconnect the USB cable

### If USB debugging is blocked by Auto Blocker (Samsung / One UI 6+):

On Samsung devices (Android 14 / One UI 6 and newer), **Auto Blocker** can block USB debugging. To enable it:

1. **Open Settings** → **Security and Privacy** (or **Biometrics and security**)
2. Find **"Auto Blocker"** (sometimes under **More security settings**)
3. **Turn Auto Blocker OFF** and confirm
4. Go back to **Settings** → **Developer options**
5. **Enable USB debugging** and accept the dialog
6. *(Optional but recommended)* Turn **Auto Blocker** back **ON** when you finish developing

**If USB debugging is still dimmed:** Toggle **Developer options** OFF, then ON again, then try enabling USB debugging.

**Security note:** Auto Blocker helps protect your device from USB-based attacks. Only disable it when you need to use USB debugging, and re-enable it when you’re done.

### For Windows Users:

- Windows might show "Installing device driver" - wait for it to finish
- If it asks for drivers, you might need to install:
  - **Samsung**: Samsung USB drivers
  - **Google Pixel**: Google USB drivers (usually auto-installed)
  - **Other brands**: Check manufacturer's website

### For Mac/Linux Users:

- Usually works automatically
- If not, you might need to install `adb` (Android Debug Bridge)

---

## Different Android Versions

### Android 12+ (newer):
- Settings → System → Developer options → USB debugging

### Android 11 and older:
- Settings → About phone → Build number (tap 7 times)
- Settings → Developer options → USB debugging

### Samsung phones:
- Settings → About phone → Software information → Build number (tap 7 times)
- Settings → Developer options → USB debugging

---

## Quick Checklist

- [ ] Tapped Build number 7 times
- [ ] Saw "You are now a developer!"
- [ ] Found Developer options in Settings
- [ ] Enabled USB debugging
- [ ] Connected phone via USB
- [ ] Allowed USB debugging on phone
- [ ] Phone appears in Android Studio device list

---

## Security Note

USB debugging allows your computer to control your phone. Only enable it when you're developing, and disable it when you're done.

---

## What to Do After Connecting

Once your phone is connected:

1. **Run the app** from Android Studio
2. **Enable the accessibility service** on your phone:
   - Settings → Accessibility → Find "Vibe" → Toggle ON
3. **Open WhatsApp** on your phone
4. **Check Logcat** in Android Studio to see the logs

Good luck! 🚀
