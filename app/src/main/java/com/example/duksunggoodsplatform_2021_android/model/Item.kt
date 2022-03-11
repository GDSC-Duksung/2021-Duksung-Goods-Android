package com.example.duksunggoodsplatform_2021_android.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


data class Item(
    val id: Long?,
    val title: String?,
    val description: String?,
    val price: Long?,
    val minNumber: Long?,
    val maxNumber: Long?,
    val startDate: String?,
    val endDate: String?,
    val progress: Boolean?,
    val createdAt: String?,
    val user: User?,
    val category: Category?,
    val demandSurveyType: DemandSurveyType?,
    val imageList: String?
)