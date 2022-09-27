package com.example.duksunggoodsplatform_2021_android.category

import android.graphics.drawable.Drawable

data class CategoryItemData(
    val id: Int,
    val name: String,
    val price: Int,
    val photo: String,
    val entireNum: Int,
    val currentNum: Int,
    val remainingDate: Int,
    val like: Boolean
    )