package com.android.crud.view.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.android.crud.MyApplication
import com.android.crud.common.dependencyinjection.activity.ActivityModule
import com.android.crud.common.dependencyinjection.presentation.PresentationModule

open class BaseActivity : AppCompatActivity() {

    val appComponent get() = (application as MyApplication).appComponent

    private val activityComponent by lazy {
        appComponent.newActivityComponent(ActivityModule(this))
    }

    private val presentationComponent by lazy {
        activityComponent.newPresentationComponent(PresentationModule())
    }

    protected val injector get() = presentationComponent

}