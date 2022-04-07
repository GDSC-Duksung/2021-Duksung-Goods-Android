package com.example.duksunggoodsplatform_2021_android.data.response

data class ResponseBuyItemData(
    val id: Int,
    val user: List<UserBuyInfo>,
    val item: List<ItemBuyInfo>,
    val count: Int,
    val createdAt: String
)

data class UserBuyInfo(
    val id: Int,
    val email: String,
    val name: String,
    val nickname: String,
    val phoneNumber: String,
    val address: String
)

data class ItemBuyInfo(
    val id: Int,
    val title: String,
    val description: String,
    val image: String,
    val price: Int,
    val minNumber: Int,
    val maxNumber: Int,
    val startData: String,
    val endData: String,
    val progress: String,
    val user: List<UserBuyInfo>,
    val category: List<CategoryBuyInfo>,
    val demandSurveyType: List<DemandTypeBuyInfo>
)

data class CategoryBuyInfo(
    val id: Int,
    val title: String
)

data class DemandTypeBuyInfo(
    val id: Int,
    val title: String
)
