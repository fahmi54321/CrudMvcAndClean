package com.android.crud.view.karyawanviews

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.android.crud.MyApplication
import com.android.crud.dialog.ServerErrorDialogFragment
import com.android.crud.dialog.SuksesDialogFragment
import com.android.crud.model.DataItem
import com.android.crud.view.common.activities.BaseActivity
import com.android.crud.view.common.dialog.DialogsNavigator
import com.android.crud.view.common.navigator.ScreenNavigator
import com.android.crud.view.karyawandetails.DetailsActivity
import com.android.crud.view.karyawanform.FormKaryawanActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainActivity() : BaseActivity(), MainActivityViewMvc.Listener {

    private lateinit var viewMvc: MainActivityViewMvc
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var mainUseCase: MainUseCase
    private lateinit var dialogsNavigator: DialogsNavigator
    private lateinit var screenNavigator: ScreenNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewMvc = compositionRoot.viewMvcFactory.newMainActivityViewMvc()
        setContentView(viewMvc.binding.root)

        compositeDisposable = compositionRoot.compositeDisposable
        dialogsNavigator = compositionRoot.dialogsNavigator
        screenNavigator = compositionRoot.screenNavigator
        mainUseCase = compositionRoot.mainUseCase

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
        mainUseCase.fetchKaryawan({
            viewMvc.bindKaryawan(it)
            viewMvc.hideProgressIndication()
        }, {
            getKaryawanFailed(it)
            viewMvc.hideProgressIndication()

        })
    }

    private fun hapusKaryawan(item: DataItem) {
        viewMvc.showProgressIndication()
        mainUseCase.deleteKaryawan(item.id.toString(), {
            viewMvc.hideProgressIndication()
            suksesDialog(it.meta?.message ?: "", "delete")
        }, {
            getKaryawanFailed(it)
            viewMvc.hideProgressIndication()
        })
    }

    override fun onDeleteKaryawan(item: DataItem) {
        hapusKaryawan(item)
    }

    override fun goToFormKaryawan() {
        screenNavigator.toFormKaryawanActivity()
    }

    override fun onDetailsActivity(id: String) {
        screenNavigator.toDetailsActivity(id)
    }

    private fun getKaryawanFailed(throwable: Throwable) {
        dialogsNavigator.showAlertMessage(throwable.message.toString())
    }

    private fun suksesDialog(message: String, aksi: String) {
        dialogsNavigator.showSuksesDialog(message, aksi)
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