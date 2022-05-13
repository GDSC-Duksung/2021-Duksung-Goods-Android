package com.example.duksunggoodsplatform_2021_android.data.local

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceController {
    fun getUserToken(ctx: Context): String {
        val preference: SharedPreferences = ctx.getSharedPreferences("User Info", Context.MODE_PRIVATE)
        return preference.getString("User Token", "")!!
    }
}