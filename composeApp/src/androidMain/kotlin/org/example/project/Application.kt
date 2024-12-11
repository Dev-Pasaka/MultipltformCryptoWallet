package org.example.project

import android.app.Application
import org.example.di.config
import org.example.di.initKoin

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(createDataStore(applicationContext)){
            applicationContext
        }
        
    }
}