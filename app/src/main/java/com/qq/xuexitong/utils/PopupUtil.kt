package com.qq.xuexitong.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.qq.xuexitong.App
import java.util.*

object PopupUtil {

    fun showPopup(
        activity: Activity,
        text: String
    ) {
        showPopup(activity, text, false, null, null)
    }

    fun showPopup(
        activity: Activity,
        text: String,
        ifNeedToCancelByTime: Boolean,
        cancelByTimeDelay: Long
    ) {
        showPopup(activity, text, false, null, null, ifNeedToCancelByTime, cancelByTimeDelay)
    }

    fun showPopup(
        activity: Activity,
        text: String,
        isSetPositiveButton: Boolean,
        positiveText: String?,
        positiveRunnable: Runnable?,
    ) {
        showPopup(activity, text, isSetPositiveButton, positiveText, positiveRunnable, false, 0)
    }


    fun showPopup(
        activity: Activity,
        text: String,
        isSetPositiveButton: Boolean,
        positiveText: String?,
        positiveRunnable: Runnable?,
        ifNeedToCancelByTime: Boolean,
        cancelByTimeDelay: Long
    ) {
        //创建弹窗构建器
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            .setMessage(text)
            .setCancelable(true) //点击窗口以外的区域，窗口消失 (默认为true)

        if (isSetPositiveButton) {
            builder.setPositiveButton(
                positiveText
            ) { _, _ -> positiveRunnable?.run() }
        }

        //创建弹窗
        val dlg: AlertDialog = builder.create()
        //窗口显示
        dlg.show()
        if (ifNeedToCancelByTime) {
            //时间线程，2s后执行里面的代码
            val t = Timer()
            t.schedule(object : TimerTask() {
                override fun run() {
                    //窗口消失
                    dlg.dismiss()
                }
            }, cancelByTimeDelay)
        }

    }

}