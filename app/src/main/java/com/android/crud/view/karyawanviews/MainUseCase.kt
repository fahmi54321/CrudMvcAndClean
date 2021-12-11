package com.android.crud.view.karyawanviews

import com.android.crud.model.ResponseDeleteKaryawan
import com.android.crud.model.ResponseKaryawan
import com.android.crud.network.RestApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainUseCase(
    private var compositeDisposable: CompositeDisposable,
    private var restApi: RestApi
) {

    fun fetchKaryawan(
        responseHandler: (ResponseKaryawan)->Unit,
        errorHandler: (Throwable)->Unit
    ) {
        compositeDisposable.add(
            restApi.getKaryawan()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    responseHandler(it)

                }, {
                    errorHandler(it)
                })
        )
    }

    fun deleteKaryawan(
        id:String,
        responseHandler: (ResponseDeleteKaryawan)->Unit,
        errorHandler: (Throwable)->Unit
    ){
        compositeDisposable.add(
            restApi.deleteKaryawan(id)
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