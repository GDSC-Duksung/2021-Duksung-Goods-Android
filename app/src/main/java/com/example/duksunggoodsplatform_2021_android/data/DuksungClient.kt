package com.example.duksunggoodsplatform_2021_android.data

import com.example.duksunggoodsplatform_2021_android.api.ApiRetrofitClient
import com.example.duksunggoodsplatform_2021_android.api.AuthInterceptor
import com.example.duksunggoodsplatform_2021_android.data.api.MyPageService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DuksungClient {
    private const val BASE_URL = "http://15.164.71.164/"

    val mypageService: MyPageService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        provideService(MyPageService::class.java)
    }

    private var httpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor())
        .build()

    private fun <T> provideService(_class: Class<T>): T = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(_class)
}
