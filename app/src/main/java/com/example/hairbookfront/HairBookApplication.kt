package com.example.hairbookfront

import android.app.Application
import com.example.hairbookfront.util.Constants.HAIR_BOOK_APPLICATION
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.Random

/**
 * The application class for the HairBook app.
 */
@HiltAndroidApp
class HairBookApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.d("$HAIR_BOOK_APPLICATION created")
    }
        val backgrounds = arrayOf(
            R.drawable.background1,
            R.drawable.background3,
            R.drawable.background4,
            R.drawable.background6,
            R.drawable.background7,
            R.drawable.background8,
            R.drawable.background9,
            R.drawable.background10,
            R.drawable.background11,
            R.drawable.background12,
            R.drawable.background13,
            R.drawable.background14,
            R.drawable.background15,
            R.drawable.background16,
            R.drawable.background17,
            R.drawable.background18,
            R.drawable.background20,
            R.drawable.background21,
            R.drawable.background22
        )

        fun getRandomBackground(): Int {
            val randomIndex = Random().nextInt(backgrounds.size)
            return backgrounds[randomIndex]
        }
    }