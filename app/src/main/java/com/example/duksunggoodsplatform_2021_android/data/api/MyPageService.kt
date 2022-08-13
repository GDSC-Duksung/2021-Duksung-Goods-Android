package com.example.duksunggoodsplatform_2021_android.data.api

import com.example.duksunggoodsplatform_2021_android.data.response.ResponseBuyItemData
import com.example.duksunggoodsplatform_2021_android.data.response.ResponseSellItemData
import com.example.duksunggoodsplatform_2021_android.model.ResponseEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MyPageService {
    @GET("api/buy-items")
    fun getBuyItem(
//        @Header("Authorization") token: String,
    ): Call<ResponseEntity<List<ResponseBuyItemData>>>

    @GET("api/sell-items")
    fun getSellItem(
//        @Header("Authorization") token: String,
    ): Call<ResponseEntity<List<ResponseSellItemData>>>
}
