package com.android.crud.view.karyawanform

import com.android.crud.BuildConfig
import com.android.crud.constant.Constants
import com.android.crud.model.ResponseCreateKaryawan
import com.android.crud.network.RestApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class FormKaryawanUserCase(
    private val compositeDisposable: CompositeDisposable,
    private var restApi: RestApi
) {

    fun registerKaryawan(
        nama: String,
        email: String,
        alamat: String,
        responseHandler: (ResponseCreateKaryawan) -> Unit,
        errorHandler: (Throwable) -> Unit,
    ) {
        compositeDisposable.add(
            restApi.addKaryawan(nama, email, alamat)
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