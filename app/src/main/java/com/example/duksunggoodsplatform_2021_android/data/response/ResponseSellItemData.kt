package com.example.duksunggoodsplatform_2021_android.data.response

data class ResponseSellItemData(
    val id: Int,
    val title: String,
    val description: String,
    val imageList: List<ImageListInfo>,
    val price: Int,
    val minNumber: Int,
    val maxNumber: Int,
    val startDate: String,
    val endData: String,
    val progress: Int,
    val createAt: String,
    val user: UserSellInfo,
    val category: CategorySellInfo,
    val demandSurveyType: DemandTypeSellInfo,
    val percentage: Float
)

data class UserSellInfo(
    val id: Int,
    val email: String,
    val name: String,
    val nickname: String,
    val phoneNumber: String,
    val address: String
)

data class CategorySellInfo(
    val id: Int,
    val title: String
)

data class DemandTypeSellInfo(
    val id: Int,
    val title: String
)

data class ImageListInfo(
    val url: String
)
