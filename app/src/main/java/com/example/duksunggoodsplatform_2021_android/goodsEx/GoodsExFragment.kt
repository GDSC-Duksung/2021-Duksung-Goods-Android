package com.example.duksunggoodsplatform_2021_android.goodsEx

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.duksunggoodsplatform_2021_android.InterestListActivity
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.api.ApiRetrofitClient
import com.example.duksunggoodsplatform_2021_android.databinding.FragmentGoodsExBinding
import com.example.duksunggoodsplatform_2021_android.feature.form.ActualFormActivity
import com.example.duksunggoodsplatform_2021_android.feature.form.FictitiousFormActivity
import com.example.duksunggoodsplatform_2021_android.goodsEx.modelItemDetailData.Data
import com.example.duksunggoodsplatform_2021_android.goodsEx.modelItemDetailData.ModelItemDetailData
import retrofit2.Call
import retrofit2.Response
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class GoodsExFragment : Fragment() {
    private var _binding: FragmentGoodsExBinding? = null
    private val binding get() = _binding!!

    // 가수요/실수요 구분 값. actual 이면 실수요, fictitious 면 가수요
    private val codeTypeActual = "actual"
    private val codeTypeFictitious = "fictitious"

    private var itemId = -1
    private var fav = false
//    private var price = -1
//    private var category = "NoData"
//    private var type = codeTypeActual
    private var imageList = arrayListOf<ModelGoodsExImage>()
    private lateinit var imageAdapter: GoodsExImageAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentGoodsExBinding.inflate(inflater, container, false)
        val view = binding.root

        itemId = arguments?.getInt("itemId") ?: -1
//        Log.d("jh", "itemId: ${itemId}")


        var purchaseCount = 1

        if(itemId == -1){
            Toast.makeText(activity, "잘못된 상품 정보 입니다. itemId = ${itemId}", Toast.LENGTH_SHORT).show()
            Log.d("GoodsEx", "잘못된 상품 정보 입니다. itemId = ${itemId}")
        }else{
            callItemDetailData(itemId)
        }

        binding.ivGoodsExFavorite.setOnClickListener {
            setFav(!fav, true)
        }


        //이미지 RecyclerView
        binding.rvGoodsExImage.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvGoodsExImage.setHasFixedSize(true)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvGoodsExImage)

        imageAdapter = GoodsExImageAdapter(imageList, requireActivity())
        binding.rvGoodsExImage.adapter = imageAdapter


        //관심 별버튼 클릭
        binding.ivGoodsExFavorite.setOnClickListener {
            fav = !fav
            setFav(fav, true)
        }

        binding.tvGoodsExCountMinus.setOnClickListener {
            if(purchaseCount > 1){
                purchaseCount -= 1
                binding.tvGoodsExCount.text = "${purchaseCount}"
            }else if(purchaseCount <= 1){
                Toast.makeText(activity, "최소 구매 가능 수량입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvGoodsExCountPlus.setOnClickListener {
            if(purchaseCount < 99999){
                purchaseCount += 1
                binding.tvGoodsExCount.text = "${purchaseCount}"
            }else if(purchaseCount >= 99999){
                Toast.makeText(activity, "최대 구매 가능 수량입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnGoodsExFavoriteList.setOnClickListener {
            val favIntent = Intent(activity, InterestListActivity::class.java)
            startActivity(favIntent)
        }


        return view
    }

    //서버 연결
    private val homeApi = ApiRetrofitClient.apiService

    private fun callItemDetailData(itemId: Int) {
        val itemDetailData = MutableLiveData<ModelItemDetailData>()

        homeApi.getItemDetailData(itemId)
            .enqueue(object : retrofit2.Callback<ModelItemDetailData> {

                override fun onResponse(
                    call: Call<ModelItemDetailData>,
                    response: Response<ModelItemDetailData>
                ) {
                    itemDetailData.value = response.body()
                    //Log.d("로그detail---", "성공 : ${itemDetailData.value}")
                    if(itemDetailData.value != null){
                        val data = itemDetailData.value!!.data
                        setItemDetailData(data)


                        val imgList = arrayListOf<String>()
                        for (img in data.imageList) {
                            imgList.add(img.url)
                        }

                        val bundle = Bundle()
                        bundle.putString("title", data.title)
                        bundle.putStringArrayList("imgList", imgList)
                        bundle.putString("description", data.description)

                        val goodsDetailFragment = GoodsDetailFragment()
                        goodsDetailFragment.arguments = bundle
                        setFragmentResult("goodsInfo", bundle) //bundleOf("bundleKey" to result)
//                        goodsDetailFragment.setData()

/*                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container_bundle, PassBundleFragment())
                            .commit()*/

                    }else{
                        Log.e("로그detail--", "ItemDetailData.value가 null임")
                    }

                }

                override fun onFailure(call: Call<ModelItemDetailData>, t: Throwable) {
                    Log.e("로그home item-error--", "실패")
                    t.printStackTrace()
                }

            })
    }

    private fun setItemDetailData(data: Data) {
//        Log.d("로그detail--set-", "Data : ${data}")

        setFav(data.likeOrNot, false)

        val priceFormat = DecimalFormat("#,###")
        //type = data.demandSurveyType

        for (img in data.imageList){
            imageList.add(ModelGoodsExImage(img.url))
        }
        imageAdapter.notifyDataSetChanged()

        binding.tvGoodsExCategory.text = data.category.title
        binding.tvGoodsExTitle.text = data.title
        binding.tvGoodsExPrice.text = priceFormat.format(data.price) + "원"

        val numberOfGathered = data.numberOfGathered.toString() //모인개수
        binding.tvGoodsExTotalCount.text =  "모인 개수\n${numberOfGathered}개"

        val calendar = Calendar.getInstance() //현재 시각에 대한 Calender 객체 반환
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0) //연월일만 남기고 0으로 만듦
        val todayDate = calendar.time //현재 시각을 millisecond 로 반환
        val format = SimpleDateFormat("yyyy-MM-dd")
        val parseDate = format.parse(data.endDate) //millisecond 단위임. 0.001초. 1000을 곱해야 1초가 됨
        val leftTime = (parseDate.time - todayDate.time) / (1000*60*60*24) //시간차를 구하고 하루 단위로 변경
        binding.tvGoodxExTime.text = "남은 시간\n${leftTime}일"

        binding.tvGoodsExInfoEndCount.text = "목표개수는 ${data.minNumber}개가 모여야만 결제됩니다."
        binding.tvGoodsExInfoPayment.text = data.description

        binding.btnGoodsExForm.setOnClickListener {
            var formIntent = Intent()
            when(data.demandSurveyType.title){
                codeTypeFictitious -> formIntent = Intent(activity, FictitiousFormActivity::class.java)
                codeTypeActual -> formIntent = Intent(activity, ActualFormActivity::class.java)
            }
            startActivity(formIntent)
        }

    }
/*
    //좋아요 조회
    private fun callItemLikesData(itemId: Int) {
        val itemLikesData = MutableLiveData<ModelItemLikesData>()

        homeApi.getItemLikesData(itemId)
            .enqueue(object : retrofit2.Callback<ModelItemLikesData> {

                override fun onResponse(
                    call: Call<ModelItemLikesData>,
                    response: Response<ModelItemLikesData>
                ) {
                    itemLikesData.value = response.body()
                    //Log.d("로그Likes---", "성공 : ${itemLikesData.value}")
                    if(itemLikesData.value != null){
                        val data = itemLikesData.value!!.data
                        setItemLikesData(data)

                    }else{
                        Log.e("로그Likes--", "ItemLikesData.value가 null임")
                    }

                }

                override fun onFailure(call: Call<ModelItemLikesData>, t: Throwable) {
                    Log.e("로그home item-error--", "실패")
                    t.printStackTrace()
                }

            })
    }
*/

    //좋아요 변경
    private fun postItemLikesChange(itemId: Int) {
        val itemLikesData = MutableLiveData<ModelItemLikesChangeData>()

        homeApi.postItemLikesChange(itemId)
            .enqueue(object : retrofit2.Callback<ModelItemLikesChangeData> {

                override fun onResponse(
                    call: Call<ModelItemLikesChangeData>,
                    response: Response<ModelItemLikesChangeData>
                ) {
                    itemLikesData.value = response.body()
                    Log.d("로그Likes---", "성공 : ${itemLikesData.value}")
//                    if(itemLikesData.value != null){
////                        val data = itemLikesData.value!!.data
//                        //setItemLikesData(data)
////                        setFav(data, false)
//
//                    }else{
//                        Log.e("로그Likes--", "ItemLikesData.value가 null임")
//                    }

                }

                override fun onFailure(call: Call<ModelItemLikesChangeData>, t: Throwable) {
                    Log.e("로그home item-error--", "실패")
                    t.printStackTrace()
                }

            })
    }


    // 관심 컨트롤 함수
    private fun setFav(fav: Boolean, statusChange: Boolean){
        if(fav){
            binding.ivGoodsExFavorite.setImageResource(R.drawable.star_clicked)
            if(statusChange){
                postItemLikesChange(itemId)
                Toast.makeText(activity, "관심목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }else if(!fav){
            binding.ivGoodsExFavorite.setImageResource(R.drawable.star_nonclicked)
            if(statusChange){
                postItemLikesChange(itemId)
                Toast.makeText(activity, "관심목록에서 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GoodsExFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                GoodsExFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
                }
    }
}