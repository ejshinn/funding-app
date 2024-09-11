package com.example.myapplication.search

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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

    private var keyValue = ""


    fun setAutoCompleteAdapter(){
        suggestTextList = arrayOf()
        adapter = ArrayAdapter(context!!, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, suggestTextList)

        autoCompleteTextView.threshold = 1
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.addTextChangedListener(this)
    }


    // TextWatcher Event Listener
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        if((p0?.length == 1 || p0?.length == 2) && keyValue != p0.toString()) {

            keyValue = p0.toString()

            try {
                setNewSuggestList(keyValue)
            } catch (e: Exception) {
                Log.d("Search Error", "${e.message}")
            }

        }

        Log.d("onTextChanged", "${p0} length : ${p0?.length}")
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    // 서버로 부터 key 값을 이용한 추천 String List 를 받아와 새로 적용
    private fun setNewSuggestList(key:String) {
        FunClient.retrofit.getSearchSuggestList(key)
            .enqueue(object : retrofit2.Callback<List<String>> {
                override fun onResponse(
                    call: Call<List<String>>,
                    response: Response<List<String>>
                ) {
                    val result = response.body() as List<String>
                    if(result.isEmpty()){
                        return
                    }

                    suggestTextList = result.toTypedArray()
                    adapter = ArrayAdapter(context!!, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, suggestTextList)
                    autoCompleteTextView.setAdapter(adapter)
                }

                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                }
            })
    }
}