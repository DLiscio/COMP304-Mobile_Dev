package com.damien.damienliscio_comp304lab3_ex1

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.damien.damienliscio_comp304lab3_ex1.di.appModules

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin with the appModules
        startKoin {
            androidContext(this@WeatherApplication) // provide the context
            modules(appModules) // load your modules
        }
    }
}