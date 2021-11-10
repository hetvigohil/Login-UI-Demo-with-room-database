package com.medicine.demo.base

import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.os.Bundle
import com.facebook.stetho.Stetho
import com.medicine.demo.BuildConfig


class BaseApplication : Application() {
    companion object {
        @get:Synchronized
        lateinit var SINGLETON_INSTANCE: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        SINGLETON_INSTANCE = this

        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            }

            override fun onActivityResumed(activity: Activity) {
            }
        })
    }
}