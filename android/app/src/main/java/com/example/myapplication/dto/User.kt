package com.example.myapplication.dto

data class User(
    var id:Long,
    var userId:String,
    var userPw:String,
    var userEmail:String,
    var userName:String,
    var adress:String,
    var getDeletedYn:String
)
