package com.example.myapplication

import android.app.DatePickerDialog
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
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
                // 아무것도 선택되지 않은 경우 처리
            }
        }

        //////////////////////이미지 지피티 버전
        // 이미지 선택 Launcher 초기화
//        val requestGalleryLauncher = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult()) { result ->
//                if (result.resultCode == RESULT_OK) {
//                    val data: Intent? = result.data
//                    val imageUri: Uri? = data?.data
//                    if (imageUri != null) {
//                        // 선택된 이미지를 ImageView에 설정
//                        binding.imageView1.setImageURI(imageUri)
//                        Log.d("imageUri", "imageUri: $imageUri")
//                    }
//                    else {
//                        Log.e("ImagePicker", "Image URI is null")
//                    }
//                }
//            }

        // 책 버전(일단 되긴 됨-절대 경로는 아님)
//        val requestGalleryLauncher = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult())
//        {
//            try {
//                // inSampleSize 비율 계산, 지정
////                val calRatio = calculateInSampleSize(
////                    it!!.data!!.data!!,
////                    resources.getDimensionPixelSize(R.dimen.imgSize),
////                    resources.getDimensionPixelSize(R.dimen.imgSize)
////                )
//
//                /// 되는 거
////                val option = BitmapFactory.Options()
////                //option.inSampleSize=calRatio
////                // 이미지 로딩
////                var inputStream = contentResolver.openInputStream(it!!.data!!.data!!)
////                val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
////                inputStream!!.close()
////                inputStream = null
////                bitmap?. let {
////                    binding.imageView1.setImageBitmap(bitmap)
////                    Log.d("kkang", "${bitmap}")
////                } ?: let {
////                    Log.d("kkang", "bitmap null")
////                }
//
//                // 이미지 URI 가져오기
//                val imageUri: Uri? = it?.data?.data
//
//                // 이미지 URI가 null이 아닐 경우
//                imageUri?.let { uri ->
//                    val inputStream = contentResolver.openInputStream(uri)
//                    val bitmap = BitmapFactory.decodeStream(inputStream)
//                    inputStream?.close()
//                    binding.imageView1.setImageBitmap(bitmap)
//                    Log.d("kkang", "Bitmap: $bitmap")
//                    Log.d("kkang", "inputStream: $inputStream")
//                    println(bitmap::class.simpleName)
//                    println(inputStream!!::class.simpleName)
//
//                } ?: run {
//                    Log.d("kkang", "Bitmap is null")
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }

//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE)
//        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }

        // 이미지 선택
        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageUri: Uri? = result.data?.data

                imageUri?.let { uri ->
                    // 절대 경로를 가져옴
                    val filePath = getRealPathFromURI(uri)

                    filePath?.let { path ->
                        Log.d("ImageLoad", "File path: $path")

                        binding.imageView1.setImageURI(Uri.parse(path))
                        Log.d("Uri.parse(path)", "${Uri.parse(path)}")

                        // Glide로 절대 경로에서 이미지 로드
                        Glide.with(this)
                            .load(path)
                            .into(binding.imageView1)
                    } ?: run {
                        Log.d("ImageLoad", "File path is null")
                    }
                } ?: run {
                    Log.d("ImageLoad", "Image URI is null")
                }
            }
        }

        // 이미지 선택 함수
        fun pickImage() {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }

        // 프로젝트 내용 타입(이미지/글) 선택
        binding.spinnerContents.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()

                when (selectedItem) {
                    "이미지" -> {
                        // "이미지"가 선택되면 LinearLayout을 보이게 하고 EditText는 숨깁니다.
                        binding.imageContents.visibility = View.VISIBLE
                        binding.edtContents.visibility = View.GONE

                        // 이미지 선택 기능 추가(지피티 버전)
                        binding.imageView1.setOnClickListener {
                            pickImage()  // 이미지 선택
                        }
                    }
                    "글" -> {
                        // "글"이 선택되면 EditText를 보이게 하고 LinearLayout은 숨깁니다.
                        binding.imageContents.visibility = View.GONE
                        binding.edtContents.visibility = View.VISIBLE

                        // 텍스트 가져오기
                        contentText = binding.edtContents.getText().toString()
                    }
                    else -> {
                        // 그 외의 경우에는 둘 다 숨깁니다.
                        binding.imageContents.visibility = View.GONE
                        binding.edtContents.visibility = View.GONE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 아무것도 선택되지 않았을 때 처리 (필요 시)
            }
        }

        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        // 펀딩 일정(시작일) 버튼 클릭
        binding.btnStartDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                binding.tvStartDate.text = year.toString() + "/" + (month + 1).toString() + "/" + day.toString()
            }, year, month, day)
            datePickerDialog.show()
        }

        // 펀딩 일정(종료일) 버튼 클릭
        binding.btnEndDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                binding.tvEndDate.text = year.toString() + "/" + (month + 1).toString() + "/" + day.toString()
            }, year, month, day)
            datePickerDialog.show()
        }

        // 작성 완료 버튼 클릭
        binding.btnSubmit.setOnClickListener {
            // 프로젝트 제목 가져오기
            title = binding.edtTitle.text.toString()
            Log.d("title", "title: $title")

            // 프로젝트 내용

            // 펀딩 일정(시작일)
            startDate = binding.tvStartDate.getText().toString()
            Log.d("startDate", "startDate: $startDate")

            // 펀딩 일정(종료일)
            endDate = binding.tvEndDate.getText().toString()
            Log.d("endDate", "endDate: $endDate")

            // 목표 금액 가져오기
            goalAmount = binding.edtGoalAmount.text.toString()
            Log.d("goalAmount", "goalAmount: $goalAmount")

            // 후원 금액(개당 금액) 가져오기
            perPrice = binding.edtPerPrice.text.toString()
            Log.d("perPrice", "perPrice: $perPrice")

            Toast.makeText(this, "작성 완료", Toast.LENGTH_SHORT).show()
        }
    }

    // 이미지 경로 가져오기
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

    // 절대경로->uri
//    fun getUriFromPath(filePath: String): Uri {
//        val cursor = contentResolver.query(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            null, "_data = '$filePath'", null, null
//        )
//
//        cursor!!.moveToNext()
//        val id = cursor!!.getInt(cursor!!.getColumnIndex("_id"))
//        val uri =
//            ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toLong())
//
//        return uri
//    }
}