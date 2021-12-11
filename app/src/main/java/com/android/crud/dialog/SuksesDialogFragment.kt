package com.android.crud.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.android.crud.view.karyawanviews.MainActivity
import com.android.crud.R

class SuksesDialogFragment(private val message: String, private val aksi: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity).let {
            it.setTitle(message)
            it.setMessage(R.string.save_sukses_dialog_message)
            it.setPositiveButton(R.string.server_error_dialog_button_caption) { _, _ -> if (aksi.equals("create")){backToHome()} else if(aksi.equals("delete")) {reload()} }
            it.create()
        }
    }

    private fun backToHome() {
        MainActivity.start(requireContext())
    }

    private fun reload(){
        MainActivity.reload(requireContext())
    }

    companion object {
        fun newInstance(message: String, aksi: String): SuksesDialogFragment {
            return SuksesDialogFragment(message,aksi)
        }
    }
}