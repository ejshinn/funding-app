package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemFavoriteBinding
import com.example.myapplication.retrofitPacket.ProjectDetail
import java.text.NumberFormat
import java.util.Locale

class FavoriteAdapter(var projectList: MutableList<ProjectDetail>): RecyclerView.Adapter<FavoriteAdapter.Holder>() {
    class Holder(val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.Holder {
        return Holder(ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.Holder, position: Int) {
        val project = projectList[position]

        Glide.with(holder.itemView.context)
            .load(project.thumbnail)
            .into(holder.binding.imageView)

        holder.binding.txtUserName.text = project.user.name // 작성자
        holder.binding.txtTitle.text = project.title // 프로젝트 제목

        val numberFormat = NumberFormat.getNumberInstance(Locale.KOREA)
        val formattedAmount = numberFormat.format(project.currentAmount)
        holder.binding.txtCurrentAmount.text = "${formattedAmount}원" // 모인 금액

        val progressPercentage: Int = ((project.currentAmount.toDouble() / project.goalAmount) * 100).toInt()

        // 퍼센트 설정
        holder.binding.txtProgressPercentage.text = progressPercentage.toString() + "%"
        holder.binding.progressBar.progress = progressPercentage


        // 아이템 클릭 시 해당 프로젝트 상세 화면으로 이동
//        holder.itemView.setOnClickListener {
//            val intent = Intent(this, DetailActivity::class.java)
//            startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    fun updateData(newProjects: List<ProjectDetail>) {
        projectList.clear()
        projectList.addAll(newProjects)
        notifyDataSetChanged()
    }
}