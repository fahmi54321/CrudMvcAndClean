package com.android.crud.view.karyawanviews

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.crud.BuildConfig
import com.android.crud.adapter.KaryawanAdapter
import com.android.crud.constant.Constants
import com.android.crud.databinding.ActivityMainBinding
import com.android.crud.dialog.ServerErrorDialogFragment
import com.android.crud.dialog.SuksesDialogFragment
import com.android.crud.model.DataItem
import com.android.crud.network.RestApi
import com.android.crud.view.karyawandetails.DetailsActivity
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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var restApi: RestApi
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var karyawanAdapter: KaryawanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init recycler view
        binding.rvKaryawan.layoutManager = LinearLayoutManager(this)
        karyawanAdapter = KaryawanAdapter(object : KaryawanAdapter.onClickListener {
            override fun itemClick(item: DataItem) {
                DetailsActivity.start(this@MainActivity, item.id.toString())
            }

            override fun itemDelete(item: DataItem) {
                hapusKaryawan(item)
            }

        })
        binding.rvKaryawan.adapter = karyawanAdapter
        binding.fabAdd.setOnClickListener {
            FormKaryawanActivity.start(this)
        }

        restApi = provideHttpAdapter().create(RestApi::class.java)
        compositeDisposable = CompositeDisposable()

    }

    override fun onStart() {
        super.onStart()
        getKaryawan()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    private fun getKaryawan() {
        showProgressIndication()
        compositeDisposable.add(
            restApi.getKaryawan()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    karyawanAdapter.setList(it.data)
                    karyawanAdapter.notifyDataSetChanged()
                    hideProgressIndication()

                }, {
                    getKaryawanFailed(it)
                    hideProgressIndication()
                })
        )
    }

    private fun hapusKaryawan(item: DataItem) {
        showProgressIndication()
        compositeDisposable.add(
            restApi.deleteKaryawan(item.id.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    hideProgressIndication()
                    suksesDialog(it.meta?.message?:"","delete")
                },{
                    getKaryawanFailed(it)
                    hideProgressIndication()
                })
        )
    }

    private fun getKaryawanFailed(throwable: Throwable) {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(throwable), null)
            .commitAllowingStateLoss()
    }

    private fun suksesDialog(message: String,aksi:String) {
        supportFragmentManager.beginTransaction()
            .add(SuksesDialogFragment.newInstance(message,aksi), null)
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
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
        fun reload(context: Context){
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}