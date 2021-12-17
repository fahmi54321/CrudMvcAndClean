package com.android.crud.common.composition

import com.android.crud.view.karyawanviews.MainActivity

class Injector(
    private val compositionRoot: PresentationCompositionRoot
) {
    fun inject(activity: MainActivity) {
        activity.compositeDisposable = compositionRoot.compositeDisposable
        activity.dialogsNavigator = compositionRoot.dialogsNavigator
        activity.screenNavigator = compositionRoot.screenNavigator
        activity.mainUseCase = compositionRoot.mainUseCase
    }
}