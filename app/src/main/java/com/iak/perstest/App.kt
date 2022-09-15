package com.iak.perstest

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import timber.log.Timber

class App : Application() {

    companion object {
        lateinit var vmFactory: ViewModelProvider.AndroidViewModelFactory
            private set
    }

    override fun onCreate() {
        // hilt dependency injection
        // koin dependency injection
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        vmFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
    }
}