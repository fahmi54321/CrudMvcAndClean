package com.android.crud.view.karyawanform

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.crud.view.common.activities.BaseActivity
import com.android.crud.view.common.dialog.DialogsNavigator
import com.android.crud.view.common.navigator.ScreenNavigator
import com.android.crud.view.common.viewmvc.ViewMvcFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class FormKaryawanActivity : BaseActivity(), FormKaryawanMvcView.Listener {

    private lateinit var viewMvc: FormKaryawanMvcView
    @Inject lateinit var compositeDisposable: CompositeDisposable
    @Inject lateinit var formKaryawanUserCase: FormKaryawanUserCase
    @Inject lateinit var dialogsNavigator: DialogsNavigator
    @Inject lateinit var screenNavigator: ScreenNavigator
    @Inject lateinit var viewMvcFactory: ViewMvcFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
        viewMvc = viewMvcFactory.newFormKaryawanMvc()
        setContentView(viewMvc.binding.root)
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