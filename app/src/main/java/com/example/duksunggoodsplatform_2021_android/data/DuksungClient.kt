package com.example.duksunggoodsplatform_2021_android.data

import com.example.duksunggoodsplatform_2021_android.data.api.MyPageService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DuksungClient {
    private const val BASE_URL = "http://15.164.71.164/"

    val mypageService: MyPageService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        provideService(MyPageService::class.java)
    }

    private fun <T> provideService(_class: Class<T>): T = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(_class)
}
