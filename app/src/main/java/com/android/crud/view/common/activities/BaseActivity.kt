package com.android.crud.view.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.android.crud.MyApplication

open class BaseActivity:AppCompatActivity() {

    val appCompositionRoot get() = (application as MyApplication).appCompositionRoot

}