package com.example.myapplication.Retrofit

import com.example.myapplication.dto.User
import com.example.myapplication.packet.LoginCheckPacket
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FunInterface {
    @POST("/login")
    fun tryLogin(@Body loginCheckPacket: LoginCheckPacket) : Call<Boolean>

    @POST("/signIn")
    fun signIn(@Body user:User) : Call<String>
}