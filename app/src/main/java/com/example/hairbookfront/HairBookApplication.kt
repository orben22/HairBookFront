package com.example.hairbookfront

import android.app.Application
import android.util.Log
import com.example.hairbookfront.util.Constants.HAIR_BOOK_APPLICATION
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HairBookApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(HAIR_BOOK_APPLICATION,"Coming from HairBookApp.kt")
    }
}