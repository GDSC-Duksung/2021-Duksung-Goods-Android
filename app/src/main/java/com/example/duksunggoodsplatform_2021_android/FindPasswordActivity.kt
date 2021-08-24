package com.example.duksunggoodsplatform_2021_android

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.duksunggoodsplatform_2021_android.databinding.ActivityFindPasswordBinding
import com.example.duksunggoodsplatform_2021_android.dialog.CustomDialog


class FindPasswordActivity: AppCompatActivity() {

    private lateinit var binding: ActivityFindPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS

        setContentView(view)
        //setContentView(R.layout.activity_find_password)

        //뒤로가기 버튼
        binding.ivFindPasswordBack.setOnClickListener {
            finish()
        }

        //인증 버튼
        binding.btnFindPasswordCertification.setOnClickListener {
            if(!emailPattern.matcher(binding.etFindPasswordEmail.text).matches()){
                //이메일 형식 체크
                Information_Dialog("이메일 형식에\n 맞지 않습니다.",binding.etFindPasswordEmail)
            }
            else{
                Information_Dialog("인증코드가\n 전송되었습니다.",binding.etFindPasswordCode)
            }
        }

        //확인 버튼
        binding.btnFindPasswordCheck.setOnClickListener {
            //TODO 인증 번호 코드 ..
            val codenumber = "123"
            //일치하면
            if(binding.etFindPasswordCode.text.toString().trim() == codenumber){
                Information_Dialog("인증이\n 완료되었습니다.",binding.btnFindPasswordCheck)
            }
            else{
                Information_Dialog("인증 코드가\n 일치하지 않습니다.",binding.etFindPasswordCode)
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

                if(focus == binding.btnFindPasswordCheck){
                    //TODO 원하는 비밀번호로 재설정하는 창으로 수정하기.
                    val intent = Intent(applicationContext, SignUpActivity::class.java)
                    startActivity(intent)
                }
                focus?.requestFocus()
            }
        })
        dialog.show(supportFragmentManager, "CustomDialog")
    }
}