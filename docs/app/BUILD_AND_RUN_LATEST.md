# Build and run the latest Vibe app

If you still see **"Welcome to Vibe!"** with only "Open Accessibility Settings" (no Step 2, no "Allow notifications"), Android Studio is building a **different project** than this one.

## This project (dot-peace-chat) has:

- **Launcher:** `DashboardActivity` (not MainActivity, not a Welcome screen)
- **First screen:** "Vibe" title, version, **Step 1: Enable Vibe in Accessibility**, **Step 2: Allow notifications**, and an **"Allow notifications"** button
- **Files:** `app/src/main/java/com/vibe/app/ui/DashboardActivity.kt`, `app/src/main/res/layout/activity_dashboard.xml`
- **No** "Welcome to Vibe!" text anywhere

## How to run THIS version

1. **Close** the current project in Android Studio (File → Close Project).
2. **Open** the folder that contains **this** project:
   - Use **File → Open** (or "Open an Existing Project").
   - Go to the **dot-peace-chat** folder (the one that contains this `BUILD_AND_RUN_LATEST.md` file and the `app` folder with `DashboardActivity.kt`).
   - Select that folder and click OK.
3. Wait for Gradle sync to finish.
4. Connect your phone or start the emulator.
5. Click the **green Run** button (play icon).
6. On the device you should see **Step 1** and **Step 2** and the **"Allow notifications"** button (not "Welcome to Vibe!").

## Quick check

- In the **Project** view (left), under `app → java → com.vibe.app.ui` you should see **DashboardActivity.kt**.
- You should **not** see **MainActivity.kt** as the main launcher (our app uses only DashboardActivity).
- In `app/src/main/res/layout/` you should see **activity_dashboard.xml** with "Step 1" and "Step 2" in the layout.

If your project has a "Welcome to Vibe!" screen or MainActivity as launcher, you are in the wrong project — open the **dot-peace-chat** folder that contains the code we edited.
