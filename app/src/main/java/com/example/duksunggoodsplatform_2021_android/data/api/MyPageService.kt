package com.example.duksunggoodsplatform_2021_android.data.api

import com.example.duksunggoodsplatform_2021_android.data.response.ResponseBuyItemData
import com.example.duksunggoodsplatform_2021_android.data.response.ResponseSellItemData
import com.example.duksunggoodsplatform_2021_android.model.ResponseEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MyPageService {
    @GET("api/buy-item")
    fun getBuyItem(
        @Header("token") token: String,
    ): Call<ResponseEntity<ResponseBuyItemData>>

    @GET("api/sell-item")
    fun getSellItem(
        @Header("token") token: String,
    ): Call<ResponseEntity<ResponseSellItemData>>
}
