package com.android.crud.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.android.crud.MainActivity
import com.android.crud.R

class FieldKosongDialogFragment(private val message: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity).let {
            it.setTitle(R.string.konformasi_dialog_title)
            it.setMessage("$message")
            it.setPositiveButton(R.string.server_error_dialog_button_caption) { _, _ -> dismiss() }
            it.create()
        }
    }

    companion object {
        fun newInstance(item: String): FieldKosongDialogFragment {
            return FieldKosongDialogFragment(item)
        }
    }
}