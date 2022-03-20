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
    @POST("user/signup")
    fun postSignUp(): Call<ModelLoginSignUpResponseData>

    //로그인
    @POST("user/signin")
    fun postLogin(
        @Body params: HashMap<String, String>
    ): Call<ModelLoginSignUpResponseData>


}