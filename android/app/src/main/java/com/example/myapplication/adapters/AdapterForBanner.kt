package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemBannerBinding

class AdapterForBanner(val imageList: List<Int>): RecyclerView.Adapter<AdapterForBanner.Holder>() {
    class Holder(val binding: ItemBannerBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val image = imageList[position]
        holder.binding.imageView.setImageResource(image)
    }


}