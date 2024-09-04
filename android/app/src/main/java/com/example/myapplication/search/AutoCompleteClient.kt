package com.example.app_autocompletetextview

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AutoCompleteClient {
    val retrofit:AutoCompleteInterface = Retrofit.Builder()
        .baseUrl("")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AutoCompleteInterface::class.java)

}