package com.android.crud.view.common.viewmvc

import android.view.LayoutInflater
import com.android.crud.view.karyawandetails.DetailsViewMvc
import com.android.crud.view.karyawanform.FormKaryawanMvcView
import com.android.crud.view.karyawanviews.MainActivityViewMvc

class ViewMvcFactory(
    private val layoutInflater: LayoutInflater
) {

    fun newMainActivityViewMvc(): MainActivityViewMvc {
        return MainActivityViewMvc(layoutInflater)
    }

    fun newFormKaryawanMvc(): FormKaryawanMvcView {
        return FormKaryawanMvcView(layoutInflater)
    }

    fun newDetailsMvc(): DetailsViewMvc {
        return DetailsViewMvc(layoutInflater)
    }

}