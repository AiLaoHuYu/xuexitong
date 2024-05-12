package com.qq.xuexitong.mode

import android.os.Message
import com.qq.xuexitong.viewModel.ChatViewModel
import java.util.concurrent.Executors

class ChatModel(
    private var chatViewModel: ChatViewModel
) {

    private var isFirst = true

    fun getResultFromRemote(chatContent: String) {
        var result: String
        val thread = Thread()
        thread.run {
            //TODO需要通过网络请求去获取数据
            result = "netWork error"
            if (isFirst) {
                val message = Message()
                message.what = chatViewModel.RESULT_BACK
                message.obj = result
                chatViewModel.handler.sendMessage(message)
                isFirst = false
            } else {
                val message = Message()
                message.what = chatViewModel.ADD_CHAT_VIEW
                message.obj = result
                chatViewModel.handler.sendMessage(message)
            }
        }
        thread.start()
    }


}