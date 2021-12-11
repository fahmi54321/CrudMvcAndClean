package com.android.crud

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class FormKaryawanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormKaryawanBinding
    private lateinit var restApi: RestApi
    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormKaryawanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restApi = provideHttpAdapter().create(RestApi::class.java)
        compositeDisposable = CompositeDisposable()

        binding.btnSimpan.setOnClickListener {
            var nama = binding.edtNama.text.toString()
            var email = binding.edtEmail.text.toString()
            var alamat = binding.edtAlamat.text.toString()
            if (nama.isNullOrEmpty()){
                dialogFieldKosong("nama kosong")
            }else if (email.isNullOrEmpty()){
                dialogFieldKosong("email kosong")
            }else if (alamat.isNullOrEmpty()){
                dialogFieldKosong("alamat kosong")
            }else {
                saveKaryawan(nama, email, alamat)
            }
        }

    }

    private fun saveKaryawan(nama: String, email: String, alamat: String) {
        showProgressIndication()
        compositeDisposable.add(
            restApi.addKaryawan(nama, email, alamat)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.meta?.code == 200){
                        dialogSuksesRegister(it.meta.message?:"","create")
                        hideProgressIndication()
                    }
                    hideProgressIndication()
                }, {
                    getKaryawanDetailsFailed(it)
                    hideProgressIndication()
                })
        )
    }

    private fun getKaryawanDetailsFailed(throwable: Throwable) {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(throwable), null)
            .commitAllowingStateLoss()
    }

    private fun dialogSuksesRegister(message:String,aksi:String) {
        supportFragmentManager.beginTransaction()
            .add(SuksesDialogFragment.newInstance(message, aksi), null)
            .commitAllowingStateLoss()
    }

    private fun showProgressIndication() {
        binding.btnSimpan.text = "Loading"
    }

    private fun hideProgressIndication() {
        binding.btnSimpan.text = "Simpan"
    }

    private fun dialogFieldKosong(item: String) {
        supportFragmentManager.beginTransaction()
            .add(FieldKosongDialogFragment.newInstance(item), null)
            .commitAllowingStateLoss()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
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