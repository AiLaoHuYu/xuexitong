package com.qq.xuexitong.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.qq.xuexitong.R
import com.qq.xuexitong.mode.UserModel

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var ivBack: ImageView
    private lateinit var loginPolicy: TextView
    private lateinit var userPolicy: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        initView()
    }

    private fun initView() {
        ivBack = findViewById(R.id.iv_back)
        ivBack.setOnClickListener(this)
        loginPolicy = findViewById(R.id.login_policy)
        loginPolicy.setOnClickListener(this)
        userPolicy = findViewById(R.id.user_policy)
        userPolicy.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.login_policy -> {
                UserModel.get().toLoginPolicy()
            }
            R.id.user_policy -> {
                UserModel.get().toUserPolicy()
            }
            else -> {

            }
        }
    }
}