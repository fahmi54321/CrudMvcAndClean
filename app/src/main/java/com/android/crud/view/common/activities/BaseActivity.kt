package com.android.crud.view.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.android.crud.MyApplication
import com.android.crud.common.dependencyinjection.ActivityCompositionRoot
import com.android.crud.common.dependencyinjection.Injector
import com.android.crud.common.dependencyinjection.PresentationCompositionRoot

open class BaseActivity : AppCompatActivity() {

    val appCompositionRoot get() = (application as MyApplication).appCompositionRoot

    val activityCompositionRoot by lazy {
        ActivityCompositionRoot(appCompositionRoot, this)
    }

    protected val compositionRoot by lazy {
        PresentationCompositionRoot(activityCompositionRoot)
    }

    protected val injector get() = Injector(compositionRoot)

}