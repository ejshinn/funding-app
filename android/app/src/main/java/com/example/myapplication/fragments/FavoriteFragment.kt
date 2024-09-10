package com.example.myapplication.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.myapplication.Login.LoginActivity
import com.example.myapplication.R
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.adapters.AdapterForMy
import com.example.myapplication.adapters.FavoriteAdapter
import com.example.myapplication.databinding.FragmentFavoriteBinding
import com.example.myapplication.databinding.FragmentMyBinding
import com.example.myapplication.dto.Project
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.example.myapplication.utils.Const
import retrofit2.Call
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val binding = FragmentFavoriteBinding.inflate(layoutInflater)

        val shared = activity?.getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
        val isLoggedIn = shared?.getString(Const.SHARED_PREF_LOGIN_KEY, "false") == "true"
        val userId = shared?.getString(Const.SHARED_PREF_LOGIN_ID, "false")

        // 로그인 상태 확인
        if(isLoggedIn) {
            binding.buttonLogin.visibility = View.GONE

            FunClient.retrofit.getFavoriteProject(userId!!).enqueue(object : retrofit2.Callback<List<ProjectDetail>> {
                override fun onResponse(call: Call<List<ProjectDetail>>, response: Response<List<ProjectDetail>>) {
                    if (response.body().isNullOrEmpty()) {
                        // 프로젝트 목록이 비어있으면 빈 레이아웃 보여줌
                        binding.recyclerView.visibility = View.GONE
                        binding.frameLayout.visibility = View.VISIBLE
                    } else {
                        Log.d("FavoriteFragment", "${response.body()}")


                        // 프로젝트 목록이 있으면 리사이클러뷰 보여줌
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.frameLayout.visibility = View.GONE
                        val favoriteProjects = response.body() as MutableList<ProjectDetail>

                        Log.d("FavoriteFragment", "어댑터 설정")
                        val adapter = FavoriteAdapter(favoriteProjects)
                        binding.recyclerView.adapter = adapter
                        binding.recyclerView.layoutManager = LinearLayoutManager(context)
                        adapter.notifyDataSetChanged()

                        // 어댑터 설정
//                        Log.d("FavoriteFragment", "어댑터 설정")
//                        binding.recyclerView.adapter = FavoriteAdapter(favoriteProjects)
//                        binding.recyclerView.layoutManager = LinearLayoutManager(context)
                    }
                }

                override fun onFailure(call: Call<List<ProjectDetail>>, t: Throwable) {
                    Log.e("FavoriteFragment", "Failed to fetch favorite projects", t)
                    binding.recyclerView.visibility = View.GONE
                    binding.frameLayout.visibility = View.VISIBLE
                }
            })
        } else {
            // 로그인 안 한 상태면 로그인 버튼만 화면에 띄우기
            binding.buttonLogin.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            binding.frameLayout.visibility = View.GONE

            // 로그인 버튼 클릭 시 로그인 화면으로 이동
            binding.buttonLogin.setOnClickListener {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
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
         * @return A new instance of fragment FavoriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}