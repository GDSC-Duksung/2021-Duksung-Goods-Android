package com.example.duksunggoodsplatform_2021_android.api

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.duksunggoodsplatform_2021_android.MainActivity
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.data.local.SharedPreferenceController.getUserToken
import com.example.duksunggoodsplatform_2021_android.user.LoginActivity
import com.example.duksunggoodsplatform_2021_android.user.ModelLoginSignUpResponseData
import retrofit2.Call
import retrofit2.Response

class MyApplication: Application() {
    init {
        instance = this
    }

    companion object{
        lateinit var instance: MyApplication

        //어디서든 context 사용 가능
        fun applicationContext(): Context {
            return instance.applicationContext
        }

    }

}