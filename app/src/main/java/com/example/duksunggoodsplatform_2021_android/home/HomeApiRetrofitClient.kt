package com.example.duksunggoodsplatform_2021_android.home

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object HomeApiRetrofitClient {

    //okhttp logging interceptor
    var homeInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.e("", message)
        }
    })

    private var homeClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(homeInterceptor)
        .build()

    //retrofit
    private val homeRetrofit: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(HomeApi.BASE_URL)
            .client(homeClient) //okhttp
            .addConverterFactory(GsonConverterFactory.create())
    }

    val homeApiService: HomeApiInterface by lazy {
        homeRetrofit
            .build()
            .create(HomeApiInterface::class.java)
    }
}