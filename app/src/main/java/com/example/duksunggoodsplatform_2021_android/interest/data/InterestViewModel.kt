package com.example.duksunggoodsplatform_2021_android.interest.data

import android.util.Log
import androidx.lifecycle.*
import com.example.duksunggoodsplatform_2021_android.api.ApiRetrofitClient
import com.example.duksunggoodsplatform_2021_android.category.CategoryItemData
import com.example.duksunggoodsplatform_2021_android.category.model.ModelCategoryItemListData
import com.example.duksunggoodsplatform_2021_android.goodsEx.ModelItemLikesChangeData
import com.example.duksunggoodsplatform_2021_android.interest.ModelInterestItem
import com.example.duksunggoodsplatform_2021_android.interest.model.ModelInterestListData
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class InterestViewModel(): ViewModel() {
    private val _datas = MutableLiveData<ArrayList<ModelInterestItem>>()

    val datas: LiveData<ArrayList<ModelInterestItem>>
        get() = _datas

    init {
//        _datas.value = arrayListOf(ModelInterestItem(10000, "박지혜", "잉ㅇ", 10000))
    }

    val apiService = ApiRetrofitClient.apiService
    fun getInterestList() = viewModelScope.launch {
        kotlin.runCatching {
            val responseData = MutableLiveData <ModelInterestListData>()

            apiService.getItemLikesList()
                .enqueue(object : retrofit2.Callback<ModelInterestListData> {
                    override fun onResponse(
                        call: Call<ModelInterestListData>,
                        response: Response<ModelInterestListData>
                    ) {

                        responseData.value = response.body()

                        val status = responseData.value?.status
                        if(status == "OK"){
                            val data = responseData.value?.data

                            if(data != null && data.isNotEmpty()){ //요청이 성공했고, data가 null이나 빈 배열이 아닐 때만 LiveData가 변함(=observer가 동작함)
                                Log.d("jh", "call data != null")

                                val calledDatas = arrayListOf<ModelInterestItem>()

                                for (d in data) {
                                    Log.d("jh", "call for문 d: ${d}")
                                    calledDatas.add(
                                        ModelInterestItem(
                                            id = d.id,
                                            writer = d.item.user.name,
                                            itemName = d.item.title,
                                            price = d.item.price
//                                            imgUrl = d.item. //TODO : api에서 사진을 응답해주지 않음. 추후 수저 요청 필요
                                        )
                                    )

                                }
                                _datas.value = calledDatas

                                Log.d("jh", "call success : ${_datas.value}")


                            }
                            else {
                                Log.d("jh", "data is null or empty")
                            }
                        }

                    }

                    override fun onFailure(call: Call<ModelInterestListData>, t: Throwable) {
                        Log.e("jh", "getInterestList fail..")
                    }
                })


        }
    }


    fun deleteItemLikes(itemId: Int) = viewModelScope.launch {
        kotlin.runCatching {
            val responseData = MutableLiveData <ModelItemLikesChangeData>()

            apiService.postItemLikesChange(itemId)
                .enqueue(object : retrofit2.Callback<ModelItemLikesChangeData> {
                    override fun onResponse(
                        call: Call<ModelItemLikesChangeData>,
                        response: Response<ModelItemLikesChangeData>
                    ) {

                        responseData.value = response.body()

                        val status = responseData.value?.status
                        if(status == "OK"){
                            val data = responseData.value?.data

                            if(data != null){
                                Log.d("jh", "call data != null")
                                Log.d("jh", "data: ${data}")
                                getInterestList()

                            }
                            else {
                                Log.d("jh", "data is null or empty")
                            }
                        }

                    }

                    override fun onFailure(call: Call<ModelItemLikesChangeData>, t: Throwable) {
                        Log.e("jh", "deleteItemLikes fail..")
                    }
                })


        }
    }


}