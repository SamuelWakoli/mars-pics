package com.samwrotethecode.marspics

import android.app.Application
import com.samwrotethecode.marspics.data.AppContainer
import com.samwrotethecode.marspics.data.DefaultAppContainer

class MarsPicsApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}