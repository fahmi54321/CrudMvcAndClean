package com.android.crud.common.dependencyinjection.app

import android.app.Application
import com.android.crud.network.RestApi
import dagger.Component
import dagger.Provides

@Component(modules = [AppModule::class])
interface AppComponent {
    fun application(): Application
    fun restApi(): RestApi
}