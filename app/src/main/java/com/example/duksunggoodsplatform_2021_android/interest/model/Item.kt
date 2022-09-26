package com.example.duksunggoodsplatform_2021_android.interest.model

data class Item(
    val category: Category,
    val createdAt: String,
    val demandSurveyType: DemandSurveyType,
    val description: String,
    val endDate: String,
    val id: Int,
    val maxNumber: Int,
    val minNumber: Int,
    val price: Int,
    val progress: Int,
    val startDate: String,
    val title: String,
    val user: User
)