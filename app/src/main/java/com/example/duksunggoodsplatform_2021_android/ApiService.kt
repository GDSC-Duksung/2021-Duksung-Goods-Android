package com.example.duksunggoodsplatform_2021_android

import com.example.duksunggoodsplatform_2021_android.depositor.DepositorData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/sell/1")
    fun fetchDepositors(): Call<ResponseBody>
}