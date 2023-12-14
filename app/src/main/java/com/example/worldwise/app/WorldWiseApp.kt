package com.example.worldwise.app

import android.app.Application
import com.example.worldwise.di.app
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

open class WorldWiseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() = startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@WorldWiseApp)
        modules(app)
    }
}