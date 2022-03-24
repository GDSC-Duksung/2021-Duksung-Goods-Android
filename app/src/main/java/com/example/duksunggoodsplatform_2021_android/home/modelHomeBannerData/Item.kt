package com.example.duksunggoodsplatform_2021_android.home.modelHomeBannerData

data class Item(
    val category: Category,
    val createdAt: String,
    val demandSurveyType: DemandSurveyType,
    val description: String,
    val endDate: String,
    val id: Int,
    val imageList: Any,
    val maxNumber: Int,
    val minNumber: Int,
    val price: Int,
    val progress: Any,
    val startDate: String,
    val title: String,
    val user: User
)