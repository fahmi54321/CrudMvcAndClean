package com.android.crud.common.dependencyinjection.presentation

import com.android.crud.common.dependencyinjection.activity.ActivityComponent
import com.android.crud.view.karyawandetails.DetailsActivity
import com.android.crud.view.karyawanform.FormKaryawanActivity
import com.android.crud.view.karyawanviews.MainActivity
import dagger.Component
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [PresentationModule::class, UseCaseModule::class])
interface PresentationComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(formKaryawanActivity: FormKaryawanActivity)
    fun inject(detailsActivity: DetailsActivity)

}