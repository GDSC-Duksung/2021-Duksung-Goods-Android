package com.example.duksunggoodsplatform_2021_android.api

import android.util.Log
import com.example.duksunggoodsplatform_2021_android.data.local.SharedPreferenceController
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object ApiRetrofitClientNoAuth {

    //okhttp logging interceptor
    private var loggingInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.e("", message)
        }
    }).apply { level = HttpLoggingInterceptor.Level.BODY } //로그 자세히

    private var httpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        //.addInterceptor(RefreshInterceptor()) //token refresh interceptor
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