package com.example.myapplication.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemProductAllBinding
import com.example.myapplication.dto.Project

class AdapterForAll(var projectList: List<Project>): RecyclerView.Adapter<AdapterForAll.Holder>() {
    class Holder(bindng: ItemProductAllBinding): RecyclerView.ViewHolder(bindng.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val view = Holder(ItemProductAllBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        return view
    }

    override fun getItemCount(): Int {
//        return productList.size
        return 16
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
    }
}