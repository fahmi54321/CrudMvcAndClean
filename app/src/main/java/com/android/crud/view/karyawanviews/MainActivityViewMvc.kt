package com.android.crud.view.karyawanviews

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.crud.adapter.KaryawanAdapter
import com.android.crud.databinding.ActivityMainBinding
import com.android.crud.model.DataItem
import com.android.crud.model.ResponseKaryawan
import com.android.crud.view.common.BaseViewMvc
import com.android.crud.view.karyawandetails.DetailsActivity

class MainActivityViewMvc(
    layoutInflater: LayoutInflater
):BaseViewMvc<MainActivityViewMvc.Listener,ActivityMainBinding>(
    layoutInflater
) {
    interface Listener {
        fun onDeleteKaryawan(item: DataItem)
        fun goToFormKaryawan()
    }

    private var karyawanAdapter: KaryawanAdapter
    init {
        binding.rvKaryawan.layoutManager = LinearLayoutManager(context)
        karyawanAdapter = KaryawanAdapter(object : KaryawanAdapter.onClickListener {
            override fun itemClick(item: DataItem) {
                DetailsActivity.start(context, item.id.toString())
            }

            override fun itemDelete(item: DataItem) {
                for (listener in listeners){
                    listener.onDeleteKaryawan(item)
                }
            }

        })
        binding.rvKaryawan.adapter = karyawanAdapter
        binding.fabAdd.setOnClickListener {
            for (listener in listeners){
                listener.goToFormKaryawan()
            }
        }
    }

    fun showProgressIndication() {
        binding.progressBar.visibility = View.VISIBLE
    }

    fun hideProgressIndication() {
        binding.progressBar.visibility = View.GONE
    }

    fun bindKaryawan(responseKaryawan: ResponseKaryawan) {
        karyawanAdapter.setList(responseKaryawan.data)
        karyawanAdapter.notifyDataSetChanged()
    }

    override val bind: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate
}