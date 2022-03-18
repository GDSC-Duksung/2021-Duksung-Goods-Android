package com.example.duksunggoodsplatform_2021_android.home

import retrofit2.Call
import retrofit2.http.GET

class HomeApi {
    companion object {
        const val BASE_URL = "http://15.164.71.164:8080/api/"
    }
}

interface HomeApiInterface {

    //홍보 뷰(배너) 조회
    @GET("promotions")
    fun getBannerData(): Call<ModelBannerData>

    //아이템 전체 목록 조회
    @GET("item/home")
    fun getHomeItemData(): Call<ModelHomeItemData>


}