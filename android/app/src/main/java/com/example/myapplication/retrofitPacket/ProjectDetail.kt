package com.example.myapplication.retrofitPacket

import com.example.myapplication.dto.User
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ProjectDetail(
    val projectId: Int,
    val currentAmount:Int,
    val title:String,
    val contents:String,
    val startDate: String,
    val endDate: String,
    val prtPrice:Int,
    val userId:Int,
    val categoryId:Int
)