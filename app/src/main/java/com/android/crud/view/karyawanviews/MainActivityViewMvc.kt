package com.android.crud.view.karyawanviews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.crud.adapter.KaryawanAdapter
import com.android.crud.databinding.ActivityMainBinding
import com.android.crud.model.DataItem
import com.android.crud.model.ResponseKaryawan
import com.android.crud.view.karyawandetails.DetailsActivity

class MainActivityViewMvc(
    private val layoutInflater: LayoutInflater
) {

    interface Listener {
        fun onDeleteKaryawan(item: DataItem)
        fun goToFormKaryawan()
    }

    private val listeners = HashSet<Listener>()
    private var karyawanAdapter: KaryawanAdapter

    val binding = ActivityMainBinding.inflate(layoutInflater)
    private val context: Context get() = binding.root.context

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

    fun registerListener(listener: Listener){
        listeners.add(listener)
    }
    fun unRegisterListener(listener: Listener){
        listeners.remove(listener)
    }

    fun bindKaryawan(responseKaryawan: ResponseKaryawan) {
        karyawanAdapter.setList(responseKaryawan.data)
        karyawanAdapter.notifyDataSetChanged()
    }

}