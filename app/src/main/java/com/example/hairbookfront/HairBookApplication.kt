package com.example.hairbookfront

import android.app.Application
import android.util.Log
import com.example.hairbookfront.util.Constants.HAIR_BOOK_APPLICATION
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class HairBookApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.d("$HAIR_BOOK_APPLICATION created")
    }
}