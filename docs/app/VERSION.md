# Vibe app version

- **versionName** (shown to user): `1.1.0`
- **versionCode** (internal, must increase each release): `2`

**Important:** Android Studio gets the version from **app/build.gradle**, not the manifest. If you still see **1.0** on the device, you must change the version in **app/build.gradle**. See **[WHY_VERSION_STILL_1.0.md](WHY_VERSION_STILL_1.0.md)** for step-by-step fix.

Set in **AndroidManifest.xml** (`android:versionName`, `android:versionCode`) for reference. Gradle overrides these when building. In **app/build.gradle** use:

```gradle
android {
    defaultConfig {
        versionCode 2
        versionName "1.1.0"
    }
}
```

When you release a new build, increase **versionCode** (e.g. 3, 4, …) and update **versionName** (e.g. 1.2.0) as needed.
