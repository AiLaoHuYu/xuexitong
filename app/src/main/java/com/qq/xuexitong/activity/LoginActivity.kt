package com.qq.xuexitong.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qq.xuexitong.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        overridePendingTransition(R.anim.app_bottom_in,R.anim.bottom_silent)
        supportActionBar!!.hide()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.bottom_silent,R.anim.app_bottom_out)
    }


}