package com.qq.xuexitong

import android.app.Application
import com.qq.xuexitong.utils.SharedPreferencesUtil

class App : Application() {

    companion object {
        const val TAG: String = "XUEXITONG"
        private var app: App = App()
        var isLogin = false
        fun getInstance(): App {
            return app
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        val user = SharedPreferencesUtil().get().getStringFromSharedPreferences("user")
        isLogin = user != "youke"
    }

}