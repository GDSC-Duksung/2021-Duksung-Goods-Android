package com.example.duksunggoodsplatform_2021_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_goods_detail.*

class GoodsDetailActivity : AppCompatActivity() {
    val tabTextList = arrayListOf("상품 설명", "상세페이지", "커뮤니티")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)

        detailViewPager.adapter = ScreenSlidePagerAdapter(this)
        detailViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(detailTabLayout, detailViewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }

    override fun onBackPressed() {
        if (detailViewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            detailViewPager.currentItem = detailViewPager.currentItem - 1
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa){
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> GoodsExFragment()
                1 -> GoodsDetailFragment()
                else -> GoodsCommunityFragment()
            }
        }
    }
}