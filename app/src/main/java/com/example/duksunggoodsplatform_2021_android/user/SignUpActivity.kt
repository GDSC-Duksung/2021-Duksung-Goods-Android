package com.example.duksunggoodsplatform_2021_android.user

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.duksunggoodsplatform_2021_android.databinding.ActivitySignUpBinding
import com.example.duksunggoodsplatform_2021_android.dialog.CustomDialog

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.ivSignUpBack.setOnClickListener { finish() }

        binding.btnSignUp.setOnClickListener {
            val emailPattern = android.util.Patterns.EMAIL_ADDRESS
            val emailDomain = "duksung.ac.kr"


            //이름
            if(binding.etSignUpName.text.toString().trim().isEmpty()){
                dialogShow("이름을 입력해주세요.", binding.etSignUpName, false)
            }

            //닉네임
            else if(binding.etSignUpNickname.text.toString().trim().isEmpty()){
                dialogShow("닉네임을 입력해주세요.", binding.etSignUpNickname, false)
            }

            //이메일 형식 체크
            else if(!emailPattern.matcher(binding.etSignUpEmail.text).matches()){
                dialogShow("이메일 형식에 맞지 않습니다.", binding.etSignUpEmail, false)
            }

/*// TODO : 덕성 이메일 체크 조건은 개발 편의상 주석처리 해 둠
            //이메일이 덕성 이메일인지 체크
            else if(binding.etSignUpEmail.text.toString().split("@")[1] != emailDomain){
                dialogShow("도메인이 @duksung.ac.kr인 덕성 이메일만 가입이 가능합니다.", binding.etSignUpEmail, false)
            }*/

            //비밀번호
            else if(binding.etSignUpPassword.text.toString().trim().isEmpty()){
                dialogShow("비밀번호를 입력해주세요.", binding.etSignUpPassword, false)
            }

            //비밀번호와 비밀번호 확인 일치 체크
            else if(binding.etSignUpPassword.text.toString() != binding.etSignUpPasswordCheck.text.toString()){
                dialogShow("비밀번호 확인이 일치하지 않습니다.", binding.etSignUpPassword, false)
            }

            //이용약관
            else if(!binding.cbSignUpAgree.isChecked){
                dialogShow("이용약관에 동의해주세요.",null, false)
            }

            //회원가입 성공
            else{

                dialogShow("회원가입에 성공하였습니다.\n로그인 해주세요.", null, true)
            }
        }

        binding.tvSignUpLogIn.setOnClickListener {
            finish()
        }
    }




    //다이얼로그 띄우는 함수
    private fun dialogShow(msg: String, focus: View?, success: Boolean){
        val dialog = CustomDialog(msg)
        // 버튼 클릭 이벤트 설정
        dialog.setButtonClickListener(object: CustomDialog.OnButtonClickListener {
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