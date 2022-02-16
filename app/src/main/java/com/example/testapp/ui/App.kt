package com.example.testapp.ui

import android.app.Application
import com.example.testapp.di.databaseModule
import com.example.testapp.di.repositoryModule
import com.example.testapp.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                databaseModule,
                repositoryModule,
                viewModelsModule
            )
        }
    }
}