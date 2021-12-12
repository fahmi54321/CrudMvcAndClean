package com.android.crud.view.common.viewmvc

import android.content.Context
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

open abstract class BaseViewMvc<LISTENER_TYPE, VB:ViewBinding>(
    private val layoutInflater: LayoutInflater,
) {

    //base view listener
    protected val listeners = HashSet<LISTENER_TYPE>()

    //base binding
    abstract val bind: (LayoutInflater)->VB
    val binding = bind.invoke(layoutInflater)


    //base view context
    protected val context: Context get() = binding.root.context

    //base view listener
    fun registerListener(listener: LISTENER_TYPE){
        listeners.add(listener)
    }
    fun unRegisterListener(listener: LISTENER_TYPE){
        listeners.remove(listener)
    }
}