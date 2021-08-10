package com.example.duksunggoodsplatform_2021_android.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.duksunggoodsplatform_2021_android.GoodsDetailActivity
import com.example.duksunggoodsplatform_2021_android.R

class HomeBannerAdapter(private val imageList: ArrayList<ModelImageOnly>, private val mContext: Context): RecyclerView.Adapter<HomeBannerAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_banner, parent, false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPosition = bindingAdapterPosition
                //val img: ModelImageOnly = imageList[curPosition]
                // TODO : 클릭 시 해당 상품 페이지로 이동하기
                Toast.makeText(mContext, "${curPosition%5}번째 배너 클릭됨", Toast.LENGTH_SHORT).show()
                val intent = Intent(mContext, GoodsDetailActivity::class.java)
                intent.putExtra("goodsId", "어떤 상품인지 구분할 정보")
                mContext.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: HomeBannerAdapter.CustomViewHolder, position: Int) {
        // 아이템 수로 굉장히 큰 수를 줬으므로 position으로 어떤 수가 나오든 5로 나눈 나머지 값 순서의의 데이터를 사해 5단위로 데이터가 반복되도록 한다.
        // 다른 곳에서도 position값은 5로 나눈 나머지를 사용하면 된다.
        Glide.with(mContext).load(imageList[position%5].img).into(holder.img)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE // 뷰페이저 전환이 무한"처럼" 보이도록 굉장히 큰 억단위 수를 아이템 수로 임의로 넣어줌
    }


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.ivHomeBanner)
    }

}