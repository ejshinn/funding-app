package com.example.myapplication.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.adapters.AdapterForAll
import com.example.myapplication.adapters.AdapterForBanner
import com.example.myapplication.adapters.AdapterForCategory
import com.example.myapplication.adapters.AdapterForProduct
import com.example.myapplication.adapters.AdapterForProductHoriz
import com.example.myapplication.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentHomeBinding
    private lateinit var bannerView: ViewPager2

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var imageSliderAdapter: AdapterForBanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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
        val categoryList = listOf(
            R.drawable.cate1,
            R.drawable.cate2,
            R.drawable.cate3,
            R.drawable.cate4,
            R.drawable.cate5,
            R.drawable.cate6,
            R.drawable.cate7,
            R.drawable.cate8,
            R.drawable.cate9,
            R.drawable.cate10,
            R.drawable.cate11,
            )
        categoryView.adapter = AdapterForCategory(categoryList)
        categoryView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

        val populView = binding.populRecyclerView
        val productList = listOf("")
        populView.adapter = AdapterForProduct(productList)
        populView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

        val productHorizView = binding.productRecycleViewHoriental
        val productHoriz = listOf("dd")
        val gridLayoutManager = GridLayoutManager(this.context, 3)
        gridLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        productHorizView.layoutManager = gridLayoutManager
        productHorizView.adapter = AdapterForProductHoriz(productHoriz)

        val productAllView = binding.allProductRecyclerView
        val productAllList = listOf("")
        val gridLayoutManager2 = GridLayoutManager(this.context, 2)
        productAllView.layoutManager = gridLayoutManager2
        productAllView.adapter = AdapterForAll(productAllList)

        return binding.root
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}