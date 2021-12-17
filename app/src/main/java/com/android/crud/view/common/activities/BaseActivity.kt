package com.android.crud.view.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.android.crud.MyApplication
import com.android.crud.common.dependencyinjection.activity.ActivityModule
import com.android.crud.common.dependencyinjection.Injector
import com.android.crud.common.dependencyinjection.activity.DaggerActivityComponent
import com.android.crud.common.dependencyinjection.presentation.DaggerPresentationComponent
import com.android.crud.common.dependencyinjection.presentation.PresentationModule

open class BaseActivity : AppCompatActivity() {

    val appComponent get() = (application as MyApplication).appComponent

    private val activityComponent by lazy {
        DaggerActivityComponent.builder()
            .activityModule(ActivityModule(appComponent,this))
            .build()
    }

    private val presentationComponent by lazy {
        DaggerPresentationComponent.builder()
            .presentationModule(PresentationModule(activityComponent))
            .build()
    }

    protected val injector get() = Injector(presentationComponent)

}