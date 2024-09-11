package com.example.myapplication.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.Login.LoginActivity
import com.example.myapplication.R
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.adapters.AdapterForBanner
import com.example.myapplication.databinding.ActivityDetailBinding
import com.example.myapplication.databinding.DialogCardInfoBinding
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.example.myapplication.retrofitPacket.SupportPacket
import com.example.myapplication.utils.Const
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity : AppCompatActivity() {

    lateinit var binding:ActivityDetailBinding
    lateinit var project:ProjectDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
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

        project = intent.getSerializableExtra("project") as ProjectDetail

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

        var userId = ""
        val shared = getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
        val isLoggedIn = shared?.getString(Const.SHARED_PREF_LOGIN_KEY, "false") == "true"
        if(isLoggedIn == true){
            userId = shared?.getString(Const.SHARED_PREF_LOGIN_ID, "").toString()

        }



        // 후원 버튼
        binding.btnSupport.isEnabled = false
        binding.btnSupport.setOnClickListener{
            if(isLoggedIn == false){
                displayRequiredLoginDialog()
                return@setOnClickListener
            }

            val activityContext = this

            val dialogView = layoutInflater.inflate(R.layout.dialog_card_info, null)
            val cardEd1 = dialogView.findViewById<EditText>(R.id.ed_card_1)
            val cardEd2 = dialogView.findViewById<EditText>(R.id.ed_card_2)
            val cardEd3 = dialogView.findViewById<EditText>(R.id.ed_card_3)
            val cardEd4 = dialogView.findViewById<EditText>(R.id.ed_card_4)

            val cardDate = dialogView.findViewById<EditText>(R.id.ed_card_end_date)
            val cardCvc = dialogView.findViewById<EditText>(R.id.ed_cvc)

            val cardOk = dialogView.findViewById<Button>(R.id.btn_card_dialog_ok)
            val cardCancel = dialogView.findViewById<Button>(R.id.btn_card_dialog_cancel)

            // 카드 입력 Dialog
            val cardInfoDialog = AlertDialog.Builder(this).run{
                setTitle("후원하기")
                setMessage("카드정보를 입력해주세요")
                setView(dialogView)
                show()
            }

            cardOk.setOnClickListener{
                if(cardEd1.text.toString().isEmpty() ||
                    cardEd2.text.toString().isEmpty() ||
                    cardEd3.text.toString().isEmpty() ||
                    cardEd4.text.toString().isEmpty() ||
                    cardDate.text.toString().isEmpty()||
                    cardCvc.text.toString().isEmpty()){
                    Toast.makeText(activityContext, "카드 정보를 전부 입력해주세요", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                // 후원
                FunClient.retrofit.createSupport(SupportPacket(project.projectId, userId)).enqueue(object : Callback<Void>{
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Log.d("Support", "test")
                        cardInfoDialog.cancel()

                        project.currentAmount += project.perPrice

                        binding.textViewSupport.text = project.formattedAmount()
                        binding.textViewPercent.text = project.percent()
                        binding.progressBar.progress = project.progress()

                        binding.btnSupport.text = "이미 후원한 프로젝트입니다"
                        binding.btnSupport.isEnabled = false

                        Toast.makeText(activityContext, "후원이 완료되었습니다", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        cardInfoDialog.cancel()
                    }
                })
            }

            cardCancel.setOnClickListener{
                cardInfoDialog.cancel()
            }

        }


        // 후원 여부 확인
        FunClient.retrofit.checkSupporting(SupportPacket(project.projectId, userId)).enqueue(object : retrofit2.Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                val isSupporting = response.body() as Boolean
                if(isSupporting){
                    binding.btnSupport.text = "이미 후원한 프로젝트입니다"
                }
                else{
                    binding.btnSupport.isEnabled = true
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
            }

        })
    }

    // 클립보드 URL 복붙
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus){
            binding.btnSharing.setOnClickListener{
                val activityContext = this
                val projectUrl = "${Const.SERVER_BASE_URL + "/project/" + project.projectId}"
                AlertDialog.Builder(this).run {
                    setTitle("공유 하기")
                    setMessage("url \n${projectUrl}")
                    setNeutralButton("URL 복사", object : DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("projectUrl", projectUrl)
                            clipboardManager.setPrimaryClip(clip)

                            Toast.makeText(activityContext, "url 복사 완료", Toast.LENGTH_SHORT).show()
                        }

                    })
                    setNegativeButton("취소", null)
                    show()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        finish()
        return true
    }

    fun displayRequiredLoginDialog(){
        AlertDialog.Builder(this).run{
            setTitle("로그인이 필요합니다.")
            setMessage("로그인 하시겠습니까?")
            setNegativeButton("취소", null)
            setPositiveButton("확인", object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    startActivity(Intent(this@DetailActivity, LoginActivity::class.java))
                }
            })
            show()
        }
    }
}