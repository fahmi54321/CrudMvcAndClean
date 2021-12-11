package com.android.crud

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.crud.constant.Constants
import com.android.crud.databinding.ActivityDetailsBinding
import com.android.crud.dialog.ServerErrorDialogFragment
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

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var restApi: RestApi
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var id:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restApi = provideHttpAdapter().create(RestApi::class.java)
        compositeDisposable = CompositeDisposable()


        id = intent.getStringExtra(EXTRA_ID)!!
    }

    override fun onStart() {
        super.onStart()
        getDetailsKaryawan()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    private fun getDetailsKaryawan(){
        showProgressIndication()
        compositeDisposable.add(
            restApi.getDetailsKaryawan(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    hideProgressIndication()
                    for (x in it.data.indices){
                        binding.txtAlamat.text = it.data.get(x).alamat
                        binding.txtEmail.text = it.data.get(x).email
                        binding.txtNama.text = it.data.get(x).nama
                    }
                },{
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

    private fun showProgressIndication() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressIndication() {
        binding.progressBar.visibility = View.GONE
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
        const val EXTRA_ID = "EXTRA_ID"
        fun start(context: Context, id: String) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            context.startActivity(intent)
        }
    }
}