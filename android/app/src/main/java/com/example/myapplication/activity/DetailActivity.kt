package com.example.myapplication.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.adapters.AdapterForBanner
import com.example.myapplication.databinding.ActivityDetailBinding
import com.example.myapplication.dto.Project
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(com.example.myapplication.R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        // 뒤로 가기 버튼 활성화
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 툴바 타이틀 설정 (필요한 문자열로 변경)
        supportActionBar?.title = "상세 페이지"

        val project = intent.getSerializableExtra("project") as Project

        val detailImages = listOf(R.drawable.home1, R.drawable.home2, R.drawable.home3)
        binding.viewPager2.adapter = AdapterForBanner(detailImages)



        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.setCustomView(R.layout.custom_tabl)
        }.attach()

    }
}