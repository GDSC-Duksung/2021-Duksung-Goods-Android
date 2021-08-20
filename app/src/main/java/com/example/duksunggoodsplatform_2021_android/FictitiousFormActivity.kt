package com.example.duksunggoodsplatform_2021_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_fictitious_demand_form.*

class FictitiousFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fictitious_demand_form)

        val countUpButton : ImageButton = findViewById(R.id.countUpButton)
        val countDownButton : ImageButton = findViewById(R.id.countDownButton)
        val txtCount : TextView = findViewById(R.id.count)
        var count = 1

        countUpButton.setOnClickListener{
            count ++
            txtCount.setText(count.toString())
        }

        countDownButton.setOnClickListener{
            count --
            txtCount.setText(count.toString())
            if (count<2)
                count = 1
        }
    }
}