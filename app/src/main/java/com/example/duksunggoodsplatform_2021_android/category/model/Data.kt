package com.example.duksunggoodsplatform_2021_android.category.model

data class Data(
    val category: Category,
    val createdAt: String,
    val demandSurveyType: DemandSurveyType,
    val description: String,
    val endDate: String,
    val id: Int,
    val imageList: List<Image>,
    val likeOrNot: Boolean,
    val maxNumber: Int,
    val minNumber: Int,
    val numberOfGathered: Int,
    val percentage: Double,
    val price: Int,
    val progress: Int,
    val startDate: String,
    val title: String,
    val user: User
)