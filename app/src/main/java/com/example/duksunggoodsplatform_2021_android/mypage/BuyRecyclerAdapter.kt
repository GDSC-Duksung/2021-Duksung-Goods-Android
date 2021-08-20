package com.example.duksunggoodsplatform_2021_android.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.duksunggoodsplatform_2021_android.R

class BuyRecyclerAdapter(private val context: Context): RecyclerView.Adapter<BuyRecyclerAdapter.ViewHolder>() {

    var buyDatas = mutableListOf<BuyData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_mypage_buy,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = buyDatas.size
/*
    override fun getItemViewType(position:Int): Int {
        return position;
    }
*/

    override fun onBindViewHolder(holder:ViewHolder,position:Int){
        holder.bind(buyDatas[position])
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val buyName: TextView = itemView.findViewById<TextView>(R.id.tv_mypage_buy_name)
        val buyCount: TextView = itemView.findViewById<TextView>(R.id.tv_mypage_buy_number)
        val buyDate:TextView = itemView.findViewById<TextView>(R.id.tv_mypage_buy_date)
        val buyImage: ImageView = itemView.findViewById<ImageView>(R.id.img_mypage_buy)

        fun bind(item:BuyData){
            buyName.text = item.name
            buyCount.text = item.count
            buyDate.text = item.date
            Glide.with(itemView).load(item.photo).into(buyImage)
        }
    }
}