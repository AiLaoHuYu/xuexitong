package com.qq.xuexitong.viewModel

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qq.xuexitong.App
import com.qq.xuexitong.mode.ChatModel

class ChatViewModel : ViewModel() {

    val data = MutableLiveData<String>()
    private val chatModel = ChatModel()
    val requestList = ArrayList<String>()
    lateinit var handler: Handler
    val ADD_CHAT_VIEW = 888

    private fun getData(): LiveData<String> {
        return data
    }

    fun loadData(chat: String) {
        Log.d(App.TAG, "loadData: $chat")
        requestList.add(chat)
        //从Model中获取数据
        val result = chatModel.getResultFromRemote(chat)
        data.value = result
    }

}