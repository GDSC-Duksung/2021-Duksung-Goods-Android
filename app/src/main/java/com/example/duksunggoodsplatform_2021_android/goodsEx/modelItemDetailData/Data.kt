package com.example.duksunggoodsplatform_2021_android.goodsEx.modelItemDetailData

data class Data(
    val category: Category,
    val createdAt: String,
    val demandSurveyType: DemandSurveyType,
    val description: String,
    val endDate: String,
    val id: Int,
    val imageList: List<Image>,
    val maxNumber: Int,
    val minNumber: Int,
    val price: Int,
    val progress: Any,
    val startDate: String,
    val title: String,
    val user: User
)