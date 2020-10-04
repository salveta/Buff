package com.buffup.app

import android.app.Application
import com.buffup.sdk.data.di.initDI

class Application : Application(){

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}