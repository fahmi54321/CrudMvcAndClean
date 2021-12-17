package com.android.crud.view.karyawandetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.crud.view.common.activities.BaseActivity
import com.android.crud.view.common.dialog.DialogsNavigator
import com.android.crud.view.common.navigator.ScreenNavigator
import com.android.crud.view.common.viewmvc.ViewMvcFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class DetailsActivity : BaseActivity(), DetailsViewMvc.Listener {

    private lateinit var id: String
    private lateinit var viewMvc: DetailsViewMvc
    @Inject lateinit var compositeDisposable: CompositeDisposable
    @Inject lateinit var detailsUseCase: DetailsUseCase
    @Inject lateinit var dialogsNavigator: DialogsNavigator
    @Inject lateinit var screenNavigator: ScreenNavigator
    @Inject lateinit var viewMvcFactory: ViewMvcFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
        viewMvc = viewMvcFactory.newDetailsMvc()
        setContentView(viewMvc.binding.root)

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