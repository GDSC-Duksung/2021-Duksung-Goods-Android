package com.example.duksunggoodsplatform_2021_android.feature.mypage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    var fragments:ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {

        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {

        return fragments[position]
    }
    fun addFragment(fragment: Fragment){
        fragments.add(fragment)
        notifyItemInserted(fragments.size-1)
    }
    fun removeFragment(){
        fragments.removeLast()
        notifyItemRemoved(fragments.size)
    }

}