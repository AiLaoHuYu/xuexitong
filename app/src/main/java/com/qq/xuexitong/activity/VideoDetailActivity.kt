package com.qq.xuexitong.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import com.qq.xuexitong.App
import com.qq.xuexitong.R

class VideoDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var vvVideo: VideoView
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
        vvVideo = findViewById(R.id.vv_video)
        vvVideo.setVideoURI(Uri.parse("http://vjs.zencdn.net/v/oceans.mp4"))
        val mediaController = MediaController(this)
        vvVideo.setMediaController(mediaController)
        vvVideo.start()
        ivBack = findViewById(R.id.iv_back)
        ivBack.bringToFront()
        ivBack.setOnClickListener(this)
//        GlideUtil().loadImgByGlide(this, imgUrl, imgVideo)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                finish()
                return
            }
        }
    }
}