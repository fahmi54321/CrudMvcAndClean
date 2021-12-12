package com.android.crud.common.composition

import com.android.crud.view.common.dialog.DialogsNavigator
import com.android.crud.view.common.navigator.ScreenNavigator
import com.android.crud.view.common.viewmvc.ViewMvcFactory
import com.android.crud.view.karyawandetails.DetailsUseCase
import com.android.crud.view.karyawanform.FormKaryawanUserCase
import com.android.crud.view.karyawanviews.MainUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PresentationCompositionRoot(
    private val activityCompositionRoot: ActivityCompositionRoot
) {

    private val layoutInflater get() = activityCompositionRoot.layoutInflater
    private val fragmentManager get() = activityCompositionRoot.fragmentManager
    private val restApi get() = activityCompositionRoot.restApi

    val screenNavigator get() = activityCompositionRoot.screenNavigator
    val compositeDisposable get() = activityCompositionRoot.compositeDisposable
    val viewMvcFactory get() = ViewMvcFactory(layoutInflater)
    val dialogsNavigator get() =  DialogsNavigator(fragmentManager)
    val mainUseCase get() = MainUseCase(compositeDisposable,restApi)
    val formKaryawanUserCase get() = FormKaryawanUserCase(compositeDisposable, restApi)
    val detailsUseCase get() = DetailsUseCase(compositeDisposable, restApi)

}