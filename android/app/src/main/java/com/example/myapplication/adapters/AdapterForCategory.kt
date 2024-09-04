package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemCategoryBinding

class AdapterForCategory(var categoryList: List<Int>): RecyclerView.Adapter<AdapterForCategory.Holder>() {
    class Holder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
//        return categoryList.size
        return 8
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

    }
}