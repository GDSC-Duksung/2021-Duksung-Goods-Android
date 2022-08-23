package com.example.duksunggoodsplatform_2021_android

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.duksunggoodsplatform_2021_android.api.ApiRetrofitClient
import com.example.duksunggoodsplatform_2021_android.api.MyApplication
import com.example.duksunggoodsplatform_2021_android.category.CategoryFragment
import com.example.duksunggoodsplatform_2021_android.data.local.SharedPreferenceController
import com.example.duksunggoodsplatform_2021_android.databinding.ActivityMainBinding
import com.example.duksunggoodsplatform_2021_android.feature.mypage.MyPageFragment
import com.example.duksunggoodsplatform_2021_android.home.HomeFragment
import com.example.duksunggoodsplatform_2021_android.user.LoginActivity
import com.example.duksunggoodsplatform_2021_android.user.ModelLoginSignUpResponseData
import retrofit2.Call
import retrofit2.Response

//네비게이션 Activity로 사용

private const val TAG_CATEGORY = "category_fragment"
private const val TAG_HOME = "home_fragment"
private const val TAG_MY_PAGE = "my_page_fragment"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        checkLoginAndRefresh()


        //맨 처음 보여줄 프래그먼트 설정
        setFragment(TAG_HOME, HomeFragment())

        //네비 항목 클릭 시 프래그먼트 변경하는 함수 호출
        // TODO : setOnNavigationItemSelectedListener가 deprecated되어서 대체했는데 setOnItemReselectedListener 는 뭐가 다른 거지?
        binding.mainNavi.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.categoryFragment -> setFragment(TAG_CATEGORY, CategoryFragment())
                R.id.homeFragment -> setFragment(TAG_HOME, HomeFragment())
                R.id.myPageFragment -> setFragment(TAG_MY_PAGE, MyPageFragment())
            }
            true
        }

/*        //프래그먼트 설정
        val resultFragmentId = intent.getIntExtra("selectFragmentId", 0)
        binding.mainNavi.selectedItemId = resultFragmentId*/

    }


    //프래그먼트 컨트롤 함수
    fun setFragment(tag: String, fragment: Fragment){
        val manager: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()

        //트랜잭션에 tag로 전달된 fragment가 없을 경우 add
        if(manager.findFragmentByTag(tag) == null){
            ft.add(R.id.mainNaviFragmentContainer, fragment, tag)
        }

        //작업이 수월하도록 manager에 add되어있는 fragment들을 변수로 할당해둠
        val category = manager.findFragmentByTag(TAG_CATEGORY)
        val home = manager.findFragmentByTag(TAG_HOME)
        val myPage = manager.findFragmentByTag(TAG_MY_PAGE)

        //모든 프래그먼트 hide
        if(category!=null){
            ft.hide(category)
        }
        if(home!=null){
            ft.hide(home)
        }
        if(myPage!=null){
            ft.hide(myPage)
        }

        //선택한 항목에 따라 그에 맞는 프래그먼트만 show
        if(tag == TAG_CATEGORY){
            if(category!=null){
                ft.show(category)
            }
        }
        else if(tag == TAG_HOME){
            if(home!=null){
                ft.show(home)
            }
        }
        else if(tag == TAG_MY_PAGE){
            if(myPage!=null){
                ft.show(myPage)
            }
        }

        //마무리
        ft.commitAllowingStateLoss()
        //ft.commit()
    }//seFragment함수 끝

    private fun checkLoginAndRefresh() {
        //로그인 여부 확인
        var token = SharedPreferenceController.getUserToken(MyApplication.applicationContext())
        Log.d("jh", "getUserToken for check login : ${token}")

        //토큰 없음. 로그인 화면 띄움
        if(token.isEmpty()){
            needLogin()
        }
        //토큰 있음. but 이 토큰이 만료된 건지 아닌지 알 수 없음
        //토큰 refresh 요청
        else{
            callRefresh()
        }

    }


    private fun needLogin() {
        Toast.makeText(MyApplication.applicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
        val loginIntent = Intent(MyApplication.applicationContext(), LoginActivity::class.java)
        startActivity(loginIntent)
        finish()
    }

    private val userApi = ApiRetrofitClient.apiService
    private fun callRefresh(){
        userApi.getRefresh()
            .enqueue(object : retrofit2.Callback<ModelLoginSignUpResponseData> {
                override fun onResponse(
                    call: Call<ModelLoginSignUpResponseData>,
                    response: Response<ModelLoginSignUpResponseData>
                ) {
                    if(response.isSuccessful){
                        //refresh 성공. 토큰값 저장
                        val userToken = response.body()?.data //토큰 받아옴
                        Log.d("jh","refreshed token : ${userToken}")
                        val sharedPref = getSharedPreferences(
                            getString(R.string.shared_preference_user_info), Context.MODE_PRIVATE)
                        with (sharedPref.edit()) {
                            putString(getString(R.string.user_token), userToken)
                            apply()
                            Log.d("jh","refreshed token is applied")
                        }
                    }
                    else{
                        when (response.code()){
                            500 -> { //만료된 토큰으로 예상됨. 재로그인 필요
                                needLogin()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ModelLoginSignUpResponseData>, t: Throwable) {
                    Log.d("jh", "callRefresh fail")
                }
            })
    }

}