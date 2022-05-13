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

/*

internal class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        when (response.code) {
            401 -> {
                // Unauthorized -> token refresh
                val apiService = ApiRetrofitClient.apiService

                fun callTokenRefresh(body: HashMap<String, String>) {
                    val responseData = MutableLiveData<ModelTokenRefreshData>()

                    apiService.tokenRefresh(body)
                        .enqueue(object : retrofit2.Callback<ModelTokenRefreshData> {
                            override fun onResponse(
                                call: Call<ModelTokenRefreshData>,
                                response: retrofit2.Response<ModelTokenRefreshData>
                            ) {
                                responseData.value = response.body()

                                //Log.d("로그refresh---", "통신성공 : ${responseData.value}")
//                                var userToken: String?
*/
/*
                                //토큰값 저장
                                val sharedPref = getSharedPreferences(
                                    getString(R.string.shared_preference_user_info), Context.MODE_PRIVATE)
                                with (sharedPref.edit()) {
                                    putString(getString(com.example.duksunggoodsplatform_2021_android.R.string.user_token), userToken)
                                    apply()
                                }*//*



                            }

                            override fun onFailure(call: Call<ModelTokenRefreshData>, t: Throwable) {
                                Log.e("로그refresh error--", "실패")
                                t.printStackTrace()
                            }

                        })
                }
            }
        }
    }
}*/
}