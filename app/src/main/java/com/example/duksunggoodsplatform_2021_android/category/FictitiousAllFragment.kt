package com.example.duksunggoodsplatform_2021_android.category

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.api.ApiRetrofitClient
import com.example.duksunggoodsplatform_2021_android.category.data.CategoryViewModel
import com.example.duksunggoodsplatform_2021_android.category.model.ModelCategoryItemListData
import kotlinx.android.synthetic.main.fragment_fictitious_all.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FictitiousAllFragment : Fragment() {

    private val model: CategoryViewModel by viewModels()

    lateinit var recyclerAdapter: RecyclerAdapter
    val datas = mutableListOf<CategoryItemData>()
    var pageNum = 1
    private val demandSurveyType = 1
    private val categoryId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_fictitious_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerAdapter = RecyclerAdapter(requireActivity())
        recyclerView.adapter = recyclerAdapter
        recyclerAdapter.datas = datas

//        val datasObserver = Observer<ArrayList<CategoryItemData>> {
//            Log.e("jh", "observe : pageNum ${pageNum}")
//            for (item in it){
//                datas.add(item)
//            }
//            recyclerAdapter.notifyDataSetChanged()
//            pageNum += 1
//
//        }
//
//        model.datas.observe(requireActivity(), datasObserver)

        model.datas.observe(requireActivity()) { datas ->
            Log.e("jh", "observe : pageNum ${pageNum}")
            for (item in datas){
                datas.add(item)
            }
            recyclerAdapter.notifyDataSetChanged()
            pageNum += 1

        }

        model.getItems(demandSurveyType, categoryId, pageNum)


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter?.itemCount
                if(lastVisibleItemPosition + 1 == itemTotalCount) {
                    model.getItems(demandSurveyType, categoryId, pageNum)
                }
            }
        })

        //initRecycler()
    }



    /*@SuppressLint("NotifyDataSetChanged")
    fun initRecycler() {

////        Log.d("jh", "call ${pageNum} page")
//        val run = runBlocking {
//            getItems2(pageNum)
//        }
//        Log.d("jh", "out runBlocking")


//        datas.apply {
//            add(CategoryItemData(photo = R.drawable.towel, name = "덕성여대 버건디 수건", price = "12,000원", entireNum = 100, currentNum = 80, remainingTime = 5))
//            add(CategoryItemData(photo = R.drawable.towel, name = "덕성여대 버건디 수건", price = "12,000원", entireNum = 100, currentNum = 80, remainingTime = 3))
//            add(CategoryItemData(photo = R.drawable.towel, name = "덕성여대 버건디 수건", price = "12,000원", entireNum = 100, currentNum = 50, remainingTime = 3))
//            add(CategoryItemData(photo = R.drawable.towel, name = "덕성여대 버건디 수건", price = "12,000원", entireNum = 100, currentNum = 50, remainingTime = 3))
//            add(CategoryItemData(photo = R.drawable.towel, name = "덕성여대 버건디 수건", price = "12,000원", entireNum = 100, currentNum = 50, remainingTime = 3))
//
//        }
    }
*/


/*

    val responseData2 = MutableLiveData <ModelCategoryItemListData>()
    suspend fun getItems2(page: Int) =
        suspendCancellableCoroutine<Int> {
            apiService.getCategoryItemData(demandSurveyTypeId = 1, categoryId = 0, page = page)
                .enqueue(object : retrofit2.Callback<ModelCategoryItemListData> {
                    override fun onResponse(
                        call: Call<ModelCategoryItemListData>,
                        response: Response<ModelCategoryItemListData>
                    ) {

                        responseData2.value = response.body()

                        val status = responseData2.value?.status
                        if(status == "OK"){
                            val data = responseData2.value?.data

                            if(data != null){
                                val calendar = Calendar.getInstance() //현재 시각에 대한 Calender 객체 반환
                                calendar.set(Calendar.HOUR, 0)
                                calendar.set(Calendar.MINUTE, 0)
                                calendar.set(Calendar.SECOND, 0)
                                calendar.set(Calendar.MILLISECOND, 0) //연월일만 남기고 0으로 만듦
                                val todayDate = calendar.time //현재 시각을 millisecond 로 반환
                                val format = SimpleDateFormat("yyyy-MM-dd")

                                for (item in data) {
                                    val parseDate = format.parse(item.endDate) //millisecond 단위임. 0.001초. 1000을 곱해야 1초가 됨
                                    val leftDate = ((parseDate.time - todayDate.time) / (1000*60*60*24)).toInt() //시간차를 구하고 하루 단위로 변경

                                    datas.add(CategoryItemData(photo = item.imageList[0].url, name = item.title, price = item.price, entireNum = item.maxNumber, currentNum = item.minNumber, remainingDate = leftDate))
                                }
//
//                            recyclerAdapter.notifyDataSetChanged()
//                            pageNum += 1


                            }
                        }

                    }

                    override fun onFailure(call: Call<ModelCategoryItemListData>, t: Throwable) {
                        Log.e("jh", "getCategoryItem fail..")
                    }
                })
        }
*/
/*

    fun getItems(page: Int) {
        val responseData = MutableLiveData <ModelCategoryItemListData>()

        apiService.getCategoryItemData(demandSurveyTypeId = 1, categoryId = 0, page = page)
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
                            val calendar = Calendar.getInstance() //현재 시각에 대한 Calender 객체 반환
                            calendar.set(Calendar.HOUR, 0)
                            calendar.set(Calendar.MINUTE, 0)
                            calendar.set(Calendar.SECOND, 0)
                            calendar.set(Calendar.MILLISECOND, 0) //연월일만 남기고 0으로 만듦
                            val todayDate = calendar.time //현재 시각을 millisecond 로 반환
                            val format = SimpleDateFormat("yyyy-MM-dd")

                            for (item in data) {
                                val parseDate = format.parse(item.endDate) //millisecond 단위임. 0.001초. 1000을 곱해야 1초가 됨
                                val leftDate = ((parseDate.time - todayDate.time) / (1000*60*60*24)).toInt() //시간차를 구하고 하루 단위로 변경

                                datas.add(CategoryItemData(photo = item.imageList[0].url, name = item.title, price = item.price, entireNum = item.maxNumber, currentNum = item.minNumber, remainingDate = leftDate))
                            }
//
//                            recyclerAdapter.notifyDataSetChanged()
//                            pageNum += 1


                        }
                    }

                }

                override fun onFailure(call: Call<ModelCategoryItemListData>, t: Throwable) {
                    Log.e("jh", "getCategoryItem fail..")
                }
            })
    }
*/

}