package com.dpassets.codebase.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication  : Application(), LifecycleObserver {

    companion object {
        var isMockLocationOn = false
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }
}
