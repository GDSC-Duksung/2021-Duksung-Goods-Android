package com.example.duksunggoodsplatform_2021_android.feature.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.duksunggoodsplatform_2021_android.interest.InterestListActivity
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.user.UserInformationActivity
import com.example.duksunggoodsplatform_2021_android.dialog.CustomDialog
import com.example.duksunggoodsplatform_2021_android.user.LoginActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_mypage.*


class MyPageFragment : Fragment() {

    val tabList = arrayListOf("Buy","Sell")

    private lateinit var mypage_viewpager: ViewPager2
    private lateinit var mypage_tab: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_mypage, container, false)

        val view:View = inflater.inflate(R.layout.fragment_mypage,container,false,)
        mypage_viewpager = view.findViewById(R.id.mypage_viewpager)
        mypage_tab = view.findViewById(R.id.tab_mypage)
        return view
    }

    override fun onActivityCreated(savedInstanceState:Bundle?){
        super.onActivityCreated(savedInstanceState)

        val pagerAdapter =
            PagerAdapter(requireActivity())
        //val pagerAdapter = PagerAdapter(requireActivity())
        pagerAdapter.addFragment(MyPageBuyFragment())
        pagerAdapter.addFragment(MyPageSellFragment())

        //관심목록
        button_star.setOnClickListener {
            activity?.let {
                val intent = Intent(activity, InterestListActivity::class.java)
                startActivity(intent)
            }
        }
        //쪽지보내기
        btn_mypage_message.setOnClickListener {
            activity?.let {
                //val intent = Intent(activity,InterestListActivity::class.java)
                //startActivity(intent)
            }
        }

        //로그아웃.
        mypage_logout.setOnClickListener {
            val dialog = CustomDialog("로그아웃 하시겠습니까?")
            // 버튼 클릭 이벤트 설정
            dialog.setButtonClickListener(object: CustomDialog.OnButtonClickListener{
                override fun onButtonClicked() {
                    //finish() 등 팝업창이 닫힐 때 실행되었으면 하는 코드 넣기
                    activity?.let {
                        val intent = Intent(activity, LoginActivity::class.java)
                        startActivity(intent)
                        it.finish()

                        val sharedPref = it.getSharedPreferences(
                            getString(R.string.shared_preference_user_info), Context.MODE_PRIVATE)
                        val sharedEditor = sharedPref.edit()
                        sharedEditor.putString(getString(R.string.user_token), "")
                        sharedEditor.apply()
                        Log.d("jh", "logout and delete token")
                    }
                }
            })
            dialog.show((activity as AppCompatActivity).supportFragmentManager, "CustomDialog")
        }

        //문의하기
        mypage_question.setOnClickListener {
            activity?.let {
                // val intent = Intent(activity,InterestListActivity::class.java)
                //  startActivity(intent)
            }
        }

        //내정보관리
        tv_mypage_manage.setOnClickListener {
            activity?.let {
                val intent = Intent(activity, UserInformationActivity::class.java)
                startActivity(intent)
            }
        }



        mypage_viewpager.adapter = pagerAdapter
        mypage_viewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int){
                super.onPageSelected(position)
                Log.e("ViewPagerFragment","Page ${position+1}")
            }
        })

        TabLayoutMediator(tab_mypage,mypage_viewpager){
                tab,position -> tab.text = tabList[position]
        }.attach()
    }
}