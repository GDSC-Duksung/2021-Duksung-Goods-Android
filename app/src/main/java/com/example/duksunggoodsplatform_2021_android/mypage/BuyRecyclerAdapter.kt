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
import com.example.duksunggoodsplatform_2021_android.data.response.ItemBuyInfo
import com.example.duksunggoodsplatform_2021_android.data.response.ResponseBuyItemData

class BuyRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<BuyRecyclerAdapter.ViewHolder>() {

    var buyDatas = mutableListOf<ItemBuyInfo>()
    var buyItemDatas = mutableListOf<ResponseBuyItemData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_mypage_buy, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = buyDatas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(buyDatas[position], buyItemDatas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val buyName: TextView = itemView.findViewById<TextView>(R.id.tv_mypage_buy_name)
        val buyCount: TextView = itemView.findViewById<TextView>(R.id.tv_mypage_buy_number)
        val buyDate: TextView = itemView.findViewById<TextView>(R.id.tv_mypage_buy_date)
        val buyImage: ImageView = itemView.findViewById<ImageView>(R.id.img_mypage_buy)
        private val buyState: ImageView = itemView.findViewById<ImageView>(R.id.img_mypage_state)
        private val buyState_Name: TextView = itemView.findViewById<TextView>(R.id.tv_mypage_buy_state)

        fun bind(itemData: ItemBuyInfo, responseBuyItemData: ResponseBuyItemData) {
            buyName.text = itemData.title
            buyCount.text = responseBuyItemData.count.toString()
            buyDate.text = responseBuyItemData.createdAt
            // buyState_Name.text = item.state
            Glide.with(itemView).load(itemData.image).into(buyImage)

            if (buyState_Name.text.equals("제작중")) {
                buyState.setImageResource(R.drawable.mypage_complete)
            }

            if (buyState_Name.text.equals("배송중")) {
                buyState.setImageResource(R.drawable.mypage_delivery)
            }

            if (buyState_Name.text.equals("배송완료")) {
                buyState.setImageResource(R.drawable.mypage_delivery_complete)
            }
        }
    }
}
