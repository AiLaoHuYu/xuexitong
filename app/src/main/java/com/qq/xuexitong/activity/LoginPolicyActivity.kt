package com.qq.xuexitong.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.qq.xuexitong.R

class LoginPolicyActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var tvLoginPolicy: TextView
    private lateinit var ivBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_policy)
        supportActionBar?.hide()
        initView()
    }

    private fun initView() {
        tvLoginPolicy = findViewById(R.id.tv_login_policy)
        ivBack = findViewById(R.id.iv_back)
        ivBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                finish()
            }
            else -> {

            }
        }
    }
}