package com.android.crud.common.dependencyinjection.activity

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.android.crud.common.dependencyinjection.app.AppComponent
import com.android.crud.common.dependencyinjection.app.AppModule
import com.android.crud.view.common.navigator.ScreenNavigator
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.disposables.CompositeDisposable

@Module
class ActivityModule(
    private val activity: AppCompatActivity
) {

    @Provides
    fun activity() = activity

    @Provides
    fun layoutInflater() = LayoutInflater.from(activity)

    @Provides
    fun fragmentManager() = activity.supportFragmentManager

    @ActivityScope
    @Provides
    fun screenNavigator(activity: AppCompatActivity) = ScreenNavigator(activity)

    @ActivityScope
    @Provides
    fun compositeDisposable() = CompositeDisposable()

}