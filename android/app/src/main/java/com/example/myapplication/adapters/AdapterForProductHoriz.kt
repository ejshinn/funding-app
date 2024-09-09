package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemProductBinding
import com.example.myapplication.databinding.ItemProductHorizonBinding
import com.example.myapplication.dto.Project

class AdapterForProductHoriz(var projectList: List<Project>): RecyclerView.Adapter<AdapterForProductHoriz.Holder>() {
    class Holder(binding: ItemProductHorizonBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemProductHorizonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
//        return productList.size
        return 13
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
    }

}