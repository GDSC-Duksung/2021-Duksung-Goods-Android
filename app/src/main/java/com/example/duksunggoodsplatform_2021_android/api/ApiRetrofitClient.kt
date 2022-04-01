package com.example.duksunggoodsplatform_2021_android.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiRetrofitClient {

    //okhttp logging interceptor
    var interceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.e("", message)
        }
    })

    private var httpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()

    //retrofit
    private val retrofit: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(ApiInfo.BASE_URL)
            .client(httpClient) //okhttp
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: ApiService by lazy {
        retrofit
            .build()
            .create(ApiService::class.java)
    }
}