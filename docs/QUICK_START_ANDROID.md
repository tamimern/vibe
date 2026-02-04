# Quick Start - Android Project Setup (5 Minutes)

If you're completely new to Android, follow these steps in order:

## 1. Install Android Studio (15-30 minutes)

1. Download from: https://developer.android.com/studio
2. Install it (follow the wizard)
3. Open Android Studio
4. Let it download SDK components (first time only)

## 2. Create New Project (2 minutes)

1. **File → New → New Project**
2. Choose **"Empty Activity"**
3. Set:
   - Name: `Vibe`
   - Package: `com.vibe.app`
   - Language: **Kotlin** ⚠️
   - Minimum SDK: **API 24**
4. Click **Finish**

## 3. Copy Files (3 minutes)

### Copy Kotlin Files:
1. Create folder: Right-click `app/src/main/java/com/vibe/app/` → **New → Package** → name: `accessibility`
2. Copy `VibeAccessibilityService.kt` into that folder
3. Copy `ViewNodeAnalyzer.kt` into that folder

### Copy XML Files:
1. Create folder: Right-click `app/src/main/res/` → **New → Directory** → name: `xml`
2. Create file: Right-click `xml` folder → **New → File** → `accessibility_service_config.xml`
3. Paste the XML content (from the files I created)

### Update Existing Files:
1. Open `AndroidManifest.xml` → Add permissions and service (see guide)
2. Open `strings.xml` → Add the accessibility description string

## 4. Build (1 minute)

1. Click **Build → Make Project**
2. Wait for "Build successful" ✅

## 5. Run (2 minutes)

1. Connect Android phone (with USB debugging) OR start emulator
2. Click green **▶ Run** button
3. App installs on device

## 6. Enable Service (1 minute)

1. Open the app
2. Click "Enable Accessibility Service"
3. In settings, toggle **Vibe** ON

## 6b. Allow notifications (Android 13+) – notifications blocked?

For "Vibe is monitoring" and violence alerts to appear:
1. **Settings → Apps → Vibe → Notifications**
2. Turn **Allow notifications** **ON** (if it’s blocked, switch it to ON)

**Detailed steps:** see [HOW_TO_ENABLE_VIBE_NOTIFICATIONS.md](HOW_TO_ENABLE_VIBE_NOTIFICATIONS.md).

## 7. Test (1 minute)

1. In the **Vibe** app, tap **“Test violence alert”** to confirm the violence notification appears (and, on many devices, as a heads-up).
2. Open **WhatsApp** → you should see a **toast** “Vibe is monitoring WhatsApp” and a **notification** in the status bar.
3. Check **Logcat** (bottom of Android Studio), filter by: `VibeAccessibilityService` → you should see: `📱 WhatsApp opened - now monitoring`

---

## That's It! 🎉

If you see the log message, the service is working!

**Total time: ~5 minutes** (after Android Studio is installed)

---

## Common Issues

| Problem | Solution |
|---------|----------|
| "Cannot resolve symbol" | **File → Sync Project with Gradle Files** |
| Service not in settings | Uninstall app, rebuild, reinstall |
| No logs | Make sure service is enabled in Accessibility Settings |

---

## File Locations Summary

```
app/
├── src/main/
│   ├── java/com/vibe/app/
│   │   ├── MainActivity.kt                    ← Already exists
│   │   └── accessibility/                     ← CREATE THIS
│   │       ├── VibeAccessibilityService.kt     ← COPY HERE
│   │       └── ViewNodeAnalyzer.kt            ← COPY HERE
│   ├── res/
│   │   ├── xml/                               ← CREATE THIS
│   │   │   └── accessibility_service_config.xml ← COPY HERE
│   │   └── values/
│   │       └── strings.xml                    ← UPDATE THIS
│   └── AndroidManifest.xml                    ← UPDATE THIS
```

---

Need more detail? See `ANDROID_SETUP_GUIDE.md` for the full guide.
