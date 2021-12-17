package com.android.crud.common.dependencyinjection

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.android.crud.view.common.navigator.ScreenNavigator
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ActivityCompositionRoot(
    private val appCompositionRoot: AppCompositionRoot,
    private val activity: AppCompatActivity
) {

    val screenNavigator by lazy { ScreenNavigator(activity) }
    val layoutInflater get() = LayoutInflater.from(activity)
    val fragmentManager get() = activity.supportFragmentManager
    val restApi get() = appCompositionRoot.restApi
    val compositeDisposable by lazy { CompositeDisposable() }

}