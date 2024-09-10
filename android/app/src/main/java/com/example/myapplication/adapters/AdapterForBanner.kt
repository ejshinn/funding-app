package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemBannerBinding
import com.squareup.picasso.Picasso

class AdapterForBanner(var imageList: List<String>): RecyclerView.Adapter<AdapterForBanner.Holder>() {
    class Holder(val binding: ItemBannerBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val image = imageList[position]

        Picasso.get()
            .load(image)
            .into(holder.binding.imageView)
    }


}