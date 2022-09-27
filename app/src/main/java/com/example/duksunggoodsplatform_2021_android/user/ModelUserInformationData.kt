package com.example.duksunggoodsplatform_2021_android.user

data class ModelUserInformationData(
    val `data`: Data,
    val message: String,
    val status: String,
    val transactionTime: String
)

data class Data(
    val id: String,
    val name: String,
    val email: String,
    val nickname: String,
    val address: String?,
    val phoneNumber: String?
)