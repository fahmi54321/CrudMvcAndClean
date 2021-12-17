package com.android.crud.common.dependencyinjection.presentation

import com.android.crud.view.common.dialog.DialogsNavigator
import com.android.crud.view.common.navigator.ScreenNavigator
import com.android.crud.view.common.viewmvc.ViewMvcFactory
import com.android.crud.view.karyawandetails.DetailsUseCase
import com.android.crud.view.karyawanform.FormKaryawanUserCase
import com.android.crud.view.karyawanviews.MainUseCase
import dagger.Component
import io.reactivex.rxjava3.disposables.CompositeDisposable

@Component(modules = [PresentationModule::class])
interface PresentationComponent {

    fun viewMvcFactory(): ViewMvcFactory
    fun dialogsNavigator(): DialogsNavigator
    fun mainUseCase(): MainUseCase
    fun formKaryawanUserCase(): FormKaryawanUserCase
    fun detailsUseCase(): DetailsUseCase
    fun screenNavigator(): ScreenNavigator
    fun compositeDisposable(): CompositeDisposable

}