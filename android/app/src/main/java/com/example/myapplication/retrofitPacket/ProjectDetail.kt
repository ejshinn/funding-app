package com.example.myapplication.retrofitPacket

import android.util.Log
import com.example.myapplication.dto.User
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

data class ProjectDetail(
    val projectId: Int,
    val currentAmount:Int,
    val title:String,
    val contents:String,
    val startDate: String,
    val endDate: String,
    val perPrice:Int,
    val thumbnail: String,
    val user:UserPacket,
    val category:CategoryPacket,
    val numOfSupport:Int,
    val numOfFavorite:Int
): Serializable {
    fun calculateDday(): String {
        return try {
            // 시간 정보가 있는지 확인
            val endLocalDate = if (endDate.contains(":")) {
                // 시간 정보가 있는 경우 LocalDateTime 사용
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                LocalDateTime.parse(endDate, formatter).toLocalDate()  // LocalDateTime에서 LocalDate로 변환
            } else {
                // 시간 정보가 없는 경우 LocalDate 사용
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                LocalDate.parse(endDate, formatter)
            }

            // 현재 날짜와 종료 날짜 사이의 일 수 계산
            "${ChronoUnit.DAYS.between(LocalDate.now(), endLocalDate)}일 남음"
        } catch (e: DateTimeParseException) {
            Log.e("DateParsing", "Failed to parse date: $endDate", e)
            "날짜 형식 오류"
        }
    }

    fun percent(): String {
        val formattedNumber = String.format("%,d", currentAmount)
        return formattedNumber + "% 달성"
    }
}