package com.qq.xuexitong.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.qq.xuexitong.App
import com.qq.xuexitong.R
import com.qq.xuexitong.mode.UserModel
import com.qq.xuexitong.utils.PatternUtil

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var ivBack: ImageView
    private lateinit var loginPolicy: TextView
    private lateinit var userPolicy: TextView
    private lateinit var tvCustomer: TextView
    private lateinit var etUserPhone: EditText
    private lateinit var etUserCode: EditText
    private lateinit var etUserPassword: EditText
    private lateinit var btnLogin: Button

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
        tvCustomer = findViewById(R.id.tv_customer)
        tvCustomer.setOnClickListener(this)
        btnLogin = findViewById(R.id.btn_login)
        btnLogin.setOnClickListener(this)
        etUserPhone = findViewById(R.id.et_user_phone)
        etUserCode = findViewById(R.id.et_user_code)
        etUserPassword = findViewById(R.id.et_user_password)
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
            R.id.tv_customer -> {
                UserModel.get().toCustomer()
            }
            R.id.btn_login -> {
                if (TextUtils.isEmpty(etUserPhone.text)) {
                    App.showTips("手机号为空，请先输入手机号", Toast.LENGTH_SHORT)
                    return
                }
                val phone = etUserPhone.text.toString()
                if (!PatternUtil.isMatched(PatternUtil.PHONE, phone)) {
                    App.showTips("手机号格式错误！", Toast.LENGTH_SHORT)
                    etUserPhone.text = null
                    return
                }
            }
            else -> {

            }
        }
    }
}