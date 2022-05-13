package com.example.duksunggoodsplatform_2021_android.data

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <ResponseType> Call<ResponseType>.customEnqueue(
    onFail: () -> Unit = { Log.d("network", "네트워크 통신 실패") },
    onSuccess: (ResponseType) -> Unit,
    onError: (Response<ResponseType>) -> Unit = {}
) {
    this.enqueue(object : Callback<ResponseType> {
        override fun onFailure(call: Call<ResponseType>, t: Throwable) {
            onFail()
            t.message?.let { Log.d("network", it) }
        }

        override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>) {
            response.body()?.let {
                onSuccess(it)
            } ?: onError(response)

            Log.d("network", response.message())
            Log.d("network", response.code().toString())
        }
    })
}
