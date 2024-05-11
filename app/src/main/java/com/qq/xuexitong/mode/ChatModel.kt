package com.qq.xuexitong.mode

import java.util.concurrent.Executors

class ChatModel {

    private val thread = Executors.newSingleThreadExecutor()


    fun getResultFromRemote(chatContent: String): String {
        var result: String
        thread.execute {
            //TODO需要通过网络请求去获取数据
            result = "netWork error"
        }
        return result
    }


}