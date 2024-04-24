package com.qq.xuexitong.utils

import android.util.Log
import com.google.gson.*
import com.qq.xuexitong.entity.UserResult
import okhttp3.*
import java.io.IOException
import java.util.concurrent.Executors

object OkHttpUtil {

    private val TAG = OkHttpUtil::class.java.simpleName

    fun login(url: String) {
        val thread = Executors.newSingleThreadExecutor()
        thread.execute {
            val okHttpClient = OkHttpClient.Builder().build()
            val request: Request = Request.Builder().url(url)
                .addHeader("Content-Type", "application/json;charset=utf-8")
                .build()
            val call = okHttpClient.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d(TAG, "onFailure: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonResult = response.body()?.string().toString().trim().replace("\\", "")
                    Log.d(TAG, "jsonResult: $jsonResult ")
                    val gson = Gson()
                    val fromJson = gson.fromJson(jsonResult, UserResult::class.java)
                    Log.d(
                        TAG,
                        "resultCode ${fromJson.data.code}  message: ${fromJson.data.message} data:${fromJson.data.entity?.name}"
                    )
                }
            })
        }
    }

}