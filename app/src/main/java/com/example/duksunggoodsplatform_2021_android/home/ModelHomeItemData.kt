package com.example.duksunggoodsplatform_2021_android.home

data class ModelHomeItemData(
    val transactionTime: String,
    val status: String,
    val message: String,
    val data: List<ItemData?>
)

data class ItemData(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val minNumber: Int,
    val maxNumber: Int,
    val startDate: String,
    val endDate: String,
    val progress: Int, //TODO : Int맞나?
    val createdAt: String,
    val user: User,
    val category: Category,
    val demandSurveyType: DemandSurveyType,
    val imageList: List<Image>?
)

data class User(
    val id: Int,
    val email: String,
    val nickname: String,
)

data class Category(
    val id: Int,
    val title: String
)

data class DemandSurveyType(
    val id: Int,
    val title: String
)

data class Image(
    val url: String
)