package com.qq.xuexitong.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.*
import com.qq.xuexitong.R

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = LoginActivity::class.java.simpleName
    private lateinit var llBack: LinearLayout
    private lateinit var etUserId: EditText
    private lateinit var etUserPassword: EditText
    private lateinit var ivUserPassword: ImageView
    private lateinit var userForgetPassword: TextView
    private lateinit var btnLogin: Button
    private lateinit var tvUserRegister: TextView
    private lateinit var tvLoginByPhone: TextView
    private lateinit var tvLoginByOther: TextView
    private lateinit var loginPolicy: TextView
    private lateinit var userPolicy: TextView
    private var isPasswordLock = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        overridePendingTransition(R.anim.app_bottom_in, R.anim.bottom_silent)
        supportActionBar!!.hide()
        initView()
    }

    private fun initView() {
        llBack = findViewById(R.id.ll_back)
        //登录政策
        loginPolicy = findViewById(R.id.login_policy)
        loginPolicy.setOnClickListener(this)
        //用户政策
        userPolicy = findViewById(R.id.user_policy)
        userPolicy.setOnClickListener(this)
        //用户输入的用户名
        etUserId = findViewById(R.id.et_user_id)
        //登录按钮
        btnLogin = findViewById(R.id.btn_login)
        btnLogin.setOnClickListener(this)
        //新用户注册
        tvUserRegister = findViewById(R.id.tv_user_register)
        tvUserRegister.setOnClickListener(this)
        //手机号快捷登录
        tvLoginByPhone = findViewById(R.id.tv_login_by_phone)
        tvLoginByPhone.setOnClickListener(this)
        //其他登录方式
        tvLoginByOther = findViewById(R.id.tv_login_by_other)
        tvLoginByOther.setOnClickListener(this)
        //忘记密码
        userForgetPassword = findViewById(R.id.user_forget_password)
        //设置层级到最上层
        userForgetPassword.bringToFront()
        userForgetPassword.setOnClickListener(this)
        //密码可见与不可见的图标
        ivUserPassword = findViewById(R.id.iv_user_password)
        ivUserPassword.bringToFront()
        ivUserPassword.setOnClickListener(this)
        //用户输入的密码
        etUserPassword = findViewById(R.id.et_user_password)
        llBack.setOnClickListener(this)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.bottom_silent, R.anim.app_bottom_out)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ll_back -> {
                finish()
            }
            R.id.iv_user_password -> {
                if (isPasswordLock) {
                    etUserPassword.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    ivUserPassword.setImageResource(R.drawable.unlock)
                } else {
                    etUserPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                    ivUserPassword.setImageResource(R.drawable.lock)
                }
                isPasswordLock = !isPasswordLock
            }
            R.id.user_forget_password -> {
                Log.d(TAG, "用户点击了忘记密码")
            }
            R.id.btn_login -> {
                Log.d(TAG, "用户点击了登录按钮")
            }
            R.id.tv_login_by_phone -> {
                Log.d(TAG, "用户点击了通过手机号码登录")
            }
            R.id.tv_user_register -> {
                Log.d(TAG, "用户点击了新用户注册")
            }
            R.id.tv_login_by_other -> {
                Log.d(TAG, "用户点击了其他登录方式")
            }
            R.id.login_policy -> {
                Log.d(TAG, "用户点击了其他登录政策")
            }
            R.id.user_policy -> {
                Log.d(TAG, "用户点击了其他用户政策")
            }
        }
    }


}