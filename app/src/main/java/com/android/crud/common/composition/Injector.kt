package com.android.crud.common.composition

import com.android.crud.view.karyawandetails.DetailsActivity
import com.android.crud.view.karyawanform.FormKaryawanActivity
import com.android.crud.view.karyawanviews.MainActivity

class Injector(
    private val compositionRoot: PresentationCompositionRoot
) {
    fun inject(activity: MainActivity) {
        activity.compositeDisposable = compositionRoot.compositeDisposable
        activity.dialogsNavigator = compositionRoot.dialogsNavigator
        activity.screenNavigator = compositionRoot.screenNavigator
        activity.mainUseCase = compositionRoot.mainUseCase
        activity.viewMvcFactory = compositionRoot.viewMvcFactory
    }

    fun inject(activity: DetailsActivity) {
        activity.detailsUseCase = compositionRoot.detailsUseCase
        activity.screenNavigator = compositionRoot.screenNavigator
        activity.dialogsNavigator = compositionRoot.dialogsNavigator
        activity.viewMvcFactory = compositionRoot.viewMvcFactory
        activity.compositeDisposable = compositionRoot.compositeDisposable
    }

    fun inject(activity: FormKaryawanActivity) {
        activity.screenNavigator = compositionRoot.screenNavigator
        activity.dialogsNavigator = compositionRoot.dialogsNavigator
        activity.formKaryawanUserCase = compositionRoot.formKaryawanUserCase
        activity.viewMvcFactory = compositionRoot.viewMvcFactory
        activity.compositeDisposable = compositionRoot.compositeDisposable
    }
}