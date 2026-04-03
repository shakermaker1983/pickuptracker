# 📱 Pickup Tracker

An Android app that counts how many times you pick up your phone each day (screen unlocks), resets at midnight, and shows the count on a home screen widget.

---

## How It Works

- A **foreground service** listens for `ACTION_USER_PRESENT` broadcasts — fired every time the screen is unlocked
- Each unlock increments a counter stored in **SharedPreferences**
- At midnight (checked on each unlock and app open), the counter resets automatically
- A **home screen widget** displays the count in real time
- A **persistent notification** (required by Android for foreground services) also shows the count
- A **BootReceiver** restarts the service automatically after the device reboots

---

## Project Structure

```
app/src/main/
├── AndroidManifest.xml
├── java/com/pickuptracker/
│   ├── MainActivity.kt        ← Main UI screen
│   ├── PickupService.kt       ← Background service (screen unlock listener)
│   ├── PickupPrefs.kt         ← SharedPreferences helper + midnight reset
│   ├── PickupWidget.kt        ← Home screen widget provider
│   └── BootReceiver.kt        ← Restarts service after reboot
└── res/
    ├── layout/
    │   ├── activity_main.xml  ← Main screen layout
    │   └── widget_pickup.xml  ← Widget layout
    ├── xml/
    │   └── pickup_widget_info.xml ← Widget metadata
    ├── drawable/
    │   ├── widget_background.xml
    │   └── ic_phone.xml
    └── values/
        ├── strings.xml
        ├── colors.xml
        └── themes.xml
```

---

## Setup Instructions

### Requirements
- Android Studio Hedgehog (2023.1.1) or newer
- Android SDK 26+ (Android 8.0 Oreo)
- Kotlin plugin

### Steps

1. **Open in Android Studio**
   - Open Android Studio → File → Open → select the `PickupTracker` folder

2. **Sync Gradle**
   - Android Studio will prompt you to sync — click **Sync Now**

3. **Add launcher icons** (required before building)
   - Right-click `res` → New → Image Asset
   - Choose the phone icon or any icon you prefer
   - This generates `mipmap/ic_launcher` and `mipmap/ic_launcher_round`

4. **Build & Run**
   - Connect your Android device or start an emulator
   - Click **Run ▶** or press `Shift+F10`

5. **Add the Widget**
   - Long-press your home screen → Widgets
   - Find **Pickup Tracker** and drag it onto your home screen

---

## Permissions Used

| Permission | Reason |
|---|---|
| `RECEIVE_BOOT_COMPLETED` | Restart tracking service after reboot |
| `FOREGROUND_SERVICE` | Run background unlock detection |
| `POST_NOTIFICATIONS` | Show the persistent notification (Android 13+) |

---

## Notes

- Android requires a visible notification for foreground services — this is by design and ensures the user knows tracking is active
- On Android 13+ (API 33), the app will request notification permission on first launch
- Battery optimisation: if Android kills the service, it will restart itself (`START_STICKY`)
