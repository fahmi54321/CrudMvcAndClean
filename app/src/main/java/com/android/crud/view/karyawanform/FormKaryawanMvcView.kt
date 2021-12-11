package com.android.crud.view.karyawanform

import android.view.LayoutInflater
import com.android.crud.databinding.ActivityFormKaryawanBinding
import com.android.crud.dialog.FieldKosongDialogFragment

class FormKaryawanMvcView(
    private val layoutInflater: LayoutInflater
) {

    interface Listener {
        fun onDialogFieldKosong(message: String)
        fun onSaveKaryawan(nama: String, email: String, alamat: String)

    }

    var binding = ActivityFormKaryawanBinding.inflate(layoutInflater)
    private val listeners = HashSet<Listener>()

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
    }

    fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    fun unRegisterListener(listener: Listener) {
        listeners.remove(listener)
    }

    fun showProgressIndication() {
        binding.btnSimpan.text = "Loading"
    }

    fun hideProgressIndication() {
        binding.btnSimpan.text = "Simpan"
    }
}