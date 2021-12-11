package com.android.crud.view.karyawandetails

import com.android.crud.BuildConfig
import com.android.crud.constant.Constants
import com.android.crud.model.ResponseKaryawan
import com.android.crud.network.RestApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailsUseCase(
    private val compositeDisposable: CompositeDisposable,
    private var restApi: RestApi
) {
    fun fetchDetailsKaryawan(
        id: String,
        responseHandler: (ResponseKaryawan) -> Unit,
        errorHandler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            restApi.getDetailsKaryawan(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    responseHandler(it)
                }, {
                    errorHandler(it)
                })
        )
    }

}