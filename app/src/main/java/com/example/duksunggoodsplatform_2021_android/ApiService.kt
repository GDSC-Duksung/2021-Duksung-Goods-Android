package com.example.duksunggoodsplatform_2021_android

import com.example.duksunggoodsplatform_2021_android.model.DepositorData
import com.example.duksunggoodsplatform_2021_android.model.ResponseEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import kotlin.collections.ArrayList

interface ApiService {
    @GET("api/sell/{itemId}")
    fun fetchDepositors(@Path("itemId") itemId: Long) : Call<ResponseEntity<MutableList<DepositorData>>>
}