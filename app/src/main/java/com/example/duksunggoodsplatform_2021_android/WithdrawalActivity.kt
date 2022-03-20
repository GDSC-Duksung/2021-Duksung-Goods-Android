package com.example.duksunggoodsplatform_2021_android

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.duksunggoodsplatform_2021_android.databinding.ActivityWithdrawalBinding
import com.example.duksunggoodsplatform_2021_android.dialog.CustomDialog
import com.example.duksunggoodsplatform_2021_android.user.LoginActivity
import kotlinx.android.synthetic.main.activity_withdrawal.*


class WithdrawalActivity:AppCompatActivity() {

    private lateinit var binding: ActivityWithdrawalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithdrawalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //뒤로가기버튼
        binding.ivWithdrawalBack.setOnClickListener {
            finish()
        }

        //회원탈퇴 버튼 클릭
        binding.btnWithdrawal.setOnClickListener{
            if(!checkbox_withdrawal.isChecked){
                Information_Dialog("약관에 동의해 주세요",binding.checkboxWithdrawal)
            }
            else if(checkbox_withdrawal.isChecked){
                Information_Dialog("회원탈퇴가 \n완료되었습니다.",null)
                //TODO 로그인 창으로 가기?

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
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                }
                focus?.requestFocus()
            }
        })
        dialog.show(supportFragmentManager, "CustomDialog")
    }
}