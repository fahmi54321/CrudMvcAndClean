package com.android.crud.view.karyawanviews

import com.android.crud.BuildConfig
import com.android.crud.constant.Constants
import com.android.crud.model.ResponseDeleteKaryawan
import com.android.crud.model.ResponseKaryawan
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
import kotlin.coroutines.cancellation.CancellationException

class MainUseCase(
    private var compositeDisposable: CompositeDisposable
) {

    private var restApi = provideHttpAdapter().create(RestApi::class.java)

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

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                false -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    private fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            retryOnConnectionFailure(true)
            readTimeout(120, TimeUnit.SECONDS);
            connectTimeout(120, TimeUnit.SECONDS);
            writeTimeout(120, TimeUnit.SECONDS);
            addInterceptor(provideHttpLoggingInterceptor())
        }.build()
    }

    fun provideHttpAdapter(): Retrofit {

        var constant: Constants? = null
        constant = Constants()

        return Retrofit.Builder().apply {
            client(provideHttpClient())
            baseUrl(constant.URL)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        }.build()
    }

}