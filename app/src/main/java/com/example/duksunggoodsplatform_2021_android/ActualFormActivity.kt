package com.example.duksunggoodsplatform_2021_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.duksunggoodsplatform_2021_android.dialog.CustomDialog
import kotlinx.android.synthetic.main.activity_actual_demand_form.*
import kotlinx.android.synthetic.main.popup_dialog.*

class ActualFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actual_demand_form)

        // 뒤로 가기 버튼
        img_back_actual_demand_form.setOnClickListener {
            finish()
        }

        // 폼 제출하기 버튼 활성화
        btn_register_actual_form.setOnClickListener {
            if (et_actual_form_name.text.isNotEmpty() && et_actual_form_phone_number.text.isNotEmpty() && et_actual_form_email.text.isNotEmpty()
                && et_actual_form_address.text.isNotEmpty() && et_actual_form_account.text.isNotEmpty()
                && cb_actual_form_refund1.isChecked && cb_actual_form_refund2.isChecked
            ) {
                val dialog = CustomDialog("실수요조사 폼을 \n 제출하였습니다.")
                // 버튼 클릭 이벤트 설정
                dialog.setButtonClickListener(object : CustomDialog.OnButtonClickListener {
                    override fun onButtonClicked() {
                        // 서버에 데이터 전송
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
}
