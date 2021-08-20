package com.example.duksunggoodsplatform_2021_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CommentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        // 만약 작성자 아이디와 일치한다면, 수정하기 및 삭제하기 버튼 보이게
        // 처음 댓글 액티비티에 들어왔을 때, 디비에서 댓글리스트 가져와서 어댑터와 연결해서 보이게
        // 댓글들 중 댓글 작성자 아이디와 일치한다면, 삭제하기 버튼 보이게
        // 댓글 입력하고 완료 버튼 누르면, Comment라는 데이터 클래스로 댓글리스트에 추가해서 notify해서 업데이트 되게
        // Comment 객체 디비에도 추가되게
    }
}