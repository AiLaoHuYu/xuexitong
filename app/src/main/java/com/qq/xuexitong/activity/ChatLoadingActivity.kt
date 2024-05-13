package com.qq.xuexitong.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.qq.xuexitong.R

class ChatLoadingActivity : AppCompatActivity(), View.OnClickListener {

    val totalTime = 3000L // 总时间为 3 秒
    val intervalTime = 1000L // 每次更新时间为 1 秒
    private lateinit var ivBack: ImageView
    private lateinit var ivChatLoading: ImageView
    private lateinit var context: Context
    private lateinit var countDownTimer:CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_loading)
        supportActionBar?.hide()
        initView()
        waitToStart()
        context = this
    }

    private fun initView() {
        ivBack = findViewById(R.id.iv_back)
        ivBack.setOnClickListener(this)
        ivChatLoading = findViewById(R.id.iv_chat_loading)
        Glide.with(this).asGif().load(R.drawable.chatactivity_loading).into(ivChatLoading)
    }

    private fun waitToStart() {
        //延迟3s跳转到客服界面
        countDownTimer = object : CountDownTimer(totalTime, intervalTime) {
            override fun onTick(millisUntilFinished: Long) {
                // 每次更新时执行的代码，例如更新 UI 上的计时器显示
                val secondsLeft = millisUntilFinished / 1000
                Log.d("ChatLoadingActivity", "Seconds left: $secondsLeft")
            }

            override fun onFinish() {
                // 倒计时结束时执行的代码
                Log.d("ChatLoadingActivity", "Countdown finished!")
                val intent = Intent(context, ChatActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
        countDownTimer.start()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                finish()
            }
        }
    }

    override fun onStop() {
        Log.d("ChatLoadingActivity", "onStop: ")
        super.onStop()
        finish()
        countDownTimer.cancel()
    }
}