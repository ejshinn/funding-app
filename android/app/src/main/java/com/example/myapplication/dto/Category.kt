package com.example.myapplication.dto

data class Category(
    val categoryId:Int,
    val title:String,
    val projectList:List<Project>
)
