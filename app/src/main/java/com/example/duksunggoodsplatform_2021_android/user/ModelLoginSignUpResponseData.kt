package com.example.duksunggoodsplatform_2021_android.user

data class ModelLoginSignUpResponseData(
    val transactionTime: String,
    val status: String,
    val message: String,
    val data: String? //uid라고 함
)
