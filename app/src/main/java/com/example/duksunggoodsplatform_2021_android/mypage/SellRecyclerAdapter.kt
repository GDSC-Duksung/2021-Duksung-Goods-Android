package com.example.duksunggoodsplatform_2021_android.mypage

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.duksunggoodsplatform_2021_android.feature.posting.ActualPostingModifyActivity
import com.example.duksunggoodsplatform_2021_android.AddPromotionActivity
import com.example.duksunggoodsplatform_2021_android.DepositorActivity
import com.example.duksunggoodsplatform_2021_android.R

class SellRecyclerAdapter(private val context: Context): RecyclerView.Adapter<SellRecyclerAdapter.ViewHolder>(){

    var sellDatas = mutableListOf<SellData>()

    //아이템과 레이아웃 결합
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_mypage_sell,parent,false)
        return ViewHolder(view)
    }

    //리스트 내 아이템 개수
    override fun getItemCount(): Int = sellDatas.size

    //view에 내용 입력
    override fun onBindViewHolder(holder:ViewHolder,position:Int){
        holder.bind(sellDatas[position])
    }

    //레이아웃 내 view연결
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val sellName: TextView = itemView.findViewById(R.id.tv_mypage_sell_name)
        private val sellPromotion: TextView = itemView.findViewById(R.id.tv_mypage_sell_promotion)
        private val sellDepositer:TextView = itemView.findViewById(R.id.tv_mypage_sell_depositer)
        private val sellChangeDelete: TextView = itemView.findViewById(R.id.tv_mypage_sell_delete)
        private val sellPercent: TextView = itemView.findViewById(R.id.tv_mypage_sell_percent)
        private val sellState: TextView = itemView.findViewById(R.id.tv_mypage_sell_state)
        private val sellImage: ImageView = itemView.findViewById(R.id.img_mypage_sell)

        //linearlayout
        private val linear_actual: LinearLayout = itemView.findViewById(R.id.linear_actual)
        private val linear_acutal_1: LinearLayout = itemView.findViewById(R.id.linear_actual_1)
        private val linear_acutal_2: LinearLayout = itemView.findViewById(R.id.linear_actual_2)

        //판매완료 이미지로 바꾸기
        //     private val sellComplete:ImageView = itemView.findViewById(R.id.mypage_sell_complete)
        private val sellCompleteText:TextView = itemView.findViewById(R.id.tv_mypage_sell_complete)
        //   private val linear_percent:LinearLayout = itemView.findViewById(R.id.linear_percent)


        fun bind(item:SellData){
            sellName.text = item.name
            sellPercent.text = item.percent
            sellState.text = item.state
            Glide.with(itemView).load(item.photo).into(sellImage)
            //sellPromotion.setOnClickListener(listener)
            if (sellState.text.equals("가수요조사중")) {
                //removeView(sellLinear)
                linear_acutal_1.removeView(linear_acutal_2)
            }

            if(sellState.text.equals("판매완료")){
                // linear_percent.removeView(linear_percent)
                linear_actual.removeView(linear_acutal_1)
                sellPercent.setBackgroundResource(R.drawable.sell_check)
                sellCompleteText.setText("판매가 완료되었습니다.")
            }

            sellPromotion.setOnClickListener {
                Intent(context, AddPromotionActivity::class.java ).run{
                    context.startActivity(this)
                }
            }
            sellDepositer.setOnClickListener {
                val intent = Intent(context, DepositorActivity::class.java)
                intent.putExtra("itemId", item.id)
                intent.run{
                    context.startActivity(this)
                }
            }

            //TODO: 수정 및 삭제 버튼 코드 짜기 (가수요조사폼 수정 및 삭제 어디로?)
            sellChangeDelete.setOnClickListener {
                // if((sellState.text as String).equals("가수요조사"))
                if (sellState.text.equals("가수요조사중")) {
                    Intent(context, AddPromotionActivity::class.java).run {
                        context.startActivity(this)
                    }
                }
                if (sellState.text.equals("실수요조사중")) {
                    Intent(context, ActualPostingModifyActivity::class.java).run {
                        context.startActivity(this)
                    }
                }
            }
        }

        }
    }
