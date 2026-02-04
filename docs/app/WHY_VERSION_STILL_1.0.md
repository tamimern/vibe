# Why you still see Vibe version 1.0 (and how to get 1.1.0)

**Android Studio uses the version from `app/build.gradle`, not from AndroidManifest.xml.**  
Gradle **overrides** the manifest when building. So if your app shows **1.0**, that value is coming from **app/build.gradle** on your machine.

---

## Fix: set the version in app/build.gradle

1. In **Android Studio**, open **app/build.gradle** (or **app/build.gradle.kts**).
   - In the Project view: **app** → **build.gradle** (or **build.gradle.kts**).

2. Find the **`defaultConfig`** block inside **`android { ... }`**. It looks like:
   ```gradle
   defaultConfig {
       applicationId "com.vibe.app"
       minSdk 24
       targetSdk 34
       versionCode 1        // ← change this
       versionName "1.0"    // ← change this
   }
   ```

3. **Change** to:
   ```gradle
       versionCode 2
       versionName "1.1.0"
   ```

4. **Sync and rebuild:**
   - **File → Sync Project with Gradle Files** (or click the elephant icon).
   - **Build → Clean Project**.
   - **Build → Rebuild Project**.
   - **Run** (green play button) to install on the device.

5. On the device, **open the Vibe app**. You should see **Version 1.1.0 (2)** on the screen (and in Settings → Apps → Vibe).

---

## If you use Kotlin DSL (build.gradle.kts)

In **app/build.gradle.kts**, find:

```kotlin
defaultConfig {
    ...
    versionCode = 1
    versionName = "1.0"
}
```

Change to:

```kotlin
    versionCode = 2
    versionName = "1.1.0"
```

Then Sync, Clean, Rebuild, and Run as above.
