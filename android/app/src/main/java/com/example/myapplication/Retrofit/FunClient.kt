package com.example.myapplication.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FunClient {
    val retrofit:FunInterface = Retrofit.Builder()
        //TODO: ip 주소 변경 필요
        .baseUrl("http://10.100.105.244:8877")
        .addConverterFactory(GsonConverterFactory.create())
//        .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
        .build()
        .create(FunInterface::class.java)
}