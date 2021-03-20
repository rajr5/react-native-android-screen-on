# react-native-wake-lock

Forked from [react-native-android-wake-lock](https://github.com/gretzky/react-native-android-wake-lock).

Native Module for setting a [wake lock](https://developer.android.com/training/scheduling/wakelock) on an Android device and turning the screen on in the process. Utilizes both `PowerManager` and `WifiManager`.

This will turn on the screen if the app is running in the
foreground or background. This is great for kiosk apps
without lockscreens to turn the screen back on when something
happens, such as a push notification.

This module also exposes methods to acquire a partial wake
lock, which will keep the CPU running and prevent the app
from going into Doze. This will eat up the user's battery, so be
cautious.
This can be useful for preventing sleep on devices.
## Installation

**Note:** This package requires React Native >=0.60.

Install with npm/yarn.

```bash
npm install react-native-android-screen-on --save
// or
yarn add react-native-android-screen-on
```

Since this package requires RN 0.60 or higher, this package will be linked automatically. If you run into an issue, you can try `react-native link react-native-android-screen-on`.

## Usage

Make sure to request the wake lock permission. Add the following inside the project manifest (`android/app/src/main/AndroidManifest.xml`):

```xml
<uses-permission android:name="android.permission.WAKE_LOCK" />
```

This module exposes 2 possible methods for accessing the wake lock: via an interface object or a React hook. Depending on your use case, one or the other should suffice.

```js
import React from "react";
import { WakeLockInterface, useWakeLock } from "react-native-android-screen-on";

// the interface exposes all 3 (async) methods
const isWakeLocked = WakeLockInterface.isWakeLocked();
// these 2 aren't necessary if you're using the hook
const setWakeLock = WakeLockInterface.setWakeLock();
const releaseWakeLock = WakeLockInterface.releaseWakeLock();

// you can also use the hook to set/release a wake lock on component mount/unmount
const Component = () => {
  const [wakeLocked, setWakeLocked] = useState(isWakeLocked);

  // you can use the `isWakeLocked` method from the interface to check whether or not the wake lock is set
  useEffect(() => {
    console.log(wakeLocked);
  }, [wakeLocked]);

  useWakeLock();

  return <View />;
};
```

## API

The interface has 6 methods:

- **`setWakeLock(): Promise<boolean>`** - sets both the full WakeLock and WifiLock and turns on the screen
- **`releaseWakeLock(): Promise<boolean>`** - releases both the full WakeLock and WifiLock, allows screen to shut off
- **`isWakeLocked(): Promise<boolean>`** - whether or not the full wake/wifi locks are held
- **`setPartialWakeLock(): Promise<boolean>`** - sets both the partial WakeLock only
- **`releasePartialWakeLock(): Promise<boolean>`** - releases both the partial WakeLock only
- **`isPartialWakeLocked(): Promise<boolean>`** - whether or not the partial wake is held

