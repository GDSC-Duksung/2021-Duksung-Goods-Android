package com.example.duksunggoodsplatform_2021_android.home

data class ModelBannerData(
    val transactionTime: String,
    val status: String,
    val message: String,
    val data: List<BannerData>
)

data class BannerData(
    val id: Int,
    val image: String,
    val content: String,
    val startDate: String,
    val endDate: String,
    val user: User,
    val item: ItemData
)
