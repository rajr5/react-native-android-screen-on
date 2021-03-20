package io.github.gretzky.wakelock

import android.content.Context
import android.net.wifi.WifiManager
import android.os.PowerManager

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class RNWakeLockModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    private val mPowerManager: PowerManager
    private val mWifiManager: WifiManager

    private var partialWakeLock: PowerManager.WakeLock? = null
    private var wakeLock: PowerManager.WakeLock? = null
    private var wifiLock: WifiManager.WifiLock? = null
    private var isPartialWakeLocked = false
    private var isWakeLocked = false

    override fun getName(): String {
        return "RNWakeLock"
    }

    init {
        mPowerManager = reactContext.getSystemService(Context.POWER_SERVICE) as PowerManager
        mWifiManager = reactContext.getSystemService(Context.WIFI_SERVICE) as WifiManager 

        partialWakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "RNPartialWakeLock")
        wakeLock = mPowerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP, "RNWakeLock")
        wifiLock = mWifiManager.createWifiLock(WifiManager.WIFI_MODE_FULL, "RNWakeLock")
    }

    @ReactMethod
    fun setWakeLock(promise: Promise) {
        if (isWakeLocked) {
            return
        }
        this.wakeLock!!.acquire()
        this.wifiLock!!.acquire()
        isWakeLocked = true
        promise.resolve(isWakeLocked)
    }

    @ReactMethod
    fun setPartialWakeLock(promise: Promise) {
         if (isPartialWakeLocked) {
             return
         }
         this.partialWakeLock!!.acquire()
         isPartialWakeLocked = true
         promise.resolve(isPartialWakeLocked)
     }

    @ReactMethod
    fun releaseWakeLock(promise: Promise) {
        if (!isWakeLocked) {
            return
        }
        this.wakeLock!!.release()
        this.wifiLock!!.release()
        isWakeLocked = false
        promise.resolve(isWakeLocked)
    }

    @ReactMethod
    fun releasePartialWakeLock(promise: Promise) {
        if (!isPartialWakeLocked) {
            return
        }
        this.partialWakeLock!!.release()
        isPartialWakeLocked = false
        promise.resolve(isPartialWakeLocked)
    } 

    @ReactMethod
    fun isPartialWakeLocked(promise: Promise) {
        promise.resolve(isPartialWakeLocked)
    }

    @ReactMethod
    fun isWakeLocked(promise: Promise) {
        promise.resolve(isWakeLocked)
    }
}
