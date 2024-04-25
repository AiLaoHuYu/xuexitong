package com.qq.xuexitong.utils

import android.content.SharedPreferences
import com.qq.xuexitong.App

open class SharedPreferencesUtil {

    private var mInstance: SharedPreferencesUtil? = null

    fun get(): SharedPreferencesUtil {
        if (mInstance == null) {
            synchronized(SharedPreferencesUtil::class) {
                if (mInstance == null)
                    mInstance = SharedPreferencesUtil()
            }
        }
        return mInstance!!
    }

    private val sharedPreferences: SharedPreferences =
        App.getInstance().getSharedPreferences("xuexitong", 0)

    internal fun getStringFromSharedPreferences(key: String, def: String): String {
        return sharedPreferences.getString(key, def)
    }

    internal fun setStringToSharedPreferences(key: String, value: String) {
        val edit = sharedPreferences.edit()
        edit.putString(key, value)
        edit.commit()
    }


}