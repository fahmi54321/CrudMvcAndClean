package com.android.crud.common.dependencyinjection.presentation

import com.android.crud.common.dependencyinjection.activity.ActivityComponent
import com.android.crud.common.dependencyinjection.activity.ActivityModule
import com.android.crud.common.dependencyinjection.app.AppComponent
import com.android.crud.view.common.dialog.DialogsNavigator
import com.android.crud.view.common.viewmvc.ViewMvcFactory
import com.android.crud.view.karyawandetails.DetailsUseCase
import com.android.crud.view.karyawanform.FormKaryawanUserCase
import com.android.crud.view.karyawanviews.MainUseCase
import dagger.Module
import dagger.Provides

@Module
class PresentationModule(
    private val activityComponent: ActivityComponent
) {

    @Provides
    fun layoutInflater() = activityComponent.layoutInflater()

    @Provides
    fun fragmentManager() = activityComponent.fragmentManager()

    @Provides
    fun restApi() = activityComponent.restApi()

    @Provides
    fun screenNavigator() = activityComponent.screenNavigator()

    @Provides
    fun compositeDisposable() = activityComponent.compositeDisposable()

    @Provides
    fun viewMvcFactory() = ViewMvcFactory(layoutInflater())

    @Provides
    fun dialogsNavigator() = DialogsNavigator(fragmentManager())

    @Provides
    fun mainUseCase() = MainUseCase(compositeDisposable(), restApi())

    @Provides
    fun formKaryawanUserCase() = FormKaryawanUserCase(compositeDisposable(), restApi())

    @Provides
    fun detailsUseCase() = DetailsUseCase(compositeDisposable(), restApi())

}