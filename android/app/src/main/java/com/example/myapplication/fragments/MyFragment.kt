package com.example.myapplication.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Login.LoginActivity
import com.example.myapplication.R
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.adapters.AdapterForMy
import com.example.myapplication.databinding.FragmentMyBinding
import com.example.myapplication.retrofitPacket.UserPacket
import com.example.myapplication.utils.Const
import retrofit2.Call
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyFragment : Fragment() {
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

        val binding = FragmentMyBinding.inflate(layoutInflater)

        binding.recyclerView2.adapter = AdapterForMy()
        binding.recyclerView2.layoutManager = LinearLayoutManager(this.context)


        val shared = activity?.getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
        val isLoggedIn = shared?.getString(Const.SHARED_PREF_LOGIN_KEY, "false") == "true"
        val userId = shared?.getString(Const.SHARED_PREF_LOGIN_ID, "false")

        if (isLoggedIn) {
            // 로그인 상태면 constraintLayout 보여주고 로그인 버튼은 숨김
            binding.constraintLayout.visibility = View.VISIBLE
            binding.recyclerView2.visibility = View.VISIBLE
            binding.buttonLogin.visibility = View.GONE

            // user 정보 가져오기
            FunClient.retrofit.getUser(userId!!).enqueue(object: retrofit2.Callback<UserPacket>{
                override fun onResponse(call: Call<UserPacket>, response: Response<UserPacket>) {
                    binding.textViewId.setText(response.body()!!.name)
                }

                override fun onFailure(call: Call<UserPacket>, t: Throwable) {

                }
            })

            binding.imageViewProfile.setImageResource(R.drawable.profile_temp)
        } else {
            // 로그인 상태가 아니라면 로그인 버튼만 보임
            binding.constraintLayout.visibility = View.GONE
            binding.recyclerView2.visibility = View.GONE
            binding.buttonLogin.visibility = View.VISIBLE

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
         * @return A new instance of fragment MyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}