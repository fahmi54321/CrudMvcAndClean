package com.android.crud.view.karyawanform

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.android.crud.BuildConfig
import com.android.crud.constant.Constants
import com.android.crud.databinding.ActivityFormKaryawanBinding
import com.android.crud.dialog.FieldKosongDialogFragment
import com.android.crud.dialog.ServerErrorDialogFragment
import com.android.crud.dialog.SuksesDialogFragment
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

class FormKaryawanActivity : AppCompatActivity(), FormKaryawanMvcView.Listener {

    private lateinit var restApi: RestApi
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var viewMvc: FormKaryawanMvcView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewMvc = FormKaryawanMvcView(LayoutInflater.from(this))
        setContentView(viewMvc.binding.root)

        restApi = provideHttpAdapter().create(RestApi::class.java)
        compositeDisposable = CompositeDisposable()

    }

    private fun dialogFieldKosong(item: String) {
        supportFragmentManager.beginTransaction()
            .add(FieldKosongDialogFragment.newInstance(item), null)
            .commitAllowingStateLoss()
    }


    private fun saveKaryawan(nama: String, email: String, alamat: String) {
        viewMvc.showProgressIndication()
        compositeDisposable.add(
            restApi.addKaryawan(nama, email, alamat)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.meta?.code == 200) {
                        dialogSuksesRegister(it.meta.message ?: "", "create")
                        viewMvc.hideProgressIndication()
                    }
                    viewMvc.hideProgressIndication()
                }, {
                    getKaryawanDetailsFailed(it)
                    viewMvc.hideProgressIndication()
                })
        )
    }

    override fun onSaveKaryawan(nama: String, email: String, alamat: String) {
        saveKaryawan(nama, email, alamat)
    }

    override fun onDialogFieldKosong(message: String) {
        dialogFieldKosong(message)
    }

    override fun onBackClicked() {
        onBackPressed()
    }

    private fun getKaryawanDetailsFailed(throwable: Throwable) {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(throwable), null)
            .commitAllowingStateLoss()
    }

    private fun dialogSuksesRegister(message: String, aksi: String) {
        supportFragmentManager.beginTransaction()
            .add(SuksesDialogFragment.newInstance(message, aksi), null)
            .commitAllowingStateLoss()
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
        viewMvc.unRegisterListener(this)
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
            val intent = Intent(context, FormKaryawanActivity::class.java)
            context.startActivity(intent)
        }
    }
}