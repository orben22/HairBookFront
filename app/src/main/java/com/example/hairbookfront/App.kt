package com.example.hairbookfront

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HairBookApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"Coming from HairBookApp.kt")
    }

    // static block
    companion object {
        const val TAG = "HairBookApp"
    }
}