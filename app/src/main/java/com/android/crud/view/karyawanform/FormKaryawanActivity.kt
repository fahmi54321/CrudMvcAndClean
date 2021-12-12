package com.android.crud.view.karyawanform

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.android.crud.BuildConfig
import com.android.crud.MyApplication
import com.android.crud.constant.Constants
import com.android.crud.databinding.ActivityFormKaryawanBinding
import com.android.crud.dialog.FieldKosongDialogFragment
import com.android.crud.dialog.ServerErrorDialogFragment
import com.android.crud.dialog.SuksesDialogFragment
import com.android.crud.network.RestApi
import com.android.crud.view.common.activities.BaseActivity
import com.android.crud.view.common.dialog.DialogsNavigator
import com.android.crud.view.common.navigator.ScreenNavigator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class FormKaryawanActivity : BaseActivity(), FormKaryawanMvcView.Listener {

    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var viewMvc: FormKaryawanMvcView
    private lateinit var formKaryawanUserCase: FormKaryawanUserCase
    private lateinit var dialogsNavigator: DialogsNavigator
    private lateinit var screenNavigator: ScreenNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewMvc = compositionRoot.viewMvcFactory.newFormKaryawanMvc()
        setContentView(viewMvc.binding.root)

        compositeDisposable = compositionRoot.compositeDisposable
        dialogsNavigator = compositionRoot.dialogsNavigator
        screenNavigator = compositionRoot.screenNavigator
        formKaryawanUserCase = compositionRoot.formKaryawanUserCase

    }

    private fun dialogFieldKosong(item: String) {
        dialogsNavigator.showAlertMessage(item)
    }

    private fun saveKaryawan(nama: String, email: String, alamat: String) {
        viewMvc.showProgressIndication()
        formKaryawanUserCase.registerKaryawan(nama, email, alamat, {
            if (it.meta?.code == 200) {
                dialogSuksesRegister(it.meta.message ?: "", "create")
                viewMvc.hideProgressIndication()
            }
            viewMvc.hideProgressIndication()
        }, {
            getKaryawanDetailsFailed(it)
            viewMvc.hideProgressIndication()
        })
    }

    override fun onSaveKaryawan(nama: String, email: String, alamat: String) {
        saveKaryawan(nama, email, alamat)
    }

    override fun onDialogFieldKosong(message: String) {
        dialogFieldKosong(message)
    }

    override fun onBackClicked() {
        screenNavigator.onBackActivity()
    }

    private fun getKaryawanDetailsFailed(throwable: Throwable) {
        dialogsNavigator.showAlertMessage(throwable.message.toString())
    }

    private fun dialogSuksesRegister(message: String, aksi: String) {
        dialogsNavigator.showSuksesDialog(message, aksi)
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

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, FormKaryawanActivity::class.java)
            context.startActivity(intent)
        }
    }
}