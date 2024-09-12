package com.example.myapplication.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.adapters.AdapterForCategory
import com.example.myapplication.adapters.AdapterForCategoryDetail
import com.example.myapplication.adapters.AdapterForProduct
import com.example.myapplication.adapters.AdapterForProductHoriz
import com.example.myapplication.adapters.AdapterForSearch
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.example.myapplication.search.AutoCompleteManager
import com.example.myapplication.utils.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.stream.Collectors

class SearchFragment : Fragment() {

    private lateinit var binding:FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        val gridLayoutManager = GridLayoutManager(this.context, 5, GridLayoutManager.HORIZONTAL, false)
        val linearLayoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.adapter = AdapterForSearch()

        val searchView = binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            // 검색 버튼 입력시 호출, 검색 버튼이 없으므로 사용하지 않는다.
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            // 텍스트 입력, 수정시 호출
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!!.isEmpty()){
                    return false
                }

                FunClient.retrofit.getProjectBySearchKey(newText.toString()).enqueue(object : Callback<List<ProjectDetail>>{
                    override fun onResponse(
                        call: Call<List<ProjectDetail>>,
                        response: Response<List<ProjectDetail>>
                    ) {
                        val result = response.body() as MutableList<ProjectDetail>
                        if(result.isEmpty()){
                            binding.recyclerView.adapter =AdapterForSearch()
                            binding.recyclerView.layoutManager = gridLayoutManager
                            return
                        }

                        val suggestTextList = result.toMutableList()

                        binding.recyclerView.adapter = AdapterForCategoryDetail(result)
                        binding.recyclerView.layoutManager = linearLayoutManager

//                        binding.recyclerView.layoutManager = linearLayoutManager
                    }



                    override fun onFailure(call: Call<List<ProjectDetail>>, t: Throwable) {
                    }

                })
                return false
            }
        })

        binding.searchView.setOnSearchClickListener{
            Log.d("setOnSearchClickListener", "setOnSearchClickListener")
        }


//        binding.btnSearchProjects.setOnClickListener{
//            val searchKey = autoComplete.autoCompleteTextView.text.toString()
//            if(searchKey.isBlank()){
//                Toast.makeText(this.context, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//            var userId = ""
//            val shared = this.context?.getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
//            val isLoggedIn = shared?.getString(Const.SHARED_PREF_LOGIN_KEY, "false") == "true"
//
//            FunClient.retrofit.getProjectBySearchKey(searchKey).enqueue(object: Callback<List<ProjectDetail>> {
//                override fun onResponse(
//                    call: Call<List<ProjectDetail>>,
//                    response: Response<List<ProjectDetail>>
//                ) {
//                    val categoryDetailAdapter = AdapterForCategoryDetail(response.body() as MutableList<ProjectDetail>)
//                    binding.recyclerView.adapter = categoryDetailAdapter
//
//                    if(isLoggedIn == true){
//                        var favoriteProjectIdList:MutableList<Int>
//                        userId = shared?.getString(Const.SHARED_PREF_LOGIN_ID, "").toString()
//
//                        FunClient.retrofit.getFavoriteProject(userId).enqueue(object : retrofit2.Callback<List<ProjectDetail>>{
//                            override fun onResponse(
//                                call: Call<List<ProjectDetail>>,
//                                response: Response<List<ProjectDetail>>
//                            ) {
//                                val projectList = response.body() as MutableList<ProjectDetail>
//                                favoriteProjectIdList = projectList.stream().map { it.projectId }.collect(
//                                    Collectors.toList()).toMutableList()
//                                categoryDetailAdapter.favoriteList = favoriteProjectIdList;
//                                categoryDetailAdapter.userId = userId
//                            }
//
//                            override fun onFailure(call: Call<List<ProjectDetail>>, t: Throwable) {
//                            }
//
//                        })
//                    }
//                }
//
//                override fun onFailure(call: Call<List<ProjectDetail>>, t: Throwable) {
//                }
//            })
//        }

        return binding.root
    }

}