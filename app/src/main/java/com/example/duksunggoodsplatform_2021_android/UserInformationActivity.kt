package com.example.duksunggoodsplatform_2021_android

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.duksunggoodsplatform_2021_android.databinding.ActivityUserInformationBinding
import com.example.duksunggoodsplatform_2021_android.dialog.CustomDialog


class UserInformationActivity: AppCompatActivity() {
    private lateinit var binding: ActivityUserInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInformationBinding.inflate(layoutInflater)
        val view = binding.root
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS
        //setContentView(R.layout.activity_user_information)
        setContentView(view)

        //뒤로가기 버튼
        binding.ivUserInformationBack.setOnClickListener{
            finish()
        }

        //회원탈퇴 버튼
        binding.btnUserInformationWithdrawal.setOnClickListener {
            val intent = Intent(applicationContext, WithdrawalActivity::class.java)
            startActivity(intent)
        }

        //회원정보 수정 완료 버튼
        binding.btnUserInformaionOk.setOnClickListener {
            if(binding.userInformationName.text.toString().trim()==""){
                Information_Dialog("이름을 입력해주세요",binding.userInformationName)
            }
            else if(binding.userInformationNickname.text.toString().trim()==""){
                Information_Dialog("닉네임을 입력해주세요",binding.userInformationNickname)
            }
            else if(binding.userInformationPhone.text.toString().trim()==""){
                Information_Dialog("휴대폰을 입력해주세요",binding.userInformationPhone)
            }
            /*
            else if(binding.userInformationEmail.text.toString().trim()==""){
                Information_Dialog("이메일을 입력해주세요",binding.userInformationEmail)
            }
             */
            else if(!emailPattern.matcher(binding.userInformationEmail.text).matches()){
                //이메일 형식 체크
                Information_Dialog("이메일 형식에 맞지 않습니다.",binding.userInformationEmail)
            }
            else if(binding.userInformationAddress.text.toString().trim()==""){
                Information_Dialog("주소를 입력해주세요",binding.userInformationAddress)
            }
            else{
                //아무 이상 없으면
                Information_Dialog("회원정보 수정이\n 완료되었습니다.",null)
            }
        }
    }

    fun Information_Dialog(str:String,focus: View?){
        val dialog = CustomDialog(str)
        // 버튼 클릭 이벤트 설정
        dialog.setButtonClickListener(object: CustomDialog.OnButtonClickListener{
            override fun onButtonClicked() {
                //finish() 등 팝업창이 닫힐 때 실행되었으면 하는 코드 넣기
                //finish()

                if(focus == null){
                    finish()
                }
                focus?.requestFocus()
            }
        })
        dialog.show(supportFragmentManager, "CustomDialog")
    }
}