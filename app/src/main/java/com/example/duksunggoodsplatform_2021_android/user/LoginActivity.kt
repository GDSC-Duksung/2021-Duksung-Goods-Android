package com.example.duksunggoodsplatform_2021_android.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.duksunggoodsplatform_2021_android.MainActivity
import com.example.duksunggoodsplatform_2021_android.databinding.ActivityLoginBinding
import com.example.duksunggoodsplatform_2021_android.dialog.CustomDialog
import retrofit2.Call
import retrofit2.Response

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString()
            val password = binding.etLoginPasswd.text.toString()

            //이메일 아이디
            if(email.trim().isEmpty()){
                dialogShow("이메일 아이디를 입력해주세요.", binding.etLoginEmail, false)
            }

            //비밀번호
            else if(password.trim().isEmpty()){
                dialogShow("비밀번호를 입력해주세요.", binding.etLoginPasswd, false)
            }

            else{
                // TODO : 서버
                val body = HashMap<String, String>()
                body["email"] = "${email}@duksung.ac.kr" // .put과 같은 의미 []
                body["password"] = password

                callPostLogin(body)
                //Log.d("로그login---", "응답 : ${response}") //안찍힘

            }
        }

        binding.tvLoginSignup.setOnClickListener {
            val signUpIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signUpIntent)
        }
    }

    private val userApi = UserApiRetrofitClient.userApiService

    private fun callPostLogin(body: HashMap<String, String>) {
        val responseData = MutableLiveData<ModelLoginSignUpResponseData>()

        userApi.postLogin(body)
            .enqueue(object : retrofit2.Callback<ModelLoginSignUpResponseData> {
                override fun onResponse(
                    call: Call<ModelLoginSignUpResponseData>,
                    response: Response<ModelLoginSignUpResponseData>
                ) {
                    responseData.value = response.body()

                    Log.d("로그login---", "통신성공 : ${responseData.value}")
                    val status = responseData.value?.status

                    if(status == "OK"){
                        val mainIntent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(mainIntent)
                        Toast.makeText(applicationContext, "~~님 로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                        //TODO : 서버에서 유저id 같은 값 받아와서 설정하기
                    }
                    else if(status == null){
                        Toast.makeText(applicationContext, "이메일/비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Log.e("로그login error--", "응답 : ${responseData.value}")
                        Toast.makeText(applicationContext, "비정상적인 오류입니다. 관리자에게 문의하세요. STATUS:${status}", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<ModelLoginSignUpResponseData>, t: Throwable) {
                    Log.e("로그login error--", "실패")
                    t.printStackTrace()
                }

            })
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