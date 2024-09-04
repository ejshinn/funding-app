package com.example.myapplication.Retrofit

import com.example.myapplication.dto.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FunInterface {
    @GET("/LogIn")
    fun tryLogin(@Body userId:String, @Body userPw:String) : Call<User>

    @POST("/SignIn")
    fun signIn(@Body user:User) : Call<Boolean>
}