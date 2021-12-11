package com.android.crud.view.karyawanform

import android.view.LayoutInflater
import com.android.crud.databinding.ActivityFormKaryawanBinding
import com.android.crud.dialog.FieldKosongDialogFragment
import com.android.crud.view.common.BaseViewMvc

class FormKaryawanMvcView(
    private val layoutInflater: LayoutInflater,
):BaseViewMvc<FormKaryawanMvcView.Listener,ActivityFormKaryawanBinding>(
    layoutInflater
) {

    interface Listener {
        fun onDialogFieldKosong(message: String)
        fun onSaveKaryawan(nama: String, email: String, alamat: String)
        fun onBackClicked()
    }

    init {
        binding.btnSimpan.setOnClickListener {
            var nama = binding.edtNama.text.toString()
            var email = binding.edtEmail.text.toString()
            var alamat = binding.edtAlamat.text.toString()
            if (nama.isNullOrEmpty()) {
                for (listener in listeners) {
                    listener.onDialogFieldKosong("nama kosong")
                }
            } else if (email.isNullOrEmpty()) {
                for (listener in listeners) {
                    listener.onDialogFieldKosong("email kosong")
                }
            } else if (alamat.isNullOrEmpty()) {
                for (listener in listeners) {
                    listener.onDialogFieldKosong("alamat kosong")
                }
            } else {
                for (listener in listeners) {
                    listener.onSaveKaryawan(nama, email, alamat)
                }
            }
        }
        binding.imgBack.setOnClickListener {
            for (listener in listeners){
                listener.onBackClicked()
            }
        }
    }

    fun showProgressIndication() {
        binding.btnSimpan.text = "Loading"
    }

    fun hideProgressIndication() {
        binding.btnSimpan.text = "Simpan"
    }

    override val bind: (LayoutInflater) -> ActivityFormKaryawanBinding
        get() = ActivityFormKaryawanBinding::inflate
}