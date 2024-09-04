package com.example.myapplication.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.favorite.FavoriteFragment
import com.example.myapplication.home.HomeFragment
import com.example.myapplication.myPage.MyFragment
import com.example.myapplication.search.SearchFragment

class AdapterForMain(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> SearchFragment()
            2 -> FavoriteFragment()
            else -> MyFragment()
        }
    }
}