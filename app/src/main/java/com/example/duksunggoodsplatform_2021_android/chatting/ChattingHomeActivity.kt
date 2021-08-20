package com.example.duksunggoodsplatform_2021_android.chatting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.duksunggoodsplatform_2021_android.R
import kotlinx.android.synthetic.main.activity_chatting_home.*

class ChattingHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting_home)
        val chatAdapter = ChattingListAdapter(this, chatList)
        chatListView.adapter = chatAdapter
    }

    var chatList = arrayListOf<ChattingHomeItem>(
        ChattingHomeItem (R.drawable.towel, "닉네임", "상품 문의 드립니다.", "7월 15일"),
        ChattingHomeItem (R.drawable.towel, "덕성 버건디 수건 구매자", "안녕하세요. 덕성 버건디 수건 판매자입니다.", "7월 15일")
    )

}