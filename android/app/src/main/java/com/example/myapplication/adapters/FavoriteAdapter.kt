package com.example.myapplication.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Login.LoginActivity
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.activity.DetailActivity
import com.example.myapplication.databinding.ItemFavoriteBinding
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.example.myapplication.retrofitPacket.UserPacket
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response
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

        Picasso.get()
            .load(project.thumbnail)
            .into(holder.binding.imageView)

        holder.binding.txtUserName.text = project.user.name // 작성자
        holder.binding.txtTitle.text = project.title // 프로젝트 제목

        val numberFormat = NumberFormat.getNumberInstance(Locale.KOREA)
        val formattedAmount = numberFormat.format(project.currentAmount)
        holder.binding.txtCurrentAmount.text = "${formattedAmount}원" // 모인 금액

        val progressPercentage: Int = ((project.currentAmount.toDouble() / project.goalAmount) * 100).toInt()

        holder.binding.txtProgressPercentage.text = progressPercentage.toString() + "%"
        holder.binding.progressBar.progress = progressPercentage

        // 클릭 시 프로젝트 상세 페이지로 이동
//        holder.itemView.setOnClickListener {
//            // DetailActivity로 갈 때 project 넘겨주면 됨
//            val intent = Intent(activity, DetailActivity::class.java)
//            startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return projectList.size
    }
}