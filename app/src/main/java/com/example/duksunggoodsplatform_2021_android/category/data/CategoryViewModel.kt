package com.example.duksunggoodsplatform_2021_android.category.data

import android.util.Log
import androidx.lifecycle.*
import com.example.duksunggoodsplatform_2021_android.api.ApiRetrofitClient
import com.example.duksunggoodsplatform_2021_android.category.CategoryItemData
import com.example.duksunggoodsplatform_2021_android.category.model.ModelCategoryItemListData
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CategoryViewModel(): ViewModel() {
    private val _datas = MutableLiveData<ArrayList<CategoryItemData>>()

    val datas: LiveData<ArrayList<CategoryItemData>>
        get() = _datas

    val apiService = ApiRetrofitClient.apiService

    init {
        //_datas.value = arrayListOf()
        //_datas.value = arrayListOf(CategoryItemData(photo = "https://bit.ly/3yAt3za", name = "하이 아이템 이름", price = 15000, entireNum = 100, currentNum = 80, remainingDate = 7))

    }


    fun getItems(demandSurveyTypeId: Int, categoryId: Int, page: Int) = viewModelScope.launch {
        kotlin.runCatching {
            val responseData = MutableLiveData <ModelCategoryItemListData>()

            apiService.getCategoryItemData(demandSurveyTypeId = demandSurveyTypeId, categoryId = categoryId, page = page)
                .enqueue(object : retrofit2.Callback<ModelCategoryItemListData> {
                    override fun onResponse(
                        call: Call<ModelCategoryItemListData>,
                        response: Response<ModelCategoryItemListData>
                    ) {

                        responseData.value = response.body()

                        val status = responseData.value?.status
                        if(status == "OK"){
                            val data = responseData.value?.data

                            if(data != null && data.isNotEmpty()){ //요청이 성공했고, data가 null이나 빈 배열이 아닐 때만 LiveData가 변함(=observer가 동작함)
                                Log.d("jh", "call data != null")

                                val calledDatas = arrayListOf<CategoryItemData>()

                                val calendar = Calendar.getInstance() //현재 시각에 대한 Calender 객체 반환
                                calendar.set(Calendar.HOUR, 0)
                                calendar.set(Calendar.MINUTE, 0)
                                calendar.set(Calendar.SECOND, 0)
                                calendar.set(Calendar.MILLISECOND, 0) //연월일만 남기고 0으로 만듦
                                val todayDate = calendar.time //현재 시각을 millisecond 로 반환
                                val format = SimpleDateFormat("yyyy-MM-dd")

//                                if(_datas.value == null){
//                                    _datas.value = arrayListOf()
//                                }

                                for (item in data) {
                                    Log.d("jh", "call for문 item: ${item}")
                                    val parseDate = format.parse(item.endDate) //millisecond 단위임. 0.001초. 1000을 곱해야 1초가 됨
                                    val leftDate = ((parseDate.time - todayDate.time) / (1000*60*60*24)).toInt() //시간차를 구하고 하루 단위로 변경

                                    calledDatas.add(CategoryItemData(photo = item.imageList[0].url, name = item.title, price = item.price, entireNum = item.maxNumber, currentNum = item.minNumber, remainingDate = leftDate))

                                }
                                _datas.value = calledDatas

                                Log.d("jh", "call success : ${_datas.value}")
//                            recyclerAdapter.notifyDataSetChanged()
//                            pageNum += 1


                            }
                            else {
                                Log.d("jh", "data is null or empty")
                            }
                        }

                    }

                    override fun onFailure(call: Call<ModelCategoryItemListData>, t: Throwable) {
                        Log.e("jh", "getCategoryItem fail..")
                    }
                })


        }
    }


/*
    suspend fun getItems(demandSurveyTypeId: Int, categoryId: Int, page: Int) {
        val responseData = MutableLiveData <ModelCategoryItemListData>()

        apiService.getCategoryItemData(demandSurveyTypeId = demandSurveyTypeId, categoryId = categoryId, page = page)
            .enqueue(object : retrofit2.Callback<ModelCategoryItemListData> {
                override fun onResponse(
                    call: Call<ModelCategoryItemListData>,
                    response: Response<ModelCategoryItemListData>
                ) {

                    responseData.value = response.body()

                    val status = responseData.value?.status
                    if(status == "OK"){
                        val data = responseData.value?.data

                        if(data != null){
                            Log.d("jh", "call data != null")
                            val calendar = Calendar.getInstance() //현재 시각에 대한 Calender 객체 반환
                            calendar.set(Calendar.HOUR, 0)
                            calendar.set(Calendar.MINUTE, 0)
                            calendar.set(Calendar.SECOND, 0)
                            calendar.set(Calendar.MILLISECOND, 0) //연월일만 남기고 0으로 만듦
                            val todayDate = calendar.time //현재 시각을 millisecond 로 반환
                            val format = SimpleDateFormat("yyyy-MM-dd")

                            for (item in data) {
                                Log.d("jh", "call for문 item: ${item}")
                                val parseDate = format.parse(item.endDate) //millisecond 단위임. 0.001초. 1000을 곱해야 1초가 됨
                                val leftDate = ((parseDate.time - todayDate.time) / (1000*60*60*24)).toInt() //시간차를 구하고 하루 단위로 변경

                                _datas.value?.add(CategoryItemData(photo = item.imageList[0].url, name = item.title, price = item.price, entireNum = item.maxNumber, currentNum = item.minNumber, remainingDate = leftDate))
                            }

                            Log.d("jh", "call success : ${_datas.value}")
//                            recyclerAdapter.notifyDataSetChanged()
//                            pageNum += 1


                        }
                    }

                }

                override fun onFailure(call: Call<ModelCategoryItemListData>, t: Throwable) {
                    Log.e("jh", "getCategoryItem fail..")
                }
            })
    }*/


}