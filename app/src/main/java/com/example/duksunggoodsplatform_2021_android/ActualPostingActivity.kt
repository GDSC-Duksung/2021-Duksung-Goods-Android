package com.example.duksunggoodsplatform_2021_android
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class ActualPostingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actual_demand_posting)

        // Spinner
        val spinner: Spinner = findViewById(R.id.spinner_actual_posting_category)
        val items = resources.getStringArray(R.array.category_array)
        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.adapter = myAdapter
        spinner.setSelection(3)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when(position) {
                    0 -> {

                    }
                    1 -> {

                    }
                    2 -> {

                    }
                    else -> {

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.d("SpinnerTag", "onNothingSelected")
            }
        }
    }
}

