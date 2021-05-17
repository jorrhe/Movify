package com.movify

import android.app.Application.ActivityLifecycleCallbacks
import android.app.Activity
import android.os.Bundle
import com.movify.R
import android.app.Application
import com.movify.SplashScreenHelper

internal class SplashScreenHelper : ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        // apply the actual theme
        activity.setTheme(R.style.Theme_Movify)
    }

    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
}

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(SplashScreenHelper())
    }
}