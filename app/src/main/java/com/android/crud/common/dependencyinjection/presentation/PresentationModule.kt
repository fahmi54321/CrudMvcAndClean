package com.android.crud.common.dependencyinjection.presentation

import android.view.LayoutInflater
import androidx.fragment.app.FragmentManager
import com.android.crud.common.dependencyinjection.activity.ActivityComponent
import com.android.crud.common.dependencyinjection.activity.ActivityModule
import com.android.crud.common.dependencyinjection.app.AppComponent
import com.android.crud.network.RestApi
import com.android.crud.view.common.dialog.DialogsNavigator
import com.android.crud.view.common.viewmvc.ViewMvcFactory
import com.android.crud.view.karyawandetails.DetailsUseCase
import com.android.crud.view.karyawanform.FormKaryawanUserCase
import com.android.crud.view.karyawanviews.MainUseCase
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.disposables.CompositeDisposable

@Module
class PresentationModule() {

    @Provides
    fun viewMvcFactory(layoutInflater: LayoutInflater) = ViewMvcFactory(layoutInflater)

    @Provides
    fun dialogsNavigator(fragmentManager: FragmentManager) = DialogsNavigator(fragmentManager)

    @Provides
    fun mainUseCase(
        compositeDisposable: CompositeDisposable,
        restApi: RestApi
    ) = MainUseCase(compositeDisposable, restApi)

    @Provides
    fun formKaryawanUserCase(
        compositeDisposable: CompositeDisposable,
        restApi: RestApi
    ) = FormKaryawanUserCase(compositeDisposable, restApi)

    @Provides
    fun detailsUseCase(
        compositeDisposable: CompositeDisposable,
        restApi: RestApi
    ) = DetailsUseCase(compositeDisposable, restApi)

}