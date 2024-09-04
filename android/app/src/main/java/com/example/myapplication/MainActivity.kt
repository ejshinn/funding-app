package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.adapters.AdapterForMain
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var adapterForMain: AdapterForMain
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterForMain = AdapterForMain(this)
        binding.viewPager.adapter = adapterForMain

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.setIcon(R.drawable.tab_home)
                1 -> tab.setIcon(R.drawable.tab_search)
                2 -> tab.setIcon(R.drawable.tab_favorite)
                else -> tab.setIcon(R.drawable.tab_my)
            }
        }.attach()


    }
}