package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemFavoriteBinding
import com.example.myapplication.dto.Project

class FavoriteAdapter(var projectList: MutableList<Project>): RecyclerView.Adapter<FavoriteAdapter.Holder>() {
    class Holder(val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.Holder {
        return Holder(ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.Holder, position: Int) {
        val project = projectList[position]
        holder.binding.txtUserName.text = project.user.name // 작성자
        holder.binding.txtTitle.text = project.title // 프로젝트 제목
        holder.binding.txtCurrentAmount.text = "${project.currentAmount}" // 모인 금액

        val progressPercentage: Int = ((project.currentAmount.toDouble() / project.goalAmount) * 100).toInt()

        // progressBar에 퍼센트 설정
        holder.binding.progressBar.progress = progressPercentage
    }

    override fun getItemCount(): Int {
        return projectList.size
    }
}