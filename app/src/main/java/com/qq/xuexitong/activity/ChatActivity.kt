package com.qq.xuexitong.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import com.qq.xuexitong.App
import com.qq.xuexitong.R
import com.qq.xuexitong.databinding.ActivityChatBinding
import com.qq.xuexitong.viewModel.ChatViewModel

class ChatActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var chatViewModel: ChatViewModel
    private lateinit var etChat: EditText
    private lateinit var btnChatSend: Button
    private lateinit var ivBack: ImageView
    private lateinit var rvChatContent: RelativeLayout
    private lateinit var svChat: ScrollView
    private val chatIdList = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_chat)
        supportActionBar?.hide()
        val binding =
            DataBindingUtil.setContentView(this, R.layout.activity_chat) as ActivityChatBinding
        chatViewModel = ChatViewModel(this)
        binding.viewModel = chatViewModel
        chatViewModel.loadData("null")
        initView()
    }

    private fun initView() {
        chatIdList.add(R.id.tv_first)
        etChat = findViewById(R.id.et_chat)
        btnChatSend = findViewById(R.id.btn_chat_send)
        btnChatSend.setOnClickListener(this)
        ivBack = findViewById(R.id.iv_back)
        ivBack.setOnClickListener(this)
        rvChatContent = findViewById(R.id.rv_chat_content)
        svChat = findViewById(R.id.sv_chat)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_chat_send -> {
                val text = etChat.text.toString()
                if (TextUtils.isEmpty(text)) {
                    App.showTips("发送消息不能为空", Toast.LENGTH_SHORT)
                    return
                }
                addView(text)
                svChat.post(Runnable { svChat.fullScroll(View.FOCUS_DOWN) })
                chatViewModel.loadData(text)
            }
            R.id.iv_back -> {
                finish()
            }
        }
    }

    @Synchronized
    private fun addView(text: String?) {
        Log.d(App.TAG, "text:$text")
        val userTextView = TextView(this)
        userTextView.setBackgroundResource(R.drawable.chat_text_background_right)
        userTextView.setTextColor(resources.getColor(R.color.text_black))
        userTextView.id = R.id.tv_first + chatIdList.size
        userTextView.text = text
        userTextView.setPadding(25)
        val layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        layoutParams.addRule(RelativeLayout.BELOW, chatIdList[chatIdList.size - 1])
        layoutParams.topMargin = 10
        synchronized(chatIdList) {
            chatIdList.add(userTextView.id)
        }
        etChat.setText("")
        rvChatContent.addView(userTextView, layoutParams)
        rvChatContent.requestLayout()
    }


    @Synchronized
    fun addChatView(text: String?) {
        Log.d(App.TAG, "text:$text")
        val userTextView = TextView(this)
        userTextView.setBackgroundResource(R.drawable.chat_text_background_left)
        userTextView.setTextColor(resources.getColor(R.color.text_black))
        userTextView.id = R.id.tv_first + chatIdList.size
        userTextView.text = text
        userTextView.setPadding(25)
        val layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        layoutParams.addRule(RelativeLayout.BELOW, chatIdList[chatIdList.size - 1])
        layoutParams.topMargin = 10
        synchronized(chatIdList) {
            chatIdList.add(userTextView.id)
        }
        rvChatContent.addView(userTextView, layoutParams)
        rvChatContent.requestLayout()
        svChat.post(Runnable { svChat.fullScroll(View.FOCUS_DOWN) })
    }

}