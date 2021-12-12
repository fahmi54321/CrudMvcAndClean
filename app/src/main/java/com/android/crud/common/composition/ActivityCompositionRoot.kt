package com.android.crud.common.composition

import android.app.Activity
import com.android.crud.view.common.navigator.ScreenNavigator
import com.android.crud.view.karyawandetails.DetailsUseCase
import com.android.crud.view.karyawanform.FormKaryawanUserCase
import com.android.crud.view.karyawanviews.MainUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ActivityCompositionRoot(
    private val appCompositionRoot: AppCompositionRoot,
    private val activity: Activity
) {

    val screenNavigator by lazy { ScreenNavigator(activity) }
    private val restApi get() = appCompositionRoot.restApi

    fun main(compositeDisposable: CompositeDisposable): MainUseCase {
        return MainUseCase(compositeDisposable, restApi)
    }

    fun form(compositeDisposable: CompositeDisposable): FormKaryawanUserCase {
        return FormKaryawanUserCase(compositeDisposable, restApi)
    }

    fun details(compositeDisposable: CompositeDisposable): DetailsUseCase {
        return DetailsUseCase(compositeDisposable, restApi)
    }

}