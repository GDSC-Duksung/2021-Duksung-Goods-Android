package com.example.duksunggoodsplatform_2021_android.feature.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.api.ApiRetrofitClient
import com.example.duksunggoodsplatform_2021_android.data.response.ResponseSellItemData
import com.example.duksunggoodsplatform_2021_android.dialog.FormDialog
import com.example.duksunggoodsplatform_2021_android.feature.form.ActualFormActivity
import com.example.duksunggoodsplatform_2021_android.model.ResponseEntity
import kotlinx.android.synthetic.main.fragment_mypage_sell.*
import kotlinx.android.synthetic.main.fragment_mypage_sell.view.*
import retrofit2.Call
import retrofit2.Response

class MyPageSellFragment : Fragment() {
    lateinit var SellRecyclerAdapter: SellRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_mypage_sell, container, false)
        val view: View = inflater!!.inflate(R.layout.fragment_mypage_sell, container, false)

        view.btn_mypage_form.setOnClickListener {
            activity?.let {
                val dialog = FormDialog("어떤 폼을 작성하시겠습니까?")
                // 버튼 클릭 이벤트 설정
                dialog.setButtonClickListener(object : FormDialog.OnButtonClickListener {

                    override fun onButton1Clicked() {
                        // finish() 등 팝업창이 닫힐 때 실행되었으면 하는 코드 넣기
                        val intent = Intent(activity, ActualFormActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onButton2Clicked() {
                        // FictitiousFormActivity 추가 후 수정 예정.
                    }
                })
                // dialog.show(myFm, "CustomDialog")
                // val dialog = CustomDialog("fdf")
                dialog.show((activity as AppCompatActivity).supportFragmentManager, "CustomDialog")
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SellRecyclerAdapter = SellRecyclerAdapter(requireActivity())
        rv_mypage_sell.adapter = SellRecyclerAdapter

        initNetwork()
    }

    private fun initNetwork() {
        ApiRetrofitClient.apiService.getSellItem().enqueue(
            object : retrofit2.Callback<ResponseEntity<List<ResponseSellItemData>>> {
                override fun onResponse(
                    call: Call<ResponseEntity<List<ResponseSellItemData>>>,
                    response: Response<ResponseEntity<List<ResponseSellItemData>>>
                ) {
                    SellRecyclerAdapter.sellItemDatas.addAll(response.body()?.data ?: listOf<ResponseSellItemData>())
                    SellRecyclerAdapter.notifyDataSetChanged()
                }

                override fun onFailure(
                    call: Call<ResponseEntity<List<ResponseSellItemData>>>,
                    t: Throwable
                ) {
                    Log.d("MyPageSellFragment", "api fail")
                }
            }
        )
    }
}
