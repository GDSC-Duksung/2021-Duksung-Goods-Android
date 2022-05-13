package com.example.duksunggoodsplatform_2021_android.feature.mypage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.data.DuksungClient
import com.example.duksunggoodsplatform_2021_android.data.customEnqueue
import com.example.duksunggoodsplatform_2021_android.data.local.SharedPreferenceController
import com.example.duksunggoodsplatform_2021_android.data.response.ItemBuyInfo
import com.example.duksunggoodsplatform_2021_android.data.response.ResponseBuyItemData
import kotlinx.android.synthetic.main.fragment_mypage_buy.*

class MyPageBuyFragment : Fragment() {

    lateinit var BuyRecyclerAdapter: BuyRecyclerAdapter
    var buy_item = mutableListOf<ItemBuyInfo>()

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
        DuksungClient.mypageService.getBuyItem(
            SharedPreferenceController.getUserToken(requireContext())
        ).customEnqueue(
            onSuccess = {
                BuyRecyclerAdapter.buyItemDatas.addAll(it.data ?: listOf<ResponseBuyItemData>())
                Log.d(
                    "BuyItem",
                    ((it.data ?: listOf<ResponseBuyItemData>()) as MutableList<ResponseBuyItemData>).toString()
                )
                BuyRecyclerAdapter.notifyDataSetChanged()
            }
        )
    }
}
