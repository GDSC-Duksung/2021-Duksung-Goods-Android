package com.example.duksunggoodsplatform_2021_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.duksunggoodsplatform_2021_android.R
import kotlinx.android.synthetic.main.activity_add_promotion.*

class AddPromotionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_promotion)


        promote_preview_btn.setOnClickListener {
            // 이미지 가져오기
            promoteEx_tv.text = promote_content.text
            promoteEx_period.text = promote_period.text
        }

        promote_btn.setOnClickListener {
            // 미리보기 내용 디비에 저장장
       }
    }
}