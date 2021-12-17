package com.android.crud.view.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.android.crud.MyApplication
import com.android.crud.common.composition.ActivityCompositionRoot
import com.android.crud.common.composition.Injector
import com.android.crud.common.composition.PresentationCompositionRoot

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