package com.example.duksunggoodsplatform_2021_android.goodsEx

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.duksunggoodsplatform_2021_android.R

class GoodsExImageAdapter(private val imageList: ArrayList<ModelGoodsExImage>, private val mContext: Context): RecyclerView.Adapter<GoodsExImageAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsExImageAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_goods_ex_image, parent, false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPosition = bindingAdapterPosition
                //val img: ModelImageOnly = imageList[curPosition]
            }
        }
    }

    override fun onBindViewHolder(holder: GoodsExImageAdapter.CustomViewHolder, position: Int) {
        Glide.with(mContext).load(imageList[position].img).into(holder.img)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.ivGoodsExImage)
    }

}