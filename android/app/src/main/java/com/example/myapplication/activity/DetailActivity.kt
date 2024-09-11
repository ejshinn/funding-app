package com.example.myapplication.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.adapters.AdapterForBanner
import com.example.myapplication.databinding.ActivityDetailBinding
import com.example.myapplication.retrofitPacket.FavoritePacket
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.example.myapplication.retrofitPacket.ProjectWrite
import com.example.myapplication.retrofitPacket.UserPacket
import com.example.myapplication.utils.Const
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Response


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

        val project = intent.getSerializableExtra("project") as ProjectDetail

        var detailImages = listOf(project.thumbnail)

        binding.viewPager2.adapter = AdapterForBanner(detailImages)
        binding.textViewCategory.text = project.category.title
        binding.textViewTitle.text = project.title
        binding.textViewSupport.text = project.formattedAmount()
        binding.textViewPercent.text = project.percent()
        binding.progressBar.progress = project.progress()

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.setCustomView(R.layout.custom_tabl)
        }.attach()

        // 좋아요 클릭
        binding.buttonFavorite.setOnClickListener {
            // user 정보 가져오기
            val shared = getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
            val userId = shared?.getString(Const.SHARED_PREF_LOGIN_ID, "false")
            Log.d("DetailActivity", "${userId}")

            val FavoritePacket = FavoritePacket(project.projectId, userId!!)

            // favorite db에 저장
            FunClient.retrofit.createFavorite(FavoritePacket).enqueue(object : retrofit2.Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d("DetailActivity", "${response.body()}")
                        Toast.makeText(this@DetailActivity, "좋아요를 눌렀습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("DetailActivity", "좋아요 실패")
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        finish()
        return true
    }

}