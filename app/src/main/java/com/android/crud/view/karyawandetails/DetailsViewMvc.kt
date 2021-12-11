package com.android.crud.view.karyawandetails

import android.view.LayoutInflater
import android.view.View
import com.android.crud.databinding.ActivityDetailsBinding
import com.android.crud.model.DataItem
import com.android.crud.view.common.BaseViewMvc

class DetailsViewMvc(
    private val layoutInflater: LayoutInflater
):BaseViewMvc<DetailsViewMvc.Listener,ActivityDetailsBinding>(
    layoutInflater
) {

    interface Listener{
        fun onBackClicked()
    }

    init {
        binding.imgBack.setOnClickListener {
            for (listener in listeners){
                listener.onBackClicked()
            }
        }
    }

    fun showProgressIndication() {
        binding.progressBar.visibility = View.VISIBLE
    }

    fun hideProgressIndication() {
        binding.progressBar.visibility = View.GONE
    }

    fun bindDetails(item: DataItem) {
        binding.txtAlamat.text = item.alamat
        binding.txtEmail.text = item.email
        binding.txtNama.text = item.nama
    }

    override val bind: (LayoutInflater) -> ActivityDetailsBinding
        get() = ActivityDetailsBinding::inflate
}