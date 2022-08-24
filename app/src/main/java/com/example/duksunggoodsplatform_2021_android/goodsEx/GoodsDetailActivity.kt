package com.example.duksunggoodsplatform_2021_android.goodsEx

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.community.GoodsCommunityFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_goods_detail.*

class GoodsDetailActivity : AppCompatActivity() {
    val tabTextList = arrayListOf("상품 설명", "상세페이지", "커뮤니티")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)

        detailViewPager.adapter = ScreenSlidePagerAdapter(this)
        detailViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        detailViewPager.setUserInputEnabled(false)

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
            val itemId = intent.getIntExtra("itemId", -1)
            val bundle = Bundle()
            bundle.putInt("itemId", itemId)
//            Log.d("jh GoodsDetailActivity", "itemId: ${itemId}")

            return when(position) {
                0 -> {
                    val goodsExFragment = GoodsExFragment()
                    goodsExFragment.arguments = bundle
                    goodsExFragment
                }
                1 -> {
                    val goodsExFragment = GoodsDetailFragment()
                    goodsExFragment.arguments = bundle
                    goodsExFragment
                }
                else -> {
                    val goodsExFragment = GoodsCommunityFragment()
                    goodsExFragment.arguments = bundle
                    goodsExFragment
                }
            }
        }
    }
}