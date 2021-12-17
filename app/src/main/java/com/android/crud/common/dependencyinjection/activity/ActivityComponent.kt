package com.android.crud.common.dependencyinjection.activity

import android.app.Application
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.android.crud.network.RestApi
import com.android.crud.view.common.navigator.ScreenNavigator
import dagger.Component
import dagger.Provides
import io.reactivex.rxjava3.disposables.CompositeDisposable

@Component(modules = [ActivityModule::class])
interface ActivityComponent {

    fun screenNavigator(): ScreenNavigator

    fun compositeDisposable(): CompositeDisposable

    fun activity(): AppCompatActivity

    fun restApi(): RestApi

    fun layoutInflater(): LayoutInflater

    fun fragmentManager(): FragmentManager

}