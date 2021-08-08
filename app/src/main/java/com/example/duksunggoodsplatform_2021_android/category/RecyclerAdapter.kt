package com.example.duksunggoodsplatform_2021_android.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.duksunggoodsplatform_2021_android.R
import kotlinx.android.synthetic.main.category_item.view.*


// fragment_all, clothes, stationary, etc.xml와 category_item.xml을 이어주는 역할

//class RecyclerAdapter(private val items: ArrayList<Data>) :
//
//    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
//
//    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
//
//        private var view: View = v
//        fun bind(listener: View.OnClickListener, item: Data) {
//            view.imageView.setImageDrawable(item.photo)
//            view.name.text = item.name
//            view.price.text = item.price.toString()
//        }
//
//    }
//
//    override fun getItemCount() = items.size
//
//    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
//
//        val item = items[position]
//
//        val listener = View.OnClickListener { it ->
//            Toast.makeText(it.context, "Clicked:" + item.name, Toast.LENGTH_SHORT).show()
//        }
//        holder.apply {
//            bind(listener, item)
//            itemView.tag = item
//        }
////
////        holder.image.setImageResource(R.drawable.star_clicked)
////        holder.name.setText("타이틀입니다")
////        holder.price.setText("세부사항 입니다")
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
//            RecyclerAdapter.ViewHolder {
//        var inflaterView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.category_item, parent, false)
//
//        return RecyclerAdapter.ViewHolder(inflaterView)
//    }
//
//
//}



//새로운 코드

class RecyclerAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var datas = mutableListOf<Data>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = itemView.findViewById(R.id.name)
        private val txtPrice: TextView = itemView.findViewById(R.id.price)
        private val imgProfile: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(item: Data) {
            txtName.text = item.name
            txtPrice.text = item.price
            Glide.with(itemView).load(item.photo).into(imgProfile)

        }
    }


}