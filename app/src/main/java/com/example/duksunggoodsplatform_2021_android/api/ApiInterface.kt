package com.example.duksunggoodsplatform_2021_android.api

import com.example.duksunggoodsplatform_2021_android.goodsEx.ModelItemLikesChangeData
import com.example.duksunggoodsplatform_2021_android.goodsEx.modelItemDetailData.ModelItemDetailData
import com.example.duksunggoodsplatform_2021_android.home.modelHomeBannerData.ModelHomeBannerData
import com.example.duksunggoodsplatform_2021_android.home.modelHomeItemData.ModelHomeItemData
import com.example.duksunggoodsplatform_2021_android.user.ModelLoginSignUpResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

class ApiInfo {
    companion object {
        const val BASE_URL = "http://15.164.71.164/api/"
    }
}

interface ApiService {

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


    //홍보 뷰(배너) 조회
    @GET("promotions")
    fun getBannerData(): Call<ModelHomeBannerData>

    //아이템 전체 목록 조회
    @GET("items/home")
    fun getHomeItemData(): Call<ModelHomeItemData>


    //아이템 상세 조회
    @GET("items/{itemId}")
    fun getItemDetailData(
        @Path("itemId") itemId: Int
    ): Call<ModelItemDetailData>

/*    //아이템 좋아요 하나만 조회
    @GET("items/{itemId}/....")
    fun getItemLikesData(
        @Path("itemId") itemId: Int
    ): Call<ModelItemLikesData>*/

    //아이템 좋아요 변경
    @POST("items/{itemId}/likes")
    fun postItemLikesChange(
        @Path("itemId") itemId: Int
    ): Call<ModelItemLikesChangeData>


}