package com.example.duksunggoodsplatform_2021_android.feature.form

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.api.ApiRetrofitClient
import com.example.duksunggoodsplatform_2021_android.data.request.RequestFormData
import com.example.duksunggoodsplatform_2021_android.data.response.ResponseFormData
import kotlinx.android.synthetic.main.activity_actual_demand_form.*
import kotlinx.android.synthetic.main.activity_fictitious_demand_form.*
import kotlinx.android.synthetic.main.activity_fictitious_demand_form.textView11
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDateTime

class FictitiousFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fictitious_demand_form)

        val countUpButton: ImageButton = findViewById(R.id.countUpButton)
        val countDownButton: ImageButton = findViewById(R.id.countDownButton)
        val txtCount: TextView = findViewById(R.id.count)
        var count = 1

        countUpButton.setOnClickListener {
            count ++
            txtCount.setText(count.toString())
        }

        countDownButton.setOnClickListener {
            count --
            txtCount.setText(count.toString())
            if (count <2)
                count = 1
        }

        textView11.text = intent.getStringExtra("name") + " 가수요조사"
        goods_detail_name.text = intent.getStringExtra("name")
        val dateAndtime: LocalDateTime = LocalDateTime.now()

        val requestFoamData = RequestFormData(
            address = "",
            count = count,
            createdAt = dateAndtime.toString(),
            email = "",
            extra = "",
            name = "",
            phoneNumber = "",
            refundAccountNumber = "",
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
                    Log.d("FictitiousFormActivity", "실수요조사 폼 데이터 전송 성공")
                }
                override fun onFailure(
                    call: Call<ResponseFormData>,
                    t: Throwable
                ) {
                    Log.d("FictitiousFormActivity", "api fail")
                }
            }
        )
    }
}
