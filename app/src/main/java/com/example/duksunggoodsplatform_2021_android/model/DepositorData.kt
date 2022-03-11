package com.example.duksunggoodsplatform_2021_android.model

import java.util.*

data class DepositorData(
    val id: Long?,
    val user: User?,
    val item: Item?,
    val count: Long?,
    var deposit: Boolean?,
    val createdAt: String?
)
