package com.example.duksunggoodsplatform_2021_android.feature.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.dialog.FormDialog
import com.example.duksunggoodsplatform_2021_android.feature.form.ActualFormActivity
import com.example.duksunggoodsplatform_2021_android.mypage.SellData
import com.example.duksunggoodsplatform_2021_android.mypage.SellRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_mypage_sell.*
import kotlinx.android.synthetic.main.fragment_mypage_sell.view.*


class MyPageSellFragment : Fragment() {
    lateinit var SellRecyclerAdapter: SellRecyclerAdapter
    val sell_data = mutableListOf<SellData>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_mypage_sell, container, false)
        val view:View = inflater!!.inflate(R.layout.fragment_mypage_sell,container,false)

        view.btn_mypage_form.setOnClickListener {
            activity?.let{
                val dialog = FormDialog("어떤 폼을 작성하시겠습니까?")
                // 버튼 클릭 이벤트 설정
                dialog.setButtonClickListener(object: FormDialog.OnButtonClickListener{

                    override fun onButton1Clicked() {
                        //finish() 등 팝업창이 닫힐 때 실행되었으면 하는 코드 넣기
                        val intent = Intent(activity, ActualFormActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onButton2Clicked() {
                        //FictitiousFormActivity 추가 후 수정 예정.
                    }
                })
                //dialog.show(myFm, "CustomDialog")
                //val dialog = CustomDialog("fdf")
                dialog.show((activity as AppCompatActivity).supportFragmentManager, "CustomDialog")
            }

        }



        return view
    }

    override fun onViewCreated(view:View, savedInstanceState:Bundle?){
        super.onViewCreated(view, savedInstanceState)
        SellRecyclerAdapter = SellRecyclerAdapter(requireActivity())
        rv_mypage_sell.adapter = SellRecyclerAdapter


        sell_data.apply{
            add(SellData(1, name="덕성여대 버건디 수건","홍보 뷰 등록","입금자 목록","21%","가수요조사", R.drawable.towel))
            add(SellData(2, name="덕성여대 버건디 수건","홍보 뷰 등록","입금자 목록","22%","가수요조사", R.drawable.towel))
            add(SellData(3, name="덕성여대 버건디 수건","홍보 뷰 등록","입금자 목록","23%","가수요조사", R.drawable.towel))
            add(SellData(4, name="덕성여대 버건디 수건","홍보 뷰 등록","입금자 목록","24%","가수요조사", R.drawable.towel))
            add(SellData(5, name="덕성여대 버건디 수건","홍보 뷰 등록","입금자 목록","25%","가수요조사", R.drawable.towel))
            add(SellData(6, name="덕성여대 버건디 수건","홍보 뷰 등록","입금자 목록","26%","가수요조사", R.drawable.towel))
            add(SellData(7, name="덕성여대 버건디 수건","홍보 뷰 등록","입금자 목록","25%","가수요조사", R.drawable.towel))
            add(SellData(8, name="덕성여대 버건디 수건","홍보 뷰 등록","입금자 목록","26%","가수요조사", R.drawable.towel))
            SellRecyclerAdapter.sellDatas = sell_data
            SellRecyclerAdapter.notifyDataSetChanged()
        }
    }


}
