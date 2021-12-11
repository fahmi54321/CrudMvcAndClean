package com.android.crud.view.common.dialog

import androidx.fragment.app.FragmentManager
import com.android.crud.dialog.ServerErrorDialogFragment
import com.android.crud.dialog.SuksesDialogFragment

class DialogsNavigator(
    private val fragmentManager: FragmentManager
) {

    fun showAlertMessage(message: String){
        fragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(message), null)
            .commitAllowingStateLoss()
    }

    fun showSuksesDialog(message: String, aksi: String) {
        fragmentManager.beginTransaction()
            .add(SuksesDialogFragment.newInstance(message, aksi), null)
            .commitAllowingStateLoss()
    }

}