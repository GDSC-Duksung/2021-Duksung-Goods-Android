package com.example.duksunggoodsplatform_2021_android.interest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.interest.data.InterestViewModel
import java.text.DecimalFormat

class InterestRecyclerViewAdapter(private val mContext: Context, private val liveData: InterestViewModel): RecyclerView.Adapter<InterestRecyclerViewAdapter.CustomViewHolder>() {

    var datas = mutableListOf<ModelInterestItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestRecyclerViewAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.interest_item_view, parent, false)
        return CustomViewHolder(view).apply {
//            itemView.setOnClickListener {
//                val curPosition = bindingAdapterPosition
//                //val img: ModelImageOnly = imageList[curPosition]
//            }
        }
    }

    override fun onBindViewHolder(holder: InterestRecyclerViewAdapter.CustomViewHolder, position: Int) {
        val data = datas[position]

        holder.tvWriter.text = data.writer
        holder.tvItemName.text = data.itemName

        val formatter = DecimalFormat("###,###")
        val priceFormatted = formatter.format(data.price)
        holder.tvPrice.text = priceFormatted.plus("￦")

        Glide.with(mContext).load(data.imgUrl).into(holder.ivImage)

        holder.ivDelete.setOnClickListener {
            Toast.makeText(mContext, "관심 상품에서 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            liveData.deleteItemLikes(data.id)
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvWriter: TextView = itemView.findViewById(R.id.tvInterestItemWriter)
        val tvItemName: TextView = itemView.findViewById(R.id.tvInterestItemName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvInterestItemPrice)
        val ivImage: ImageView = itemView.findViewById(R.id.ivInterestItemImage)
        val ivDelete: ImageView = itemView.findViewById(R.id.ivInterestItemDelete)
    }

}