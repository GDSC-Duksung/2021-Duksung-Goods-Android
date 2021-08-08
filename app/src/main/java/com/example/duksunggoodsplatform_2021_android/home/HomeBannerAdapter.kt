package com.example.duksunggoodsplatform_2021_android.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.duksunggoodsplatform_2021_android.R

class HomeBannerAdapter(private val imageList: ArrayList<ModelImageOnly>, private val mContext: Context): RecyclerView.Adapter<HomeBannerAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_banner, parent, false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPosition = bindingAdapterPosition
                //val img: ModelImageOnly = imageList[curPosition]
                // TODO : 클릭 시 해당 상품 페이지로 이동하기
                Toast.makeText(mContext, "${curPosition}번째 배너 클릭됨", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBindViewHolder(holder: HomeBannerAdapter.CustomViewHolder, position: Int) {
        Glide.with(mContext).load(imageList[position].img).into(holder.img)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.ivHomeBanner)
    }
}