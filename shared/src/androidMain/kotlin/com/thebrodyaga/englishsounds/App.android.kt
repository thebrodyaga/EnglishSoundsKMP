package com.thebrodyaga.englishsounds

import android.app.Application
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication

class App : Application() {

    lateinit var koinApp: KoinApplication

    override fun onCreate() {
        koinApp = koinApplication {}
        super.onCreate()
    }
}