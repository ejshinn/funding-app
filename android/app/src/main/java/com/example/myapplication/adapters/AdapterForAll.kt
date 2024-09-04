package com.example.myapplication.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemProductAllBinding

class AdapterForAll(var productList: List<String>): RecyclerView.Adapter<AdapterForAll.Holder>() {
    class Holder(bindng: ItemProductAllBinding): RecyclerView.ViewHolder(bindng.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val view = Holder(ItemProductAllBinding.inflate(LayoutInflater.from(parent.context), parent, false))
//        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
//        val imageWidth = screenWidth / 3
//        view.itemView.layoutParams = ViewGroup.LayoutParams(imageWidth, imageWidth)
        return view
    }

    override fun getItemCount(): Int {
//        return productList.size
        return 16
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
    }
}