package com.example.duksunggoodsplatform_2021_android.category

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.viewpager2.widget.ViewPager2
import com.example.duksunggoodsplatform_2021_android.InterestListActivity
import com.example.duksunggoodsplatform_2021_android.LoginActivity
import com.example.duksunggoodsplatform_2021_android.LookUpUserActivity
import com.example.duksunggoodsplatform_2021_android.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_category.*


class CategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters

    val tabTextList = arrayListOf("가수요조사", "실수요조사")

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_category, container, false)
        viewPager = view.findViewById(R.id.pager)
        tabLayout = view.findViewById(R.id.tab_layout)


        // 관심목록 버튼 클릭시

        val starButton = view.findViewById<ImageButton>(R.id.starbutton)


        starButton.setOnClickListener{
            activity?.let{
                val intent = Intent(context, LookUpUserActivity::class.java)
                startActivity(intent)
            }
        }


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val pagerAdapter = PagerFragmentStateAdapter(requireActivity())
        // 6개의 fragment add
        pagerAdapter.addFragment(FictitiousDemandFragment())
        pagerAdapter.addFragment(ActualDemandFragment())

        // adapter
        viewPager.adapter = pagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int){
                super.onPageSelected(position)
                Log.e("ViewPagerFragment", "Page ${position+1}")
            }
        })

        // tablayout attach
        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }

}