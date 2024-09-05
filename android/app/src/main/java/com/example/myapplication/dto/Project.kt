package com.example.myapplication.dto


data class Project(
    val project: Long,
    val currentAmount:Int,
    val title:String,
    val contents:String,
    val startDate: String,
    val endDate:String,
    val prtPrice:Int,
    val user:User,
    val supportList:List<Support>,
    val favoriteList:List<Favorite>
)
