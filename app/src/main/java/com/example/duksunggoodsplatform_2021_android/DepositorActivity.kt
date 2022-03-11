package com.example.duksunggoodsplatform_2021_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.duksunggoodsplatform_2021_android.depositor.RecyclerAdapter
import com.example.duksunggoodsplatform_2021_android.model.DepositorData
import com.example.duksunggoodsplatform_2021_android.model.ResponseEntity
import kotlinx.android.synthetic.main.activity_depositor.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.runBlocking


class DepositorActivity : AppCompatActivity() {
    lateinit var recyclerAdapter: RecyclerAdapter
    var data = mutableListOf<DepositorData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_depositor)

        runBlocking {
            ApiGenerator.generate(ApiService::class.java).fetchDepositors(
                intent.getLongExtra("itemId", 1)
            )
                .enqueue(object : Callback<ResponseEntity<MutableList<DepositorData>>> {
                    override fun onResponse(
                        call: Call<ResponseEntity<MutableList<DepositorData>>>,
                        response: Response<ResponseEntity<MutableList<DepositorData>>>
                    ) {
                        val result = response.body()?.data
                        if (result != null) {
                            for (it in result) {
                                data.apply { add(it) }
                            }
                            recyclerAdapter.depositorList = data
                            recyclerAdapter.notifyDataSetChanged()
                        }
                    }

                    override fun onFailure(
                        call: Call<ResponseEntity<MutableList<DepositorData>>>,
                        t: Throwable
                    ) {
                        Log.e("Error: ", t.stackTraceToString())
                    }
                })
        }

        val depositorBack: TextView = findViewById<TextView>(R.id.depositor_back)
        depositorBack.setOnClickListener { finish() }

        recyclerAdapter = RecyclerAdapter(this)
        recycler_depositor.adapter = recyclerAdapter

        depositor_checkbox.setOnClickListener {
            if (depositor_checkbox.isChecked) {
                recyclerAdapter.depositorList = data.filter { it.deposit == true }.toMutableList()
                recyclerAdapter.notifyDataSetChanged()
            } else {
                recyclerAdapter.depositorList = data.filter { it.deposit != null }.toMutableList()
                recyclerAdapter.notifyDataSetChanged()
            }
        }

    }
}