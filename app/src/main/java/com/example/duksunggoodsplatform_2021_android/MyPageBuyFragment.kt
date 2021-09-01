package com.example.duksunggoodsplatform_2021_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.duksunggoodsplatform_2021_android.mypage.BuyData
import com.example.duksunggoodsplatform_2021_android.mypage.BuyRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_mypage_buy.*


class MyPageBuyFragment : Fragment() {


    lateinit var BuyRecyclerAdapter: BuyRecyclerAdapter
    val buy_data = mutableListOf<BuyData>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mypage_buy, container, false)
    }

    override fun onViewCreated(view:View, savedInstanceState:Bundle?){
        super.onViewCreated(view, savedInstanceState)
        BuyRecyclerAdapter = BuyRecyclerAdapter(requireActivity())
        rv_mypage_buy.adapter = BuyRecyclerAdapter

        buy_data.apply{
            add(BuyData("덕성여대 버건디 수건", R.drawable.towel,"1","2021-08-01","제작중"))
            add(BuyData("덕성여대 버건디 수건", R.drawable.towel,"2","2021-08-02","배송중"))
            add(BuyData("덕성여대 버건디 수건", R.drawable.towel,"3","2021-08-03","배송완료"))
            add(BuyData("덕성여대 버건디 수건", R.drawable.towel,"4","2021-08-04","제작중"))
            add(BuyData("덕성여대 버건디 수건", R.drawable.towel,"5","2021-08-05","제작중"))
            add(BuyData("덕성여대 버건디 수건", R.drawable.towel,"6","2021-08-06","제작중"))
            add(BuyData("덕성여대 버건디 수건", R.drawable.towel,"7","2021-08-07","제작중"))
            add(BuyData("덕성여대 버건디 수건", R.drawable.towel,"8","2021-08-08","제작중"))
            add(BuyData("덕성여대 버건디 수건", R.drawable.towel,"7","2021-08-07","제작중"))
            add(BuyData("덕성여대 버건디 수건", R.drawable.towel,"8","2021-08-08","제작중"))
            add(BuyData("덕성여대 버건디 수건", R.drawable.towel,"7","2021-08-07","제작중"))
            add(BuyData("덕성여대 버건디 수건", R.drawable.towel,"8","2021-08-08","제작중"))

            BuyRecyclerAdapter.buyDatas = buy_data
            BuyRecyclerAdapter.notifyDataSetChanged()
        }
    }

}