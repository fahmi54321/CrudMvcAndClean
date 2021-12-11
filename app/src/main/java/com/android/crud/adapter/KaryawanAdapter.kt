package com.android.crud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.crud.databinding.ItemKaryawanBinding
import com.android.crud.model.DataItem

class KaryawanAdapter(
    private val onClick: onClickListener
) : RecyclerView.Adapter<KaryawanAdapter.KaryawanViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KaryawanViewHolder {
        val view = ItemKaryawanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KaryawanViewHolder(view)
    }

    private val karyawanList= ArrayList<DataItem>()

    override fun onBindViewHolder(holder: KaryawanViewHolder, position: Int) {
        holder.bind(karyawanList[position])
        holder.itemView.setOnClickListener {
            onClick.itemClick(karyawanList[position])
        }
        holder.binding.imgDelete.setOnClickListener {
            onClick.itemDelete(karyawanList[position])
        }
    }

    override fun getItemCount(): Int {
        return karyawanList.size
    }

    fun setList(produk: List<DataItem>){
        karyawanList.clear()
        karyawanList.addAll(produk)
    }

    class KaryawanViewHolder(val binding: ItemKaryawanBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataItem) {
            binding.txtNama.text = item.nama
            binding.txtEmail.text = item.email
            binding.txtAlamat.text = item.alamat
        }
    }

    interface onClickListener{
        fun itemClick(item: DataItem)
        fun itemDelete(item: DataItem)
    }

}