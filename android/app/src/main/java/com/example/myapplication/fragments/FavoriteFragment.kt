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
import com.example.myapplication.adapters.FavoriteAdapter
import com.example.myapplication.databinding.FragmentFavoriteBinding
import com.example.myapplication.databinding.FragmentMyBinding
import com.example.myapplication.dto.Project
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

        // 로그인 상태
        if(isLoggedIn) {
            val recyclerView = binding.recyclerView
            val emptyLayout = binding.frameLayout
            Log.d("FavoriteFragment", "User ID: $userId")

            FunClient.retrofit.getFavoriteProject(userId!!).enqueue(object : retrofit2.Callback<List<Project>> {
                override fun onResponse(call: Call<List<Project>>, response: Response<List<Project>>) {
                    if (response.body().isNullOrEmpty()) {
                        // 프로젝트 목록이 비어있으면 빈 레이아웃을 보여줌
                        recyclerView.visibility = View.GONE
                        emptyLayout.visibility = View.VISIBLE
                        Log.d("FavoriteFragment", "관심 목록 비어있음")
                    } else {
                        // 프로젝트 목록이 있으면 리사이클러뷰를 보여줌
                        recyclerView.visibility = View.VISIBLE
                        emptyLayout.visibility = View.GONE

                        val favoriteProjects = response.body() as MutableList<Project>

                        // 리사이클러뷰에 데이터 바인딩 (어댑터 설정)
                        recyclerView.adapter = FavoriteAdapter(favoriteProjects)
                        binding.recyclerView.layoutManager = LinearLayoutManager(context)
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<List<Project>>, t: Throwable) {
                }

            })
        } else{
            // 로그인 상태가 아니라면 LoginActivity 열기
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish() // 현재 Fragment가 포함된 Activity를 종료하여 로그인 후 다시 돌아오지 않도록 함
        }

        return inflater.inflate(R.layout.fragment_favorite, container, false)
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