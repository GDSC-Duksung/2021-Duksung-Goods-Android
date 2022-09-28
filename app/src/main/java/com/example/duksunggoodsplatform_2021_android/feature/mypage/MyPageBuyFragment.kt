package com.example.duksunggoodsplatform_2021_android.feature.mypage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.api.ApiRetrofitClient
import com.example.duksunggoodsplatform_2021_android.data.response.ItemBuyInfo
import com.example.duksunggoodsplatform_2021_android.data.response.ResponseBuyItemData
import com.example.duksunggoodsplatform_2021_android.model.ResponseEntity
import kotlinx.android.synthetic.main.fragment_mypage_buy.*
import retrofit2.Call
import retrofit2.Response

class MyPageBuyFragment : Fragment() {

    lateinit var BuyRecyclerAdapter: BuyRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mypage_buy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BuyRecyclerAdapter = BuyRecyclerAdapter(requireActivity())
        rv_mypage_buy.adapter = BuyRecyclerAdapter

        initNetwork()
    }

    private fun initNetwork() {
        ApiRetrofitClient.apiService.getBuyItem().enqueue(
            object : retrofit2.Callback<ResponseEntity<List<ResponseBuyItemData>>> {
                override fun onResponse(
                    call: Call<ResponseEntity<List<ResponseBuyItemData>>>,
                    response: Response<ResponseEntity<List<ResponseBuyItemData>>>
                ) {
                    BuyRecyclerAdapter.buyItemDatas.addAll(response.body()?.data ?: listOf<ResponseBuyItemData>())
                    BuyRecyclerAdapter.notifyDataSetChanged()
                }

                override fun onFailure(
                    call: Call<ResponseEntity<List<ResponseBuyItemData>>>,
                    t: Throwable
                ) {
                    Log.d("MyPageBuyFragment", "api fail")
                }
            }
        )
    }
}
