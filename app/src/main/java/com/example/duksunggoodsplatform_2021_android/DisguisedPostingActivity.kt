package com.example.duksunggoodsplatform_2021_android
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.duksunggoodsplatform_2021_android.dialog.CustomDialog
import kotlinx.android.synthetic.main.activity_disguised_demand_posting.*

class DisguisedPostingActivity : AppCompatActivity() {
    val spinner: Spinner = findViewById(R.id.spinner_actual_posting_category)
    val items = resources.getStringArray(R.array.category_array)
    val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
    var SET_IMAGE = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disguised_demand_posting)

        // Spinner
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.adapter = myAdapter
        spinner.setSelection(3)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when(position) {
                    0 -> {

                    }
                    1 -> {

                    }
                    2 -> {

                    }
                    else -> {

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.d("SpinnerTag", "onNothingSelected")
            }
        }

        // 뒤로가기 버튼, 취소하기 버튼
        img_back_disguised_demand_posting.setOnClickListener{
            finish()
        }

        btn_cancel_disguised_posting.setOnClickListener {
            finish()
        }

        // 등록하기 버튼
        btn_register_disguised_posting.setOnClickListener {
            // 성공
            if(et_disguised_posting_title.text.isNotEmpty() && et_disguised_posting_content.text.isNotEmpty() && SET_IMAGE == true
                && et_disguised_posting_date.text.isNotEmpty() && et_disguised_posting_price.text.isNotEmpty() && cb_disguised_posting.isChecked) {
                val dialog = CustomDialog("가수요조사 포스팅에 \n 성공하였습니다.")
                // 버튼 클릭 이벤트 설정
                dialog.setButtonClickListener(object : CustomDialog.OnButtonClickListener {
                    override fun onButtonClicked() {
                        // 서버에 데이터 전송
                        finish()
                        // 다른 화면 연결
                    }
                })
                dialog.show(supportFragmentManager, "CustomDialog")
            } else{
                // 실패
                val dialog = CustomDialog("가수요조사 포스팅에 \n 실패하였습니다. \n 모든 내용을 입력해주세요. ")
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