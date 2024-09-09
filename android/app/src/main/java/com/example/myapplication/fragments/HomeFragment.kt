package com.example.myapplication.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.adapters.AdapterForAll
import com.example.myapplication.adapters.AdapterForBanner
import com.example.myapplication.adapters.AdapterForCategory
import com.example.myapplication.adapters.AdapterForProduct
import com.example.myapplication.adapters.AdapterForProductHoriz
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.dto.Category
import com.example.myapplication.dto.CategoryManager
import com.example.myapplication.dto.Project
import com.example.myapplication.retrofitPacket.HomeInitPacket
import com.example.myapplication.retrofitPacket.ProjectDetail
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var bannerView: ViewPager2
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var imageSliderAdapter: AdapterForBanner

    private lateinit var populAdapter: AdapterForProduct
    private lateinit var deadlineAdapter: AdapterForProductHoriz
    private lateinit var allAdapter: AdapterForAll

    var currentPage = 0
    val productAllList = mutableListOf<ProjectDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FunClient.retrofit.getHomeInitData().enqueue(object :retrofit2.Callback<HomeInitPacket>{
            override fun onResponse(
                call: Call<HomeInitPacket>,
                response: Response<HomeInitPacket>
            ) {
                response.body()?.let { data ->

                    val bannerDataList: List<String> = data.bannerUrl
//                    imageSliderAdapter.imageList = bannerDataList
                    imageSliderAdapter.notifyDataSetChanged()

                    val popularProjectList: List<ProjectDetail> = data.popularProjects
                    populAdapter.projectList = popularProjectList
                    Log.d("popularProjectList", "${popularProjectList}")
                    populAdapter.notifyDataSetChanged()

                    val deadlineProjectList:List<ProjectDetail> = data.deadlineProjects
                    deadlineAdapter.projectList = deadlineProjectList
                    Log.d("deadlineProjectList", "${deadlineProjectList}")
                    deadlineAdapter.notifyDataSetChanged()

                    val scrollProjectList :MutableList<ProjectDetail> = data.scrollProjects.toMutableList()
                    allAdapter.projectList = scrollProjectList
                    Log.d("scrollProjectList", "${scrollProjectList}")
                    allAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<HomeInitPacket>, t: Throwable) {
                Log.e("API_ERROR", "Failed to fetch data: ${t.message}", t)
            }

        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        bannerView = binding.bannerView
        val imageList = listOf(R.drawable.home1, R.drawable.home2, R.drawable.home3)
        imageSliderAdapter = AdapterForBanner(imageList)
        bannerView.adapter = imageSliderAdapter
        setupAutoSlide()

        val categoryView = binding.categoryRecyclerView



        categoryView.adapter = AdapterForCategory(CategoryManager.categoryList)
        categoryView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

        // 인기순
        val populView = binding.populRecyclerView
        val productList = listOf<ProjectDetail>()
        populAdapter = AdapterForProduct(productList)
        populView.adapter = populAdapter
        populView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

        // 마감순
        val productHorizView = binding.productRecycleViewHoriental
        val deadlineList = listOf<ProjectDetail>()
        val gridLayoutManager = GridLayoutManager(this.context, 3)
        gridLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        productHorizView.layoutManager = gridLayoutManager
        deadlineAdapter = AdapterForProductHoriz(deadlineList)
        productHorizView.adapter = deadlineAdapter

        // 전체
        val productAllView = binding.allProductRecyclerView
        val gridLayoutManager2 = GridLayoutManager(this.context, 2)
        productAllView.layoutManager = gridLayoutManager2
        allAdapter = AdapterForAll(productAllList)
        productAllView.adapter = allAdapter

        productAllView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = gridLayoutManager2.itemCount
                val lastVisibleItemPosition = gridLayoutManager2.findLastVisibleItemPosition()

                if (lastVisibleItemPosition == totalItemCount - 1) {
                    currentPage++
                    loadMoreData(currentPage)
                }
            }
        })

        return binding.root
    }

    fun loadMoreData(page: Int) {

        FunClient.retrofit.getScrollProject(page).enqueue(object :retrofit2.Callback<List<ProjectDetail>>{
            override fun onResponse(
                call: Call<List<ProjectDetail>>,
                response: Response<List<ProjectDetail>>
            ) {
                response.body()?.let { newData ->
                    allAdapter.projectList.addAll(newData)
                    allAdapter.notifyDataSetChanged()
                    Log.d("resultData", "${newData}")
                }
            }

            override fun onFailure(call: Call<List<ProjectDetail>>, t: Throwable) {
                Log.e("RetrofitError", "Failed to load more data: ${t.message}")
            }
        })
    }

    private fun setupAutoSlide() {
        val runnable = object : Runnable {
            override fun run() {
                val nextItem = (bannerView.currentItem + 1) % imageSliderAdapter.itemCount
                bannerView.setCurrentItem(nextItem, true)
                handler.postDelayed(this, 3000)
            }
        }

        // 자동 슬라이드 시작
        handler.postDelayed(runnable, 3000)
    }



}