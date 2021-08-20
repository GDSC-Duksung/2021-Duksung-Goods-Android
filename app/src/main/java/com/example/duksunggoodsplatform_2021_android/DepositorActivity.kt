package com.example.duksunggoodsplatform_2021_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.duksunggoodsplatform_2021_android.depositor.DepositorData
import com.example.duksunggoodsplatform_2021_android.depositor.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_depositor.*


class DepositorActivity : AppCompatActivity() {


    lateinit var RecyclerAdapter: RecyclerAdapter
    var data = mutableListOf<DepositorData>()
    //  lateinit var inflater:LayoutInflater


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  return inflater(R.layout.activity_depositor)
        setContentView(R.layout.activity_depositor)

        val depositor_back: TextView = findViewById<TextView>(R.id.depositor_back)

        depositor_back.setOnClickListener {

            //Toast.makeText(this,"되는거니?",Toast.LENGTH_LONG).show()
            finish()
        }


        //super.onViewCreated(view, savedInstanceState)
        RecyclerAdapter = RecyclerAdapter(this)

        // RecyclerAdapter = RecyclerAdapter(activity!!)
        recycler_depositor.adapter = RecyclerAdapter


        data.apply{
            add(DepositorData("김아무개",R.drawable.depositor_unchecked))
            add(DepositorData("김아무개",R.drawable.depositor_unchecked))
            add(DepositorData("김아무개",R.drawable.depositor_checked))
            add(DepositorData("김아무개",R.drawable.depositor_checked))
            add(DepositorData("김아무개",R.drawable.depositor_unchecked))
            add(DepositorData("김아무개",R.drawable.depositor_unchecked))
            add(DepositorData("김아무개",R.drawable.depositor_checked))
            add(DepositorData("김아무개",R.drawable.depositor_checked))
            add(DepositorData("김아무개",R.drawable.depositor_unchecked))
            add(DepositorData("김아무개",R.drawable.depositor_unchecked))
            add(DepositorData("김아무개",R.drawable.depositor_checked))
            add(DepositorData("김아무개",R.drawable.depositor_checked))
            add(DepositorData("김아무개",R.drawable.depositor_unchecked))
            add(DepositorData("김아무개",R.drawable.depositor_unchecked))
            add(DepositorData("김아무개",R.drawable.depositor_checked))
            add(DepositorData("김아무개",R.drawable.depositor_checked))


            RecyclerAdapter.Datas = data
            RecyclerAdapter.notifyDataSetChanged()
        }

        //TODO 체크박스 기능 아직 못함!!!!
        depositor_checkbox.setOnClickListener {


            if(depositor_checkbox.isChecked()){
                // data.find{ it.checked == R.drawable.depositor_checked }
                //    data.filter { it.checked == R.drawable.depositor_checked }
                data.filter {
                    it.checked == R.drawable.depositor_checked
                    // RecyclerAdapter.Datas = data
                    //RecyclerAdapter.notifyDataSetChanged()
                }
                RecyclerAdapter.Datas = data
                RecyclerAdapter.notifyDataSetChanged()
                // Log.d("tag",strss)
            }
            else if(!depositor_checkbox.isChecked()){

                Log.d("tag","unchecked")
            }


        }

    }




}
