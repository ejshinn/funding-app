package com.example.myapplication.dto



data class Favorite(
    val favoriteId: String,
    val user:User,
    val project:Project
)
