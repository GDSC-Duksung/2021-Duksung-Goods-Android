package com.example.duksunggoodsplatform_2021_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_look_up_user.*

class LookUpUserActivity : AppCompatActivity() {

    var ongoinglist = arrayListOf<OngoingItem>(
        OngoingItem("덕성 버건디 수건", "실수요조사"),
        OngoingItem("덕새 그립톡", "가수요조사"),
        OngoingItem("복단이 스티커", "판매종료"),
        OngoingItem("굿즈 이름", "상태")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_look_up_user)

        val adapter = OngoingListAdapter(this, ongoinglist)
        listView.adapter = adapter
    }

}