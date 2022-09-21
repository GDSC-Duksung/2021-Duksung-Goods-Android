package com.example.duksunggoodsplatform_2021_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.duksunggoodsplatform_2021_android.databinding.ActivityWritingBinding

class WritingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWritingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWritingBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}