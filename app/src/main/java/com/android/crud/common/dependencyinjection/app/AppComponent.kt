package com.android.crud.common.dependencyinjection.app

import android.app.Application
import com.android.crud.network.RestApi
import dagger.Component
import dagger.Provides

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun application(): Application
    fun restApi(): RestApi
}