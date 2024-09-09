package com.example.myapplication.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.activity.DetailActivity
import com.example.myapplication.databinding.ActivityCategoryBinding
import com.example.myapplication.databinding.ItemCategoryDetailBinding
import com.example.myapplication.dto.Project

class AdapterForCategoryDetail(val projectList: List<Project>): RecyclerView.Adapter<AdapterForCategoryDetail.Holder>() {
    class Holder(val binding: ItemCategoryDetailBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemCategoryDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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