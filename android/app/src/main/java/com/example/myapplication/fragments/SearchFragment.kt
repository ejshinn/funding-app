package com.example.myapplication.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.adapters.AdapterForCategory
import com.example.myapplication.adapters.AdapterForCategoryDetail
import com.example.myapplication.adapters.AdapterForSearch
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.example.myapplication.search.AutoCompleteManager
import com.example.myapplication.utils.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.stream.Collectors

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding:FragmentSearchBinding

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
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
//        val searchView = binding.searchView
////        searchView.isIconified = false // 검색창 기본 확장 상태
//        searchView.setIconifiedByDefault(false) // 돋보기 아이콘을 클릭하지 않아도 확장되도록 설정
////        searchView.isFocusable = true
////        searchView.isFocusableInTouchMode = true
//        searchView.requestFocus()
//        searchView.queryHint = "검색어를 입력하세요"
//        searchView.findViewById<View>(androidx.appcompat.R.id.search_plate).setBackgroundColor(Color.TRANSPARENT)



        val gridLayoutManager = GridLayoutManager(this.context, 5, GridLayoutManager.HORIZONTAL, false)
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.adapter = AdapterForSearch()


        val autoComplete = AutoCompleteManager(binding.autoCompleteTextView, requireContext())
        autoComplete.setAutoCompleteAdapter()


        binding.btnSearchProjects.setOnClickListener{
            val searchKey = autoComplete.autoCompleteTextView.text.toString()
            if(searchKey.isBlank()){
                Toast.makeText(this.context, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var userId = ""
            val shared = this.context?.getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
            val isLoggedIn = shared?.getString(Const.SHARED_PREF_LOGIN_KEY, "false") == "true"

            FunClient.retrofit.getProjectBySearchKey(searchKey).enqueue(object: Callback<List<ProjectDetail>> {
                override fun onResponse(
                    call: Call<List<ProjectDetail>>,
                    response: Response<List<ProjectDetail>>
                ) {
                    val categoryDetailAdapter = AdapterForCategoryDetail(response.body() as MutableList<ProjectDetail>)
                    binding.recyclerView.adapter = categoryDetailAdapter

                    if(isLoggedIn == true){
                        var favoriteProjectIdList:MutableList<Int>
                        userId = shared?.getString(Const.SHARED_PREF_LOGIN_ID, "").toString()

                        FunClient.retrofit.getFavoriteProject(userId).enqueue(object : retrofit2.Callback<List<ProjectDetail>>{
                            override fun onResponse(
                                call: Call<List<ProjectDetail>>,
                                response: Response<List<ProjectDetail>>
                            ) {
                                val projectList = response.body() as MutableList<ProjectDetail>
                                favoriteProjectIdList = projectList.stream().map { it.projectId }.collect(
                                    Collectors.toList()).toMutableList()
                                categoryDetailAdapter.favoriteList = favoriteProjectIdList;
                                categoryDetailAdapter.userId = userId
                            }

                            override fun onFailure(call: Call<List<ProjectDetail>>, t: Throwable) {
                            }

                        })
                    }
                }

                override fun onFailure(call: Call<List<ProjectDetail>>, t: Throwable) {
                }
            })


        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}