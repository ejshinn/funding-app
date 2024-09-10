package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityCategoryBinding
import com.example.myapplication.databinding.ItemCategoryDetailBinding
import com.example.myapplication.retrofitPacket.ProjectDetail

class AdapterForCategoryDetail(var projectList: MutableList<ProjectDetail>): RecyclerView.Adapter<AdapterForCategoryDetail.Holder>() {
    class Holder(val binding: ItemCategoryDetailBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemCategoryDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val project = projectList[position]
        holder.binding.apply {
            textViewUser.text = project.user.name
            textViewTitle.text = project.title
            textViewTotal.text = project.percent()
            textViewDeadline.text = project.calculateDday()
        }
    }


}