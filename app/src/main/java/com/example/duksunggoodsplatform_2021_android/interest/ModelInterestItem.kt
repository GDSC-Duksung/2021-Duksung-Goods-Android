package com.example.duksunggoodsplatform_2021_android.interest

data class ModelInterestItem (
    val id: Int,
    val writer: String,
    val itemName: String,
    val price: Int,
    val imgUrl: String? = null
)