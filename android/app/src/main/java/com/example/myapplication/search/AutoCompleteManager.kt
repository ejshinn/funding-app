package com.example.myapplication.search

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.app_autocompletetextview.AutoCompleteClient
import com.example.myapplication.Retrofit.FunClient
import retrofit2.Call
import retrofit2.Response


/*
        사용법
        val autoComplete = AutoCompleteManager(binding.autoCompleteTextView, this)
        autoComplete.setAutoCompleteAdapter()
 */

class AutoCompleteManager(var autoCompleteTextView: AutoCompleteTextView, val context: Context?) :TextWatcher{

    lateinit var suggestTextList:Array<String>
    lateinit var adapter:ArrayAdapter<String>


    var keyValue:Char? = null

    // 서버 연결 전 테스용 더미 데이터
    val suggestText_S = arrayOf("SM3", "SM5", "SM7", "SONATA", "SOUL")
    val suggestText_K = arrayOf("K5", "K7")


    fun setAutoCompleteAdapter(){
        suggestTextList = suggestText_S
        adapter = ArrayAdapter(context!!, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, suggestTextList)

        autoCompleteTextView.threshold = 1
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.addTextChangedListener(this)
    }


    // TextWatcher Event Listener
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if(p0?.length == 1 && keyValue != p0[0]){

            keyValue = p0[0]

            //테스트 용 코드
            if(keyValue == 'k'){

                Log.d("onTextChanged", "key is k")
                adapter = ArrayAdapter(context!!, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, suggestText_K)

                autoCompleteTextView.setAdapter(adapter)
            }
            else  if(keyValue == 's'){

                Log.d("onTextChanged", "key is s")
                adapter = ArrayAdapter(context!!, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, suggestText_S)

                autoCompleteTextView.setAdapter(adapter)
            }
            else{
                getNewSuggestList(keyValue.toString())
            }
        }

        Log.d("onTextChanged", "${p0} length : ${p0?.length}")
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    // 서버로 부터 key 값을 이용한 추천 String List 를 받아와 새로 적용
    private fun getNewSuggestList(key:String) {
        FunClient.retrofit.getProjectSearch(key)
            .enqueue(object : retrofit2.Callback<List<String>> {
                override fun onResponse(
                    call: Call<List<String>>,
                    response: Response<List<String>>
                ) {
                    val result = response.body() as List<String>
                    suggestTextList = result.toTypedArray()
                    adapter = ArrayAdapter(context!!, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, suggestTextList)
                    autoCompleteTextView.setAdapter(adapter)
                }

                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                }
            })
    }
}