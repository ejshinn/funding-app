package com.example.myapplication.Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.databinding.ActivitySignInBinding
import com.example.myapplication.dto.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSignIn.setOnClickListener{
            val userId = binding.edUserId.text.toString()
            val userPw = binding.edUserPw.text.toString()
            val userEmail = binding.edUserEmail.text.toString()

            if(userId.isEmpty()){
                displayWarningDialog("아이디를 입력해주세요")
                return@setOnClickListener
            }

            if(userPw.isEmpty()){
                displayWarningDialog("비밀번호를 입력해주세요")
                return@setOnClickListener
            }

            if(userEmail.isEmpty()){
                displayWarningDialog("이메일을 입력해주세요")
                return@setOnClickListener
            }

            val user = User(
                0,
                userId,
                userPw,
                userEmail
            )

            FunClient.retrofit.signIn(user).enqueue(object : Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    val isSuccess = response.body() as Boolean
                    if(isSuccess){
                        Log.d("retrofit try sign in", "successful")
                        startActivity(Intent(this@SignInActivity, LoginActivity::class.java))
                        finish()
                        return
                    }
                    else{
                        displayWarningDialog("아이디가 이미 존재합니다.")
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("retrofit try sign in", "onFailure : ${t.message}")
                }

            })
        }

        binding.btnCancel.setOnClickListener{
            startActivity(Intent(this@SignInActivity, LoginActivity::class.java))
        }
    }


    fun displayWarningDialog(msg:String){
        AlertDialog.Builder(this).run{
            setMessage(msg)
            setPositiveButton("확인", null)
            show()
        }
    }
}