package com.android.crud.common.composition

import androidx.annotation.UiThread
import com.android.crud.BuildConfig
import com.android.crud.constant.Constants
import com.android.crud.network.RestApi
import com.android.crud.view.karyawandetails.DetailsUseCase
import com.android.crud.view.karyawanform.FormKaryawanUserCase
import com.android.crud.view.karyawanviews.MainUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@UiThread
class AppCompositionRoot {


    private val restApi:RestApi by lazy {
        retrofit.create(RestApi::class.java)
    }

    fun main(compositeDisposable: CompositeDisposable): MainUseCase {
        return MainUseCase(compositeDisposable, restApi)
    }

    fun form(compositeDisposable: CompositeDisposable): FormKaryawanUserCase {
        return FormKaryawanUserCase(compositeDisposable, restApi)
    }

    fun details(compositeDisposable: CompositeDisposable): DetailsUseCase {
        return DetailsUseCase(compositeDisposable, restApi)
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


    private val retrofit : Retrofit by lazy {
        var constant: Constants? = null
        constant = Constants()
        Retrofit.Builder().apply {
            client(provideHttpClient())
            baseUrl(constant.URL)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        }.build()
    }

}