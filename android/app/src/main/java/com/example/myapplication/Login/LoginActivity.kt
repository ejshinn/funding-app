package com.example.myapplication.Login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.activity.MainActivity
import com.example.myapplication.R
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.dto.Project
import com.example.myapplication.retrofitPacket.LoginCheckPacket
import com.example.myapplication.utils.Const
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnTryLogin.setOnClickListener{
            val loginCheckPacket = LoginCheckPacket(
                binding.edUserId.text.toString(),
                binding.edUserPw.text.toString()
            )

            FunClient.retrofit.tryLogin(loginCheckPacket).enqueue(object:retrofit2.Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    val shared = getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
                    val editor = shared.edit()
                    editor.putString(Const.SHARED_PREF_LOGIN_KEY, "true")
                    editor.putInt(Const.SHARED_PREF_LOGIN_ID, binding.edUserId.text.toString().toInt())
                    editor.commit()
                    Log.d("retrofit getProjectList", "-------")
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("retrofit getProjectList", "${t.message}")
                }
            })

        }

        binding.btnGoSignIn.setOnClickListener{
            startActivity(Intent(this@LoginActivity, SignInActivity::class.java))
        }

        binding.btnCancel.setOnClickListener {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
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