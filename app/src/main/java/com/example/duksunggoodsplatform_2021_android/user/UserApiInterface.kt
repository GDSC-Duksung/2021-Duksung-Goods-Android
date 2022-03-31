package com.example.duksunggoodsplatform_2021_android.user

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

class HomeApi {
    companion object {
        const val BASE_URL = "http://15.164.71.164:8080/api/"
    }
}

interface HomeApiInterface {

    //회원가입
    @POST("users/signup")
    fun postSignUp(
        @Body params: HashMap<String, String>
    ): Call<ModelLoginSignUpResponseData>

    //로그인
    @POST("users/login")
    fun postLogin(
        @Body params: HashMap<String, String>
    ): Call<ModelLoginSignUpResponseData>


}