package com.example.duksunggoodsplatform_2021_android.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.duksunggoodsplatform_2021_android.GoodsDetailActivity
import com.example.duksunggoodsplatform_2021_android.R

class HomeSectionAdapter(private val sectionItemList: ArrayList<ModelImageText>, private val mContext: Context): RecyclerView.Adapter<HomeSectionAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_section, parent, false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPosition = bindingAdapterPosition
                // TODO : 클릭 시 해당 상품 페이지로 이동하기
                Toast.makeText(mContext, "${curPosition}번째 아이템 \"${sectionItemList[curPosition].label}\" 클릭됨", Toast.LENGTH_SHORT).show()
                val intent = Intent(mContext, GoodsDetailActivity::class.java)
                intent.putExtra("goodsId", "어떤 상품인지 구분할 정보")
                mContext.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        Glide.with(mContext).load(sectionItemList[position].img).into(holder.img)
        holder.label.text = sectionItemList[position].label
    }

    override fun getItemCount(): Int {
        return sectionItemList.size
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.ivHomeSectionImage)
        val label: TextView = itemView.findViewById(R.id.tvHomeSectionLabel)

    }

}