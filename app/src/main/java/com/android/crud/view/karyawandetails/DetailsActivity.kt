package com.android.crud.view.karyawandetails

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.android.crud.MyApplication
import com.android.crud.dialog.ServerErrorDialogFragment
import com.android.crud.view.common.activities.BaseActivity
import com.android.crud.view.common.dialog.DialogsNavigator
import com.android.crud.view.common.navigator.ScreenNavigator
import io.reactivex.rxjava3.disposables.CompositeDisposable

class DetailsActivity : BaseActivity(), DetailsViewMvc.Listener {

    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var id: String
    private lateinit var viewMvc: DetailsViewMvc
    private lateinit var detailsUseCase: DetailsUseCase
    private lateinit var dialogsNavigator: DialogsNavigator
    private lateinit var screenNavigator: ScreenNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewMvc = compositionRoot.viewMvcFactory.newDetailsMvc()
        setContentView(viewMvc.binding.root)

        compositeDisposable = CompositeDisposable()
        dialogsNavigator = compositionRoot.dialogsNavigator
        screenNavigator = compositionRoot.screenNavigator
        detailsUseCase = compositionRoot.detailsUseCase

        id = intent.getStringExtra(EXTRA_ID)!!
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
        getDetailsKaryawan()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
        viewMvc.unRegisterListener(this)
    }

    private fun getDetailsKaryawan() {
        viewMvc.showProgressIndication()
        detailsUseCase.fetchDetailsKaryawan(id, {
            viewMvc.hideProgressIndication()
            for (x in it.data.indices) {
                viewMvc.bindDetails(it.data.get(x))
            }
        }, {
            getKaryawanDetailsFailed(it)
            viewMvc.hideProgressIndication()
        })
    }

    private fun getKaryawanDetailsFailed(throwable: Throwable) {
        dialogsNavigator.showAlertMessage(throwable.message.toString())
    }

    override fun onBackClicked() {
        screenNavigator.onBackActivity()
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