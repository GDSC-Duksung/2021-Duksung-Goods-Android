package com.example.duksunggoodsplatform_2021_android.api

import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.api.MyApplication.Companion.applicationContext
import com.example.duksunggoodsplatform_2021_android.data.local.SharedPreferenceController
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object ApiRetrofitClient {

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
        .addInterceptor(AppInterceptor())
        //.addInterceptor(AuthInterceptor()) //token refresh interceptor
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

    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
            Log.d("jh token",SharedPreferenceController.getUserToken(applicationContext()))
            var token = SharedPreferenceController.getUserToken(applicationContext())
            val newRequest = request().newBuilder()
                .addHeader("Authorization", "${token}")
                .build()
            proceed(newRequest)
        }
    }

////token refresh interceptor
//internal class AuthInterceptor: Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request()
//        val response = chain.proceed(request)
//        when (response.code) {
//            401 -> { //토큰 만료 시 오는 응답
//
//            }
//        }
//
//        return response
//    }
//}//inner class end








///////////////////////////
/*

internal class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        when (response.code) {
            401 -> { //토큰 만료 시 오는 응답
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
                                //토큰값 저장
                                val sharedPref = getSharedPreferences(
                                    getString(R.string.shared_preference_user_info), Context.MODE_PRIVATE)
                                with (sharedPref.edit()) {
                                    putString(getString(com.example.duksunggoodsplatform_2021_android.R.string.user_token), userToken)
                                    apply()
                                }



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
}//inner class end
*/

}