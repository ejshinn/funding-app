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
                0 -> tab.text = "HOME"
                1 -> tab.text = "SEARCH"
                2 -> tab.text = "FAVORITE"
                else -> tab.text = "MY"
            }
        }.attach()


    }
}