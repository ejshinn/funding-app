package com.example.myapplication.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.activity.CategoryActivity
import com.example.myapplication.databinding.ItemCategoryBinding
import com.example.myapplication.dto.Category

class AdapterForCategory(var categoryList: List<Category>): RecyclerView.Adapter<AdapterForCategory.Holder>() {
    class Holder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val category = categoryList[position]

        holder.binding.imageView2.setImageResource(category.image!!)
        holder.binding.textViewTitle.text = category.title

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("category", category)
            context.startActivity(intent)
        }

    }
}