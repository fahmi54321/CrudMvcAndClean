package com.android.crud.view.common.navigator

import android.app.Activity
import com.android.crud.view.karyawandetails.DetailsActivity
import com.android.crud.view.karyawanform.FormKaryawanActivity

class ScreenNavigator(
    private val activity: Activity
) {

    fun toDetailsActivity(id: String) {
        DetailsActivity.start(activity, id)
    }

    fun toFormKaryawanActivity() {
        FormKaryawanActivity.start(activity)
    }

    fun onBackActivity(){
        activity.onBackPressed()
    }

}