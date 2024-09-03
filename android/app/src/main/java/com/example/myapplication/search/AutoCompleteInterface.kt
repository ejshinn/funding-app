package com.example.app_autocompletetextview

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AutoCompleteInterface {

    @GET("/suggestString/{key}")
    fun getSuggestList(@Path("key") key:String) : Call<List<String>>
}