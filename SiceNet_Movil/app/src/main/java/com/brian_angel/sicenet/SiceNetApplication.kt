package com.brian_angel.sicenet

import android.app.Application
import com.brian_angel.sicenet.data.AppContainer

class SiceNetApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        //container = DefaultAppContainer()
    }
}