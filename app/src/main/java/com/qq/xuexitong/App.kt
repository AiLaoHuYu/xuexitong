package com.qq.xuexitong

import android.app.Application

class App : Application() {

    companion object {
        const val TAG:String = "XUEXITONG"
        private var app: App = App()
        fun getInstance(): App {
            return app
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }

}