package com.android.crud.view.karyawanviews

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.android.crud.BuildConfig
import com.android.crud.constant.Constants
import com.android.crud.dialog.ServerErrorDialogFragment
import com.android.crud.dialog.SuksesDialogFragment
import com.android.crud.model.DataItem
import com.android.crud.network.RestApi
import com.android.crud.view.karyawanform.FormKaryawanActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), MainActivityViewMvc.Listener {

    private lateinit var restApi: RestApi
    private lateinit var viewMvc: MainActivityViewMvc
    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewMvc = MainActivityViewMvc(LayoutInflater.from(this))
        setContentView(viewMvc.binding.root)

        compositeDisposable = CompositeDisposable()
        restApi = provideHttpAdapter().create(RestApi::class.java)

    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
        getKaryawan()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
        viewMvc.unRegisterListener(this)
    }

    private fun getKaryawan() {
        viewMvc.showProgressIndication()
        compositeDisposable.add(
            restApi.getKaryawan()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewMvc.bindKaryawan(it)
                    viewMvc.hideProgressIndication()

                }, {
                    getKaryawanFailed(it)
                    viewMvc.hideProgressIndication()
                })
        )
    }

    private fun hapusKaryawan(item: DataItem) {
        viewMvc.showProgressIndication()
        compositeDisposable.add(
            restApi.deleteKaryawan(item.id.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewMvc.hideProgressIndication()
                    suksesDialog(it.meta?.message ?: "", "delete")
                }, {
                    getKaryawanFailed(it)
                    viewMvc.hideProgressIndication()
                })
        )
    }

    override fun onDeleteKaryawan(item: DataItem) {
        hapusKaryawan(item)
    }

    override fun goToFormKaryawan() {
        FormKaryawanActivity.start(this)
    }

    private fun getKaryawanFailed(throwable: Throwable) {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(throwable), null)
            .commitAllowingStateLoss()
    }

    private fun suksesDialog(message: String, aksi: String) {
        supportFragmentManager.beginTransaction()
            .add(SuksesDialogFragment.newInstance(message, aksi), null)
            .commitAllowingStateLoss()
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

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }

        fun reload(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}