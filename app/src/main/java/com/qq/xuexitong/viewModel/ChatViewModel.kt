package com.qq.xuexitong.viewModel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qq.xuexitong.App
import com.qq.xuexitong.BR
import com.qq.xuexitong.activity.ChatActivity
import com.qq.xuexitong.mode.ChatModel

class ChatViewModel(private var chatActivity: ChatActivity) : BaseObservable() {

    @Bindable
    val data = MutableLiveData<String>()
    private val chatModel = ChatModel(this)
    private val requestList = ArrayList<String>()
    private val resultList = ArrayList<String>()
    var handler: Handler = Handler(Looper.getMainLooper()) { msg ->
        when (msg?.what) {
            ADD_CHAT_VIEW -> {
                Log.d(App.TAG, "ADD_CHAT_VIEW: ${msg.obj}")
                resultList.add(msg.obj as String)
                chatActivity.addChatView(msg.obj as String?)
            }
            RESULT_BACK -> {
                Log.d(App.TAG, "RESULT_BACK: ${msg.obj}")
                resultList.add(msg.obj as String)
                setData(msg.obj as String)
            }
        }
        true
    }
    val ADD_CHAT_VIEW = 888
    val RESULT_BACK = 666

    private fun getData(): LiveData<String> {
        return data
    }

    fun loadData(chat: String) {
        Log.d(App.TAG, "loadData: $chat")
        requestList.add(chat)
        //从Model中获取数据
        chatModel.getResultFromRemote(chat)
    }

    private fun setData(result: String) {
        Log.d(App.TAG, "setData: $result")
        data.value = result
        notifyPropertyChanged(BR.viewModel)

    }

}