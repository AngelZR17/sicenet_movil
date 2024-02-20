package com.brian_angel.sicenet

import android.app.Application
import com.brian_angel.sicenet.data.AppContainer
import com.brian_angel.sicenet.data.DefaultAppContainer

class AlumnosContainer: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}