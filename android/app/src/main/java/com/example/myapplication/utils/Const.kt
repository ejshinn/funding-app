package com.example.myapplication.utils

import com.example.myapplication.BuildConfig

object Const {
    val SHARED_PREF_LOGIN_NAME = "loginCheck"
    val SHARED_PREF_LOGIN_KEY = "isLogin"
    val SHARED_PREF_LOGIN_TRUE = "true"
    val SHARED_PREF_LOGIN_FALSE = "false"
    val SHARED_PREF_LOGIN_ID = "userId"

    val SERVER_BASE_URL = "${BuildConfig.BASE_URL}"
}