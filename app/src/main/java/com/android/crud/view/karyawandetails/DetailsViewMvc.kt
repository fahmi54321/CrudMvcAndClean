package com.android.crud.view.karyawandetails

import android.view.LayoutInflater
import android.view.View
import com.android.crud.databinding.ActivityDetailsBinding
import com.android.crud.model.DataItem

class DetailsViewMvc(
    private val layoutInflater: LayoutInflater
) {

    var binding = ActivityDetailsBinding.inflate(layoutInflater)

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
}