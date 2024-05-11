package com.qq.xuexitong.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.*
import com.qq.xuexitong.App
import com.qq.xuexitong.R
import com.qq.xuexitong.mode.UserModel
import com.qq.xuexitong.utils.PopupUtil

class LoginActivity() : AppCompatActivity(), View.OnClickListener {

    companion object {
        private var instance: LoginActivity? = null

        @Synchronized
        fun get(): LoginActivity {
            if (instance == null) {
                instance = LoginActivity()
            }
            return instance!!
        }
    }

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
    private lateinit var tvCustomer: TextView
    private lateinit var agree: CheckBox
    private var isPasswordLock = true
    private var url: String =
        "http://192.168.13.197:8080/user/login?userName=%USER&password=%PASSWORD"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        overridePendingTransition(R.anim.app_bottom_in, R.anim.bottom_silent)
        get()
        supportActionBar!!.hide()
        initView()
    }

    private fun initView() {
        agree = findViewById(R.id.agree)
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
        //客服
        tvCustomer = findViewById(R.id.tv_customer)
        tvCustomer.setOnClickListener(this)
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
                val userName = etUserId.text.toString()
                if (TextUtils.isEmpty(userName)) {
                    App.showTips("用户名为空，请先输入用户名", Toast.LENGTH_SHORT)
                    return
                }
                var realUrl = url
                realUrl = realUrl.replace("%USER", userName)
                val userPassword = etUserPassword.text.toString()
                if (TextUtils.isEmpty(userPassword)) {
                    PopupUtil.showPopup(this, "密码为空，请先输入密码", true, "确定", null)
                    return
                }
                realUrl = realUrl.replace("%PASSWORD", userPassword)
                if (!agree.isChecked) {
                    PopupUtil.showPopup(this, "请先阅读并同意协议", true, 2000)
                    return
                }
                UserModel.get().login(realUrl)
                Log.d(TAG, "用户点击了登录按钮: $realUrl")
            }
            R.id.tv_login_by_phone -> {
                Log.d(TAG, "用户点击了通过手机号码登录")
            }
            R.id.tv_user_register -> {
                Log.d(TAG, "用户点击了新用户注册")
                val intent = Intent(App.getInstance(), RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            R.id.tv_login_by_other -> {
                Log.d(TAG, "用户点击了其他登录方式")
            }
            R.id.login_policy -> {
                Log.d(TAG, "用户点击了登录政策")
                UserModel.get().toLoginPolicy()
            }
            R.id.user_policy -> {
                Log.d(TAG, "用户点击了用户政策")
                UserModel.get().toUserPolicy()
            }
            R.id.tv_customer -> {
                Log.d(TAG, "用户点击了客服")
                UserModel.get().toCustomer()
            }
        }
    }


}