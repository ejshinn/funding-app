package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityWriteBinding


class WriteActivity : AppCompatActivity() {
    lateinit var binding:ActivityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // setContentView(R.layout.activity_write)

        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lateinit var category: String // 카테고리
        lateinit var title: String // 타이틀
        lateinit var imagePath: String // 이미지 절대 경로
        lateinit var contentText: String // 내용
        lateinit var goalAmount: String // 목표 금액
        lateinit var perPrice: String // 후원 금액(개당 금액)
        lateinit var startDate: String // 펀딩 일정(시작일)
        lateinit var endDate: String // 펀딩 일정(종료일)

        // 프로젝트 카테고리 선택
        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // 선택된 항목의 값 가져오기
                category = binding.spinnerCategory.selectedItem.toString()
                // 선택된 항목을 Log로 출력
                Log.d("category", "category: $category")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }

        // 현재 선택된 ImageView를 저장할 변수
        var selectedImageView: ImageView? = null

        // 이미지 선택
        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageUri: Uri? = result.data?.data

                imageUri?.let { uri ->
                    // 절대 경로 가져옴
                    imagePath = getRealPathFromURI(uri).toString()

                    // 선택된 ImageView에 이미지 설정
                    selectedImageView?.setImageURI(uri)

                } ?: run {
                    Log.d("ImageLoad", "Image URI is null")
                }
            }
        }

        fun pickImage() {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }

        // imageView1 클릭
        binding.imageView1.setOnClickListener {
            selectedImageView = binding.imageView1
            pickImage()
        }

        // imageView2 클릭
        binding.imageView2.setOnClickListener {
            selectedImageView = binding.imageView2
            pickImage()
        }

        // imageView3 클릭
        binding.imageView3.setOnClickListener {
            selectedImageView = binding.imageView3
            pickImage()
        }

        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        // 펀딩 일정(시작일) 버튼 클릭
        binding.tvStartDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                // "YYYY-MM-DD" 형식으로 날짜 설정
                binding.tvStartDate.text = String.format("%04d-%02d-%02d", year, month + 1, day)
            }, year, month, day)
            datePickerDialog.show()
        }

        // 펀딩 일정(종료일) 버튼 클릭
        binding.tvEndDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                // "YYYY-MM-DD" 형식으로 날짜 설정
                binding.tvEndDate.text = String.format("%04d-%02d-%02d", year, month + 1, day)
            }, year, month, day)
            datePickerDialog.show()
        }

        // 작성 완료 버튼 클릭
        binding.btnSubmit.setOnClickListener {
            // 카테고리 값 확인
            Log.d("button click", "category: $category")

            // 프로젝트 제목
            title = binding.edtTitle.text.toString()
            Log.d("button click", "title: $title")

            // 이미지 절대 경로
            //Log.d("button click", "image path: $imagePath")

            // 프로젝트 내용(텍스트)
            contentText = binding.edtContents.getText().toString()
            Log.d("button click", "contentText: $contentText")

            // 펀딩 일정(시작일)
            startDate = binding.tvStartDate.getText().toString()
            Log.d("button click", "startDate: $startDate")

            // 펀딩 일정(종료일)
            endDate = binding.tvEndDate.getText().toString()
            Log.d("button click", "endDate: $endDate")

            // 목표 금액
            goalAmount = binding.edtGoalAmount.text.toString()
            Log.d("button click", "goalAmount: $goalAmount")

            // 후원 금액(개당 금액)
            perPrice = binding.edtPerPrice.text.toString()
            Log.d("button click", "perPrice: $perPrice")

            Toast.makeText(this, "작성 완료", Toast.LENGTH_SHORT).show()
        }
    }

    // 이미지 절대 경로
    fun getRealPathFromURI(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return cursor.getString(columnIndex)
            }
        }
        return null
    }
}