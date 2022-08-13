package com.example.duksunggoodsplatform_2021_android.api

import android.util.Log
import com.example.duksunggoodsplatform_2021_android.data.local.SharedPreferenceController
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
        Log.d("jh token", SharedPreferenceController.getUserToken(MyApplication.applicationContext()))
        var token = SharedPreferenceController.getUserToken(MyApplication.applicationContext())
        val newRequest = request().newBuilder()
            .addHeader("Authorization", "${token}")
            .build()
        proceed(newRequest)
    }
}