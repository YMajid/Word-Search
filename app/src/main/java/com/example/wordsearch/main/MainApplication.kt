package com.example.wordsearch.main

import android.app.Application
import timber.log.Timber

/**
 * Overrides Application, used to implement Timber throughout the project
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}