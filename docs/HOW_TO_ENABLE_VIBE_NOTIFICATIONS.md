# Vibe notifications are blocked – how to unblock

If **App info → Vibe** shows that notifications are **blocked**, follow these steps to turn them on.

---

## Emulator: grant permission via ADB

On the **Android emulator**, the system “Allow notifications?” dialog often **does not appear**. The Settings screen then shows **“This app does not send notifications”** and the toggle stays grayed out.

**Fix:** grant the permission from your computer (with the emulator running and the app installed):

```bash
adb shell pm grant com.vibe.app android.permission.POST_NOTIFICATIONS
```

Then:

1. Open the **Vibe** app.
2. Tap **“Send test notification”**. You should see a notification and the status may change to “Notifications: allowed.”
3. Open **Settings → Apps → Vibe → Notifications** again. The toggle may now be enabled; if not, the app can still post notifications (e.g. when you open WhatsApp with Vibe enabled).

---

## First: use the in‑app “Allow notifications” button

1. Open **Vibe** (tap the app icon).
2. On the main screen you’ll see **“Notifications: not allowed”** and a button **“Allow notifications”**.
3. Tap **“Allow notifications”**. The system should show **“Allow Vibe to send you notifications?”**.
4. Tap **Allow**. The status will change to **“Notifications: allowed”** and the Settings toggle should no longer be grayed out.

If you don’t see the system dialog (e.g. you previously chose “Don’t allow”), tap **“Open notification settings”** and turn notifications on there.

---

## Important: Android 13+ – app must ask for permission first

On **Android 13 and above**, the system keeps notifications **off** until the **app** asks for permission. If the app never asks, the **Allow notifications** toggle in Settings can stay **grayed out** or off.

**What we added in the app:** When you **open the Vibe app** (tap the Vibe icon), it now **requests notification permission** and creates notification channels. You should see a system dialog: **“Allow Vibe to send you notifications?”** → tap **Allow**. After that, the toggle in **Settings → Apps → Vibe → Notifications** should be available and you can turn **Allow notifications** ON.

**What you need to do:** Rebuild and reinstall the app, then **open the Vibe app once** (tap the icon). When the permission dialog appears, tap **Allow**. Then check **Settings → Apps → Vibe → Notifications** again.

---

## If the “Allow notifications” toggle is grayed out (disabled)

On Samsung and some other Android phones, the toggle can be **disabled** until you change a few system settings. Try these in order:

### 1. Turn off Do Not Disturb
- Swipe down from the top of the screen (Quick settings).
- Turn **Do Not Disturb** (or **Focus mode**) **OFF**.

### 2. Turn off Power Saving
- **Settings → Battery** (or **Device care → Battery**).
- Turn **Power saving** / **Medium power saving** **OFF** (at least temporarily).

### 3. Remove Vibe from “Sleeping apps” (Samsung)
- **Settings → Device care → Battery** (or **Battery and device care**).
- Tap **Background usage limits** (or **App power management**).
- Open **Sleeping apps** → if **Vibe** is listed, remove it (tap Vibe → **Remove** or **Allow**).
- Also check **Deep sleeping apps** and remove **Vibe** if it’s there.

### 4. Try the toggle again
- Go back to **Settings → Apps → Vibe → Notifications**.
- The **Allow notifications** toggle should now be tappable. Turn it **ON**.

If it’s still grayed out, restart the phone and repeat step 4. On some devices, opening the Vibe app once (then going back to Settings → Apps → Vibe → Notifications) can also unlock the toggle.

---

## On most Android phones (including Samsung)

1. Open **Settings**.
2. Go to **Apps** (or **Applications** / **Application manager**).
3. Find and tap **Vibe**.
4. Tap **Notifications** (under the app name).
5. Turn **Allow notifications** **ON** (toggle to the right).
6. *(Optional)* Turn ON **Vibe Status** and **Vibe Alerts** if they are listed as categories.

---

## If you don’t see “Allow notifications”

- On some devices: **Settings → Apps → Vibe → Notifications** → make sure the main switch at the top is **ON**.
- On Samsung (One UI): **Settings → Apps → Vibe** → tap **Notifications** → set **Allow notifications** to **Allowed**.

---

## After enabling

- When you open WhatsApp (with Vibe enabled), you should see the **“Vibe is monitoring WhatsApp”** toast and the **Monitoring WhatsApp** notification in the status bar.
- When violence is detected in an incoming message, you’ll get a **“Vibe: Violence detected”** notification.

If notifications are still blocked, check that **Do Not Disturb** is off and that **Vibe** is not in a “blocked” or “silent” apps list in your system notification settings.

---

## Testing the violence alert

1. **In the Vibe app**  
   Tap **“Test violence alert”** (below “Send test notification”). You should get a **“Vibe: Violence detected”** notification (and, on many devices, a heads-up pop-up). This confirms the **Vibe Alerts** channel and notification permission work.

2. **With WhatsApp**  
   Enable Vibe in Accessibility, open WhatsApp, and have someone send a message that contains toxic or aggressive keywords (or send from another device). You should get the same violence notification only when the message is classified as toxic/aggressive.  
   For full steps and Logcat checks, see [app/MONITORING_WHATSAPP.md](app/MONITORING_WHATSAPP.md).
