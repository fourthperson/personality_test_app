package com.iak.perstest.base

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.iak.perstest.BuildConfig
import timber.log.Timber

class App : Application() {

    companion object {
        lateinit var vmFactory: ViewModelProvider.AndroidViewModelFactory
            private set
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        vmFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
    }
}