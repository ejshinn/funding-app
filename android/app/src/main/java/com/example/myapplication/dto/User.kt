package com.example.myapplication.dto



data class User(
    var id:Long,
    var userId:String,
    var userPw:String,
    var userEmail:String,
    var userName:String,
    var address:String,
    var projectList:List<Project>,
    var supportList:List<Support>,
    var favoriteList:List<Favorite>
)
