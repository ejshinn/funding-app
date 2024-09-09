package com.example.myapplication.dto

import com.example.myapplication.R
import java.io.Serializable

data class Category(
    val categoryId: Int,
    val title: String,
    val image: Int?,
    val projectList: List<Project>
): Serializable

object CategoryManager {
    val categoryList = listOf(
        Category(1, "전체", R.drawable.cate1, listOf()),
        Category(2, "케릭터 · 굿즈", R.drawable.cate2, listOf()),
        Category(3, "홈 · 리빙", R.drawable.cate3, listOf()),
        Category(4, "테크 · 가전", R.drawable.cate4, listOf()),
        Category(5, "반려동물", R.drawable.cate5, listOf()),
        Category(6, "향수 · 뷰티", R.drawable.cate6, listOf()),
        Category(7, "의류", R.drawable.cate7, listOf()),
        Category(8, "잡화", R.drawable.cate8, listOf()),
        Category(9, "음악", R.drawable.cate9, listOf()),
        Category(10, "푸드", R.drawable.cate10, listOf()),
        Category(11, "주얼리", R.drawable.cate11, listOf())
    )
}
