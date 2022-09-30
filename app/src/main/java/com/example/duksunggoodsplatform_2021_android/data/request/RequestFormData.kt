package com.example.duksunggoodsplatform_2021_android.data.request

data class RequestFormData(
    val address: String,
    val count: Int,
    val createdAt: String,
    val email: String,
    val extra: String,
    val name: String,
    val phoneNumber: String,
    val refundAccountNumber: String,
    val zipCode: Int
)
