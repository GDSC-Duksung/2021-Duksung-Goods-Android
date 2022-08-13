package com.example.duksunggoodsplatform_2021_android.user

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.duksunggoodsplatform_2021_android.api.ApiRetrofitClient
import com.example.duksunggoodsplatform_2021_android.databinding.ActivitySignUpBinding
import com.example.duksunggoodsplatform_2021_android.dialog.CustomDialog
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response

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

            val name = binding.etSignUpName.text.toString()
            val nickname = binding.etSignUpNickname.text.toString()
            val email = binding.etSignUpEmail.text.toString()
            val password = binding.etSignUpPassword.text.toString()
            val address = binding.etSignUpAddress.text.toString()
            val phoneNumber = binding.etSignUpPhoneNumber.text.toString()

            //이름
            if(name.trim().isEmpty()){
                dialogShow("이름을 입력해주세요.", binding.etSignUpName, false)
            }

            //닉네임
            else if(nickname.trim().isEmpty()){
                dialogShow("닉네임을 입력해주세요.", binding.etSignUpNickname, false)
            }

            //이메일 형식 체크
            else if(!emailPattern.matcher(email).matches()){
                dialogShow("이메일 형식에 맞지 않습니다.", binding.etSignUpEmail, false)
            }

/*// TODO : 덕성 이메일 체크 조건은 개발 편의상 주석처리 해 둠
            //이메일이 덕성 이메일인지 체크
            else if(binding.etSignUpEmail.text.toString().split("@")[1] != emailDomain){
                dialogShow("도메인이 @duksung.ac.kr인 덕성 이메일만 가입이 가능합니다.", binding.etSignUpEmail, false)
            }*/

            //비밀번호
            else if(password.trim().isEmpty()){
                dialogShow("비밀번호를 입력해주세요.", binding.etSignUpPassword, false)
            }

            //비밀번호와 비밀번호 확인 일치 체크
            else if(password != binding.etSignUpPasswordCheck.text.toString()){
                dialogShow("비밀번호 확인이 일치하지 않습니다.", binding.etSignUpPassword, false)
            }

            //주소
            else if(address.trim().isEmpty()){
                dialogShow("주소를 입력해주세요.", binding.etSignUpAddress, false)
            }

            //전화번호
            else if(phoneNumber.trim().isEmpty()){
                dialogShow("전화번호를 입력해주세요.", binding.etSignUpPhoneNumber, false)
            }

            //이용약관
            else if(!binding.cbSignUpAgree.isChecked){
                dialogShow("이용약관에 동의해주세요.",null, false)
            }

            //회원가입 요청
            else{
                Toast.makeText(this, "회원가입을 요청 중입니다.", Toast.LENGTH_LONG).show()
                val body = HashMap<String, String>()
                body["name"] = name
                body["nickname"] = nickname
                body["email"] = email
                body["password"] = password
                body["address"] = address
                body["phoneNumber"] = phoneNumber
                Log.d("signup", "name: ${name}, nickname: ${nickname}, email: ${email}, pw: ${password}, addr: ${address}, phone: ${phoneNumber}")
                callPostSignUp(body)

            }
        }

        binding.tvSignUpLogIn.setOnClickListener {
            finish()
        }

        //okhttp interceptor
        //ApiRetrofitClient.interceptor.level = HttpLoggingInterceptor.Level.BODY

    }



    private val userApi = ApiRetrofitClient.apiService

    private fun callPostSignUp(body: HashMap<String, String>) {
        val responseData = MutableLiveData<ModelLoginSignUpResponseData>()

        userApi.postSignUp(body)
            .enqueue(object : retrofit2.Callback<ModelLoginSignUpResponseData> {
                override fun onResponse(
                    call: Call<ModelLoginSignUpResponseData>,
                    response: Response<ModelLoginSignUpResponseData>
                ) {
                    responseData.value = response.body()

                    Log.d("로그signUp---", "통신성공 : ${responseData.value}")
                    val status = responseData.value?.status

                    if(status == "OK"){
                        //dialogShow("회원가입에 성공하였습니다.\n로그인 해주세요.", null, true)
                        dialogShow("이메일주소로 인증메일이 전송되었습니다. \n이메일 인증 후 회원가입이 완료됩니다.", null, true)
                    }
                    //잘못된 요청
                    else if(status == null){
                        Toast.makeText(applicationContext, "잘못된 정보이거나 이미 존재하는 이메일입니다.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Log.e("로그signUp error--", "응답 : ${responseData.value}")
                        Toast.makeText(applicationContext, "비정상적인 오류입니다. 관리자에게 문의하세요. STATUS:${status}", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<ModelLoginSignUpResponseData>, t: Throwable) {
                    Log.e("로그signUp error--", "실패")
                    t.printStackTrace()
                }

            })
    }


    //다이얼로그 띄우는 함수
    private fun dialogShow(msg: String, focus: View?, success: Boolean){ //, mContext: Context
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