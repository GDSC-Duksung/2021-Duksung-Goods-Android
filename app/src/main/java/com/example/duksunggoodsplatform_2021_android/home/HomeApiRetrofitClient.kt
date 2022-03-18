package com.example.duksunggoodsplatform_2021_android.home

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HomeApiRetrofitClient {
    private val homeRetrofit: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(HomeApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val homeApiService: HomeApiInterface by lazy {
        homeRetrofit
            .build()
            .create(HomeApiInterface::class.java)
    }
}