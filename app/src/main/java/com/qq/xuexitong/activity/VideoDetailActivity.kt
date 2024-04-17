package com.qq.xuexitong.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.qq.xuexitong.App
import com.qq.xuexitong.R
import com.qq.xuexitong.utils.GlideUtil

class VideoDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var imgVideo: ImageView
    private lateinit var imgUrl: String
    private lateinit var ivBack: ImageView
    private var TAG: String = VideoDetailActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
        supportActionBar?.hide()
        imgUrl = intent.getStringExtra("imgUrl")
        Log.d(App.TAG, "接收到传过来的图片地址:${imgUrl}")
        initView()
    }

    private fun initView() {
        imgVideo = findViewById(R.id.img_video)
        ivBack = findViewById(R.id.iv_back)
        ivBack.setOnClickListener(this)
        GlideUtil().loadImgByGlide(this, imgUrl, imgVideo)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return
            }
        }
    }
}