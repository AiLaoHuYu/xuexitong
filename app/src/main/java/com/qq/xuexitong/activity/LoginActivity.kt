package com.qq.xuexitong.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.qq.xuexitong.R

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var ivBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        overridePendingTransition(R.anim.app_bottom_in, R.anim.bottom_silent)
        supportActionBar!!.hide()
        initView()
    }

    private fun initView() {
        ivBack = findViewById(R.id.iv_back)
        ivBack.setOnClickListener(this)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.bottom_silent, R.anim.app_bottom_out)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_back -> {
                finish()
            }
        }
    }


}