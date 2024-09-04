package com.example.myapplication.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FunClient {
    val retrofit:FunInterface = Retrofit.Builder()
        //TODO: ip 주소 변경 필요
        .baseUrl("http://ip주소:8877")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FunInterface::class.java)
}