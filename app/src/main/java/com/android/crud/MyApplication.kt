package com.android.crud

import android.app.Application
import com.android.crud.common.dependencyinjection.app.AppModule
import com.android.crud.common.dependencyinjection.app.DaggerAppComponent

class MyApplication() : Application() {

    val appComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
    }

}