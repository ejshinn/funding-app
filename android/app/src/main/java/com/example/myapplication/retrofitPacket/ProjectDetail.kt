package com.example.myapplication.retrofitPacket

import com.example.myapplication.dto.User
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

data class ProjectDetail(
    val projectId: Int,
    val goalAmount: Int,
    val thumbnail: String,
    val currentAmount:Int,
    val title:String,
    val contents:String,
    val startDate: String,
    val endDate: String,
    val perPrice:Int,
    val user:UserPacket,
    val category:CategoryPacket,
    val numOfSupport:Int,
    val numOfFavorite:Int
) {
    fun calculateDday(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val endLocalDate = LocalDate.parse(endDate, formatter)
        return "${ChronoUnit.DAYS.between(LocalDate.now(), endLocalDate)}일 남음"
    }

    fun percent(): String {
        val formattedNumber = String.format("%,d", currentAmount)
        return formattedNumber + "% 달성"
    }
}