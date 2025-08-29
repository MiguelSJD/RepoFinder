package com.miguelsjd.repofinder

import android.app.Application
import android.util.Log
import com.miguelsjd.core.koin.provider.KoinProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            Log.d("Koin", "Koin initialized " + KoinProvider.modules.size)
            modules(KoinProvider.modules)
        }
    }
}