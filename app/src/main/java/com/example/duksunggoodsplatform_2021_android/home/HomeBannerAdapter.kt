package com.example.duksunggoodsplatform_2021_android.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.duksunggoodsplatform_2021_android.goodsEx.GoodsDetailActivity
import com.example.duksunggoodsplatform_2021_android.R

class HomeBannerAdapter(private val bannerList: ArrayList<ModelHomeBanner>, private val mContext: Context): RecyclerView.Adapter<HomeBannerAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_banner, parent, false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPosition = bindingAdapterPosition
                //val img: ModelImageOnly = imageList[curPosition]
                // TODO : 클릭 시 해당 상품 페이지로 이동하기
                val bannerCount = bannerList.size
                val itemId = bannerList[curPosition%bannerCount].id
                Toast.makeText(mContext, "${curPosition%bannerCount}번째 배너, id=${itemId} 클릭됨", Toast.LENGTH_SHORT).show()

                val intent = Intent(mContext, GoodsDetailActivity::class.java)
                intent.putExtra("itemId", itemId)
//                intent.putExtra("itemId", 1)
                mContext.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: HomeBannerAdapter.CustomViewHolder, position: Int) {
        //받아온 배너 갯수 = k라 하면
        // 아이템 수로 굉장히 큰 수를 줬으므로 position으로 어떤 수가 나오든 k로 나눈 나머지 값 순서의의 데이터를 사해 k단위로 데이터가 반복되도록 한다.
        // 다른 곳에서도 position값은 k로 나눈 나머지를 사용하면 된다.
        val count = bannerList.size
        if(count > 0){ //count=0일 때 실행되어서 오류발생하는 현상 방지
            Glide.with(mContext).load(bannerList[position%count].img).into(holder.img)
            holder.content.text = bannerList[position%count].content
            holder.date.text = bannerList[position%count].date
        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE // 뷰페이저 전환이 무한"처럼" 보이도록 굉장히 큰 억단위 수를 아이템 수로 임의로 넣어줌
    }


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.ivHomeBanner)
        val content: TextView = itemView.findViewById(R.id.tvHomeBannerContent)
        val date: TextView = itemView.findViewById(R.id.tvHomeBannerDate)
    }

}