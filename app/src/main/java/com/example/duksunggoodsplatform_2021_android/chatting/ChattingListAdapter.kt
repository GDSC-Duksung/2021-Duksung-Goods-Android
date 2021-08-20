package com.example.duksunggoodsplatform_2021_android.chatting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.duksunggoodsplatform_2021_android.R

class ChattingListAdapter (val context: Context, val chatList: ArrayList<ChattingHomeItem>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */
        val view: View = LayoutInflater.from(context).inflate(R.layout.chatting_home_item, null)

        /* 위에서 생성된 view를 res-layout-main_lv_item.xml 파일의 각 View와 연결하는 과정이다. */
        val userPhoto = view.findViewById<ImageView>(R.id.userPhoto)
        val userName = view.findViewById<TextView>(R.id.userName)
        val content = view.findViewById<TextView>(R.id.content)
        val date = view.findViewById<TextView>(R.id.date)

        /* ArrayList<Dog>의 변수 dog의 이미지와 데이터를 ImageView와 TextView에 담는다. */
        val chatItem = chatList[position]
        val resourceId = context.resources.getIdentifier(chatItem.photo.toString(), "drawable", context.packageName)
        userPhoto.setImageResource(resourceId)
        userName.text = chatItem.name
        content.text = chatItem.content
        date.text = chatItem.date

        return view
    }

    override fun getItem(position: Int): Any {
        return chatList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return chatList.size
    }
}