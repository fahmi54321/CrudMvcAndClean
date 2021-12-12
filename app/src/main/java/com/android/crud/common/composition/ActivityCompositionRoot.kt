package com.android.crud.common.composition

import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.android.crud.view.common.dialog.DialogsNavigator
import com.android.crud.view.common.navigator.ScreenNavigator
import com.android.crud.view.common.viewmvc.ViewMvcFactory
import com.android.crud.view.karyawandetails.DetailsUseCase
import com.android.crud.view.karyawanform.FormKaryawanUserCase
import com.android.crud.view.karyawanviews.MainUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ActivityCompositionRoot(
    private val appCompositionRoot: AppCompositionRoot,
    private val activity: AppCompatActivity
) {

    private val layoutInflater get() = LayoutInflater.from(activity)
    val viewMvcFactory get() = ViewMvcFactory(layoutInflater)
    private val fragmentManager get() = activity.supportFragmentManager
    val dialogsNavigator get() =  DialogsNavigator(fragmentManager)
    val screenNavigator by lazy { ScreenNavigator(activity) }

    private val restApi get() = appCompositionRoot.restApi
    val compositeDisposable by lazy { CompositeDisposable() }
    val mainUseCase get() = MainUseCase(compositeDisposable,restApi)
    val formKaryawanUserCase get() = FormKaryawanUserCase(compositeDisposable, restApi)
    val detailsUseCase get() = DetailsUseCase(compositeDisposable, restApi)

}