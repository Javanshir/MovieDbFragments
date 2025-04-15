package com.alammadli.moviedbfragments.ui

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by Javanshir on 22.08.24.
 */
@HiltAndroidApp
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}