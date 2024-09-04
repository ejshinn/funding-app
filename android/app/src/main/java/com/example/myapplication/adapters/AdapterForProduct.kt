package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemProductBinding

class AdapterForProduct(var productList: List<String>): RecyclerView.Adapter<AdapterForProduct.Holder>() {
    class Holder(val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
//        return productList.size
        return 8
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
    }
}