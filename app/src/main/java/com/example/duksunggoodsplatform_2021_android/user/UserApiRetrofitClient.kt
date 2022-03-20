package com.example.duksunggoodsplatform_2021_android.user

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object UserApiRetrofitClient {

    //okhttp logging interceptor
    var userInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.e("", message)
        }
    })

    private var userClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(userInterceptor)
        .build()

    //retrofit
    private val userRetrofit: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(HomeApi.BASE_URL)
            .client(userClient) //okhttp
            .addConverterFactory(GsonConverterFactory.create())
    }

    val userApiService: HomeApiInterface by lazy {
        userRetrofit
            .build()
            .create(HomeApiInterface::class.java)
    }
}