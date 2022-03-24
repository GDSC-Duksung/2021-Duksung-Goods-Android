package com.example.duksunggoodsplatform_2021_android.depositor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.model.DepositorData


class RecyclerAdapter(private val context: Context) :RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    var depositorList = mutableListOf<DepositorData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.depositor_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = depositorList.size
/*
    override fun getItemViewType(position:Int): Int {
        return position;
    }
*/

    override fun onBindViewHolder(holder:ViewHolder, position:Int){
        holder.bind(depositorList[position])
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val depositorName: TextView = itemView.findViewById<TextView>(R.id.depositorName)
        val depositorChecked: ImageView = itemView.findViewById<ImageView>(R.id.depositorChecked)

        fun bind(depositor: DepositorData){
            depositorName.text = depositor.user?.nickname
            if (depositor.deposit!!)
                Glide.with(itemView).load(R.drawable.depositor_checked).into(depositorChecked)
            else
                Glide.with(itemView).load(R.drawable.depositor_unchecked).into(depositorChecked)

            depositorChecked.setOnClickListener {
                if (depositor.deposit!!) {
                    depositor.deposit = false
                    Glide.with(itemView).load(R.drawable.depositor_unchecked).into(depositorChecked)
                } else {
                    depositor.deposit = true
                    Glide.with(itemView).load(R.drawable.depositor_checked).into(depositorChecked)
                }
            }
        }
    }


}



/*
class RecyclerAdapter(private val items: ArrayList<DepositorData>) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       /*
        val item = items[position]
        val listener = View.OnClickListener { it ->
           Toast.makeText(it.context, "Clicked: ${item.name}", Toast.LENGTH_SHORT).show()
        }


        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
*/

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.depositior_item, parent, false)
        return ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var view: View = v
        val depositorName:TextView = view.findViewById<TextView>(R.id.depositorName)
        val depositorChecked:ImageView = view.findViewById<ImageView>(R.id.depositorChecked)

        fun bind(listener: View.OnClickListener, item: DepositorData) {
         //   view.depositorChecked.setImageDrawable(item.checked)
        //    view.depositorName.text = item.name
          //  view.setOnClickListener(listener)
        //    view.depositorName.text = item.name
            depositorName.text = item.name
            Glide.with(view).load(item.checked).into(depositorChecked)
        }
    }
}

 */