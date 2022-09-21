package com.example.duksunggoodsplatform_2021_android.api

import com.example.duksunggoodsplatform_2021_android.category.model.ModelCategoryItemListData
import com.example.duksunggoodsplatform_2021_android.goodsEx.ModelItemLikesChangeData
import com.example.duksunggoodsplatform_2021_android.goodsEx.modelItemDetailData.ModelItemDetailData
import com.example.duksunggoodsplatform_2021_android.home.modelHomeBannerData.ModelHomeBannerData
import com.example.duksunggoodsplatform_2021_android.home.modelHomeItemData.ModelHomeItemData
import com.example.duksunggoodsplatform_2021_android.user.ModelLoginSignUpResponseData
import com.example.duksunggoodsplatform_2021_android.user.ModelUserInformationData
import retrofit2.Call
import retrofit2.http.*

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

    //refresh
    @GET("users/refresh")
    fun getRefresh(): Call<ModelLoginSignUpResponseData>

    //내 정보 조회
    @GET("users/me")
    fun getUserInfo(): Call<ModelUserInformationData>


    //홍보 뷰(배너) 조회
    @GET("promotions")
    fun getBannerData(): Call<ModelHomeBannerData>

    //홈 아이템 6개 조회
    @GET("items/home")
    fun getHomeItemData(): Call<ModelHomeItemData>

    //카테고리별 아이템 목록 조회
    @GET("/api/items/demand-survey-type/{demandSurveyTypeId}/category/{categoryId}")
    fun getCategoryItemData(
        @Path("demandSurveyTypeId") demandSurveyTypeId: Int,
        @Path("categoryId") categoryId: Int,
        @Query("page") page: Int
    ): Call<ModelCategoryItemListData>

    //아이템 상세 조회
    @GET("items/{itemId}")
    fun getItemDetailData(
        @Path("itemId") itemId: Int
    ): Call<ModelItemDetailData>

    //아이템 좋아요 변경
    @POST("items/{itemId}/likes")
    fun postItemLikesChange(
        @Path("itemId") itemId: Int
    ): Call<ModelItemLikesChangeData>


}