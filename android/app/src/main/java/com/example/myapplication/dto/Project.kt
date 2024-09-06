package com.example.myapplication.dto


data class Project(
    val projectId: Int,
    val goalAmount:Int,
    val currentAmount:Int,
    val title:String,
    val contents:String,
    val startDate: String,
    val endDate:String,
    val prtPrice:Int,
    val thumbnail:String,
    val user:User,
    val supportList:List<Support>,
    val favoriteList:List<Favorite>,
    val category: Category
)
