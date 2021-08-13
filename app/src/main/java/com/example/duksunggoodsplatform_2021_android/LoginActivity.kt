package com.example.duksunggoodsplatform_2021_android

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.duksunggoodsplatform_2021_android.databinding.ActivityLoginBinding
import com.example.duksunggoodsplatform_2021_android.dialog.CustomDialog

class LoginActivity:AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLogin.setOnClickListener {
            //이메일 아이디
            if(binding.etLoginEmail.text.toString().trim().isEmpty()){
                dialogShow("이메일 아이디를 입력해주세요.", binding.etLoginEmail, false)
            }

            //비밀번호
            else if(binding.etLoginPasswd.text.toString().trim().isEmpty()){
                dialogShow("비밀번호를 입력해주세요.", binding.etLoginPasswd, false)
            }

            else{
                // TODO : 서버

                //로그인 성공
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
            }
        }

        binding.tvLoginSignup.setOnClickListener {
            val signUpIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signUpIntent)
        }
    }

    //다이얼로그 띄우는 함수
    private fun dialogShow(msg: String, focus: View?, success: Boolean){
        val dialog = CustomDialog(msg)
        // 버튼 클릭 이벤트 설정
        dialog.setButtonClickListener(object: CustomDialog.OnButtonClickListener{
            override fun onButtonClicked() {
                //팝업창이 닫힐 때 실행되었으면 하는 코드 넣기 - 포커스 이동
                focus?.requestFocus()
                if(success){
                    finish()
                }
            }
        })
        dialog.show(supportFragmentManager, "CustomDialog")
    }
}