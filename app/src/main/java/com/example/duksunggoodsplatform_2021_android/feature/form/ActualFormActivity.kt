package com.example.duksunggoodsplatform_2021_android.feature.form

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.api.ApiRetrofitClient
import com.example.duksunggoodsplatform_2021_android.data.request.RequestFormData
import com.example.duksunggoodsplatform_2021_android.data.response.ResponseFormData
import com.example.duksunggoodsplatform_2021_android.dialog.CustomDialog
import kotlinx.android.synthetic.main.activity_actual_demand_form.*
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDateTime

class ActualFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actual_demand_form)

        // 뒤로 가기 버튼
        img_back_actual_demand_form.setOnClickListener {
            finish()
        }

        //데이터 가져오기
        tv_actual_form_product_name.text = intent.getStringExtra("name")
        Glide.with(this).load(intent.getStringExtra("image")).into(img_actual_form_product)
        tv_actual_form_product_price.text = intent.getIntExtra("price", 0).toString()

        // 폼 제출하기 버튼 활성화
        btn_register_actual_form.setOnClickListener {
            if (et_actual_form_name.text.isNotEmpty() && et_actual_form_phone_number.text.isNotEmpty() && et_actual_form_email.text.isNotEmpty() &&
                et_actual_form_address.text.isNotEmpty() && et_actual_form_account.text.isNotEmpty() &&
                cb_actual_form_refund1.isChecked && cb_actual_form_refund2.isChecked
            ) {
                val dialog = CustomDialog("실수요조사 폼을 \n 제출하였습니다.")
                // 버튼 클릭 이벤트 설정
                dialog.setButtonClickListener(object : CustomDialog.OnButtonClickListener {
                    val dateAndtime: LocalDateTime = LocalDateTime.now()
                    override fun onButtonClicked() {
                        val requestFoamData = RequestFormData(
                            address = et_actual_form_address.toString(),
                            count = 0,
                            createdAt = dateAndtime.toString(),
                            email = et_actual_form_email.toString(),
                            extra = et_actual_form_delivery.toString(),
                            name = et_actual_form_name.toString(),
                            phoneNumber = et_actual_form_phone_number.toString(),
                            refundAccountNumber = et_actual_form_account.toString(),
                            zipCode = 0
                        )
                        // 서버에 데이터 전송
                        ApiRetrofitClient.apiService.postForm(
                            itemId = intent.getIntExtra("id", 0),
                            requestFoamData
                        ).enqueue(
                            object : retrofit2.Callback<ResponseFormData> {
                                override fun onResponse(
                                    call: Call<ResponseFormData>,
                                    response: Response<ResponseFormData>
                                ) {
                                    Log.d("ActualFormActivity", "실수요조사 폼 데이터 전송 성공")
                                }
                                override fun onFailure(
                                    call: Call<ResponseFormData>,
                                    t: Throwable
                                ) {
                                    Log.d("ActualFormActivity", "api fail")
                                }
                            }
                        )
                        finish()
                        // 다른 화면으로 연결
                    }
                })
                dialog.show(supportFragmentManager, "CustomDialog")
            } else {
                // 실패
                val dialog = CustomDialog("실수요조사 폼 제출에 \n 실패하였습니다. \n 필수 내용을 모두 입력해주세요. ")
                // 버튼 클릭 이벤트 설정
                dialog.setButtonClickListener(object : CustomDialog.OnButtonClickListener {
                    override fun onButtonClicked() {
                        dialog.dismiss()
                    }
                })
                dialog.show(supportFragmentManager, "CustomDialog")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


    }
}
