package com.android.crud.view.karyawandetails

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.android.crud.BuildConfig
import com.android.crud.constant.Constants
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

    private lateinit var restApi: RestApi
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var id:String
    private lateinit var viewMvc: DetailsViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewMvc = DetailsViewMvc(LayoutInflater.from(this))
        setContentView(viewMvc.binding.root)

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
        viewMvc.showProgressIndication()
        compositeDisposable.add(
            restApi.getDetailsKaryawan(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewMvc.hideProgressIndication()
                    for (x in it.data.indices){
                        viewMvc.bindDetails(it.data.get(x))
                    }
                },{
                    getKaryawanDetailsFailed(it)
                    viewMvc.hideProgressIndication()
                })
        )
    }

    private fun getKaryawanDetailsFailed(throwable: Throwable) {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(throwable), null)
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
        const val EXTRA_ID = "EXTRA_ID"
        fun start(context: Context, id: String) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            context.startActivity(intent)
        }
    }
}