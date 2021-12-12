package com.android.crud.view.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.android.crud.MyApplication
import com.android.crud.common.composition.ActivityCompositionRoot

open class BaseActivity:AppCompatActivity() {

    val appCompositionRoot get() = (application as MyApplication).appCompositionRoot

    val compositionRoot by lazy {
        ActivityCompositionRoot(appCompositionRoot,this)
    }

}