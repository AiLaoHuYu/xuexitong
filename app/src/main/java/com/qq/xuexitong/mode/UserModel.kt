package com.qq.xuexitong.mode

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.qq.xuexitong.App
import com.qq.xuexitong.activity.LoginActivity
import com.qq.xuexitong.entity.UserEntity
import com.qq.xuexitong.entity.UserResult
import com.qq.xuexitong.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.concurrent.Executors

open class UserModel {

    private val TAG = UserModel::class.java.simpleName
    private val thread = Executors.newSingleThreadExecutor()
    private val handler by lazy { WeakReferenceHandler(get()) }
    private val PARSE_RESULT = 1

    companion object {
        private var instance: UserModel? = null

        @Synchronized
        fun get(): UserModel {
            if (instance == null) {
                instance = UserModel()
            }
            return instance!!
        }
    }

    class WeakReferenceHandler(obj: UserModel) : Handler(Looper.getMainLooper()) {
        private val mRef: WeakReference<UserModel> = WeakReference(obj)

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            mRef.get()?.run {
                Log.d(TAG, "handleMessage: ${msg!!.what} ${msg!!.obj} ")
                when (msg!!.what) {
                    PARSE_RESULT -> {
                        val fromJson = msg.obj as UserResult
                        Log.d(
                            TAG,
                            "resultCode ${fromJson.data.code}  message: ${fromJson.data.message} data:${fromJson.data.entity?.name}"
                        )
                        //解析返回来的结果
                        parseResult(fromJson.data.code, fromJson.data.message, fromJson.data.entity)
                    }
                    else -> {

                    }
                }
            }
        }
    }

    fun login(url: String) {
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
                    val message = Message()
                    message.what = PARSE_RESULT
                    message.obj = fromJson as UserResult
                    handler.sendMessageDelayed(message, 200)
                }
            })
        }
    }

    private fun parseResult(code: Int, text: String?, entity: UserEntity?) {
        if (code == 555) {
            //后台请求失败
            Log.d(TAG, "请求失败: $text")
            App.showTips("账号或密码错误", Toast.LENGTH_SHORT)
        }
    }

}