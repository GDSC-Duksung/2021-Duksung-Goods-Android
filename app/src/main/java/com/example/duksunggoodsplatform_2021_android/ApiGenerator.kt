package com.example.duksunggoodsplatform_2021_android

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiGenerator {
    private val BASE_URL = "" // base url 넣기
    private var gson = GsonBuilder()
        .setLenient()
        .create()

    fun <T> generate(api: Class<T>): T = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
        .create(api)

    var okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
}