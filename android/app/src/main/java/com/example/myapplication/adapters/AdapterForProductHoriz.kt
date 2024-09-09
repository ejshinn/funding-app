package com.example.myapplication.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.activity.DetailActivity
import com.example.myapplication.databinding.ItemProductBinding
import com.example.myapplication.databinding.ItemProductHorizonBinding
import com.example.myapplication.dto.Project

class AdapterForProductHoriz(var projectList: List<Project>): RecyclerView.Adapter<AdapterForProductHoriz.Holder>() {
    class Holder(binding: ItemProductHorizonBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemProductHorizonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val project = projectList[position]
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("project", project)
            context.startActivity(intent)
        }
    }

}