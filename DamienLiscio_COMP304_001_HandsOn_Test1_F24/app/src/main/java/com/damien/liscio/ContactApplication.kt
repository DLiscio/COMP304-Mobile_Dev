package com.damien.liscio

import android.app.Application
import com.damien.liscio.di.appModules
import org.koin.core.context.startKoin

class ContactApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModules)
        }
    }
}