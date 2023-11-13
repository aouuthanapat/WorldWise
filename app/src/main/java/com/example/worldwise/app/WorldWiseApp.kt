package com.example.worldwise.app

import android.app.Application
import com.example.worldwise.di.modules.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.core.context.startKoin

open class WorldWiseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() = startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@WorldWiseApp)
        modules(presentationModule)
    }
}