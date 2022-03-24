package com.example.duksunggoodsplatform_2021_android.model

data class ResponseEntity<T>(
    val transactionTime: String?,
    val status: String?,
    val message: String?,
    val data: T? =null
) {
    companion object {
        inline fun <reified T> error(description: String? = null) =
            ResponseEntity("", "ERROR", "ERROR", null as T?)
    }
}