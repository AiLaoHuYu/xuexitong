package com.qq.xuexitong.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.qq.xuexitong.App
import com.qq.xuexitong.R
import com.qq.xuexitong.utils.OkHttpUtil
import java.util.*

class LoginActivity() : AppCompatActivity(), View.OnClickListener {

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
    private lateinit var agree:CheckBox
    private var isPasswordLock = true
    private var url: String =
        "http://192.168.14.161:8080/user/login?userName=%USER&password=%PASSWORD"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        overridePendingTransition(R.anim.app_bottom_in, R.anim.bottom_silent)
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
                    App.showTips("密码为空，请先输入密码", Toast.LENGTH_SHORT)
                    return
                }
                realUrl = realUrl.replace("%PASSWORD", userPassword)
                if (!agree.isChecked){
                    showPopup()
                    return
                }
                OkHttpUtil.login(realUrl)
                Log.d(TAG, "用户点击了登录按钮: $realUrl")
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

    private fun showPopup() {
        //创建弹窗构建器
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            .setTitle("Tips")
            .setMessage("请先阅读并同意协议") //点击窗口以外的区域，窗口消失 (默认为true)
            .setCancelable(true)
        //创建弹窗
        val dlg: AlertDialog = builder.create()
        //窗口显示
        dlg.show()
        //时间线程，2s后执行里面的代码
        val t = Timer()
        t.schedule(object : TimerTask() {
            override fun run() {
                //窗口消失
                dlg.dismiss()
            }
        }, 2000)
    }


}