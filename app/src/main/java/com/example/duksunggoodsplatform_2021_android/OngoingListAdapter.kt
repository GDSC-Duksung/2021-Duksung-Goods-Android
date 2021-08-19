package com.example.duksunggoodsplatform_2021_android

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView



class OngoingListAdapter (val context: Context, val ongoinglist: ArrayList<OngoingItem>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */
        val view: View = LayoutInflater.from(context).inflate(R.layout.ongoing_item, null)



        /* 위에서 생성된 view를 ongoing_item.xml 파일의 각 View와 연결하는 과정이다. */

        val name = view.findViewById<TextView>(R.id.name)
        val state = view.findViewById<TextView>(R.id.state)


        val ongoingItem = ongoinglist[position]

        name.text = ongoingItem.name
        state.text = ongoingItem.state

        return view
    }

    override fun getItem(position: Int): Any {
        return ongoinglist[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return ongoinglist.size
    }
}

