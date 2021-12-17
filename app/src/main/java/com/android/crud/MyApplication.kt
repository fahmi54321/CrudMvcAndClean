package com.android.crud

import android.app.Application
import com.android.crud.common.dependencyinjection.AppCompositionRoot

class MyApplication() : Application() {

    lateinit var appCompositionRoot: AppCompositionRoot

    override fun onCreate() {
        appCompositionRoot = AppCompositionRoot()
        super.onCreate()
    }

}