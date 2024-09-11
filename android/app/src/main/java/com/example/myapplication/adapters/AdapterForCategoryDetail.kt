package com.example.myapplication.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.activity.DetailActivity
import com.example.myapplication.databinding.ItemCategoryDetailBinding
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.squareup.picasso.Picasso

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
            progressBar.progress = project.progress()
            textViewDeadline.text = project.calculateDday()
        }

        Picasso.get()
            .load(project.thumbnail)
            .into(holder.binding.imageView5)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("project", project)
            context.startActivity(intent)
        }

    }


}