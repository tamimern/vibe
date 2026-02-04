# Android Setup Guide for Beginners - Vibe Accessibility Service

This guide will walk you through setting up your first Android project to use the VibeAccessibilityService.

## Prerequisites

Before starting, you need:

1. **Android Studio** - Download from [developer.android.com/studio](https://developer.android.com/studio)
2. **Java Development Kit (JDK)** - Android Studio usually includes this
3. **An Android device or emulator** - For testing

---

## Step 1: Create a New Android Project

1. **Open Android Studio**
2. **Click "New Project"** (or File → New → New Project)
3. **Choose "Empty Activity"** template
4. **Configure your project:**
   - **Name**: `Vibe` (or any name you like)
   - **Package name**: `com.vibe.app` (must match what we use in code)
   - **Save location**: Choose where to save
   - **Language**: **Kotlin** (important!)
   - **Minimum SDK**: **API 24** (Android 7.0) or higher
   - **Build configuration language**: Gradle Kotlin DSL (default)
5. **Click "Finish"**

Android Studio will create the project and download dependencies (this may take a few minutes).

---

## Step 2: Understand the Project Structure

After creating the project, you'll see this structure:

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/vibe/app/
│   │   │   └── MainActivity.kt          ← Your main activity
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml    ← UI layout
│   │   │   ├── values/
│   │   │   │   └── strings.xml          ← Text strings
│   │   │   └── xml/                     ← We'll create this folder
│   │   └── AndroidManifest.xml          ← App configuration
│   └── test/                            ← Test files
├── build.gradle.kts                     ← Project dependencies
└── settings.gradle.kts
```

---

## Step 3: Add the Accessibility Service Files

### 3.1 Create the accessibility folder

1. In Android Studio, right-click on `app/src/main/java/com/vibe/app/`
2. Select **New → Package**
3. Name it: `accessibility`
4. You should now have: `app/src/main/java/com/vibe/app/accessibility/`

### 3.2 Add VibeAccessibilityService.kt

1. Right-click on the `accessibility` folder
2. Select **New → Kotlin Class/File**
3. Name it: `VibeAccessibilityService`
4. Choose **Class**
5. **Replace all the code** with the content from `app/src/main/java/com/vibe/app/accessibility/VibeAccessibilityService.kt` that was created earlier

### 3.3 Add ViewNodeAnalyzer.kt

1. Right-click on the `accessibility` folder again
2. Select **New → Kotlin Class/File**
3. Name it: `ViewNodeAnalyzer`
4. Choose **Class**
5. **Replace all the code** with the content from `app/src/main/java/com/vibe/app/accessibility/ViewNodeAnalyzer.kt`

---

## Step 4: Create XML Configuration Files

### 4.1 Create the xml folder

1. Right-click on `app/src/main/res/`
2. Select **New → Directory**
3. Name it: `xml`
4. You should now have: `app/src/main/res/xml/`

### 4.2 Create accessibility_service_config.xml

1. Right-click on the `xml` folder
2. Select **New → File**
3. Name it: `accessibility_service_config.xml`
4. **Paste this content:**

```xml
<?xml version="1.0" encoding="utf-8"?>
<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android"
    android:description="@string/accessibility_service_description"
    android:packageNames="com.whatsapp"
    android:accessibilityEventTypes="typeWindowStateChanged|typeWindowContentChanged|typeViewTextChanged"
    android:accessibilityFlags="flagIncludeNotImportantViews|flagReportViewIds"
    android:accessibilityFeedbackType="feedbackGeneric"
    android:notificationTimeout="100"
    android:canRetrieveWindowContent="true"
    android:settingsActivity="com.vibe.app.MainActivity" />
```

### 4.3 Update strings.xml

1. Open `app/src/main/res/values/strings.xml`
2. **Add this line** (or replace the entire file):

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">Vibe</string>
    <string name="accessibility_service_description">Vibe monitors WhatsApp messages to detect cyberbullying and provide real-time nudges. Vibe does not store or transmit your messages - all analysis happens locally on your device.</string>
</resources>
```

---

## Step 5: Update AndroidManifest.xml

1. Open `app/src/main/AndroidManifest.xml`
2. **Add the permissions** at the top (inside `<manifest>` tag, before `<application>`):

```xml
<uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE" />
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
<uses-permission android:name="android.permission.INTERNET" />
```

3. **Add the service** inside the `<application>` tag (after the activity):

```xml
<!-- Vibe Accessibility Service -->
<service
    android:name=".accessibility.VibeAccessibilityService"
    android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE"
    android:exported="false">
    <intent-filter>
        <action android:name="android.accessibilityservice.AccessibilityService" />
    </intent-filter>
    
    <!-- Accessibility Service Configuration -->
    <meta-data
        android:name="android.accessibilityservice"
        android:resource="@xml/accessibility_service_config" />
</service>
```

**Your complete AndroidManifest.xml should look like this:**

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.vibe.app">

    <uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE" />
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.AppCompat">
        
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- Vibe Accessibility Service -->
        <service
            android:name=".accessibility.VibeAccessibilityService"
            android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE"
            android:exported="false">
            <intent-filter>
                <action android:name="android.accessibilityservice.AccessibilityService" />
            </intent-filter>
            
            <meta-data
                android:name="android.accessibilityservice"
                android:resource="@xml/accessibility_service_config" />
        </service>

    </application>

</manifest>
```

---

## Step 6: Update MainActivity.kt (Optional - for testing)

Open `app/src/main/java/com/vibe/app/MainActivity.kt` and update it to show a simple UI:

```kotlin
package com.vibe.app

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val statusText = findViewById<TextView>(R.id.statusText)
        val enableButton = findViewById<Button>(R.id.enableButton)
        
        enableButton.setOnClickListener {
            // Open Accessibility Settings
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
            
            statusText.text = "Please enable 'Vibe' in Accessibility Settings"
        }
    }
}
```

---

## Step 7: Update activity_main.xml (Optional - for testing)

Open `app/src/main/res/layout/activity_main.xml` and replace with:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp"
    android:gravity="center">

    <TextView
        android:id="@+id/statusText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Vibe Accessibility Service"
        android:textSize="18sp"
        android:textStyle="bold"
        android:layout_marginBottom="32dp" />

    <Button
        android:id="@+id/enableButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Enable Accessibility Service" />

</LinearLayout>
```

---

## Step 8: Build the Project

1. Click **Build → Make Project** (or press `Ctrl+F9` / `Cmd+F9`)
2. Wait for the build to complete
3. If there are errors, check:
   - All files are in the correct folders
   - Package names match (`com.vibe.app`)
   - XML files are properly formatted

---

## Step 9: Run on Device/Emulator

### Option A: Physical Device

1. **Enable Developer Options** on your Android phone:
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times
   - Go back to Settings → Developer Options
   - Enable "USB Debugging"

2. **Connect phone** via USB

3. **In Android Studio**: Click the green "Run" button (or press `Shift+F10`)

4. **Select your device** from the list

### Option B: Emulator

1. **Create an emulator**:
   - Tools → Device Manager
   - Click "Create Device"
   - Choose a phone (e.g., Pixel 5)
   - Download a system image (API 24+)
   - Finish

2. **Run the app** on the emulator

---

## Step 10: Enable the Accessibility Service

1. **Open the app** on your device
2. **Click "Enable Accessibility Service"** button
3. **In Accessibility Settings**, find "Vibe"
4. **Toggle it ON**
5. **Grant all permissions** when prompted

---

## Step 11: Test the Service

1. **Open WhatsApp** on your device
2. **Check Android Studio Logcat** (bottom panel):
   - Filter by: `VibeAccessibilityService`
   - You should see: `📱 WhatsApp opened - now monitoring`

3. **Send a test message** in WhatsApp
4. **Check Logcat** again:
   - You should see: `💬 Message detected`

---

## Troubleshooting

### "Cannot resolve symbol" errors
- **Solution**: Click **File → Sync Project with Gradle Files**
- Wait for sync to complete

### "Package does not exist" errors
- **Solution**: Make sure package name is `com.vibe.app` everywhere
- Check folder structure matches package name

### Service not appearing in Accessibility Settings
- **Solution**: 
  1. Uninstall the app
  2. Rebuild and reinstall
  3. Check AndroidManifest.xml is correct

### No logs appearing
- **Solution**:
  1. Make sure service is enabled in Accessibility Settings
  2. Check Logcat filter is set correctly
  3. Make sure WhatsApp is actually open

---

## Next Steps

Once the basic service is working:

1. **Add Sentiment Analysis** - Integrate TensorFlow Lite or ML Kit
2. **Add UI Overlays** - Create the "Speed Bump" intervention
3. **Add Database** - Store sentiment tokens (not raw text!)
4. **Add Vibe Score** - Calculate and display the score

---

## Need Help?

- **Android Studio Documentation**: [developer.android.com/studio](https://developer.android.com/studio)
- **Accessibility Service Guide**: [developer.android.com/guide/topics/ui/accessibility/service](https://developer.android.com/guide/topics/ui/accessibility/service)
- **Kotlin Basics**: [kotlinlang.org/docs/home.html](https://kotlinlang.org/docs/home.html)

---

## Quick Checklist

- [ ] Android Studio installed
- [ ] New project created with Kotlin
- [ ] Accessibility folder created
- [ ] VibeAccessibilityService.kt added
- [ ] ViewNodeAnalyzer.kt added
- [ ] XML folder created
- [ ] accessibility_service_config.xml added
- [ ] strings.xml updated
- [ ] AndroidManifest.xml updated with permissions and service
- [ ] Project builds without errors
- [ ] App runs on device/emulator
- [ ] Accessibility service enabled in settings
- [ ] Logs appear when WhatsApp opens

Good luck! 🚀
