package com.qq.xuexitong.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object GlideUtil {
//    fun loadImgByGlide(context: Context, url: String, view: ImageView) {
//        loadImgByGlide(context, url, view)
//    }

//    fun loadImgByGlide(context: Context, url: String, placeholder: Int, view: ImageView) {
//        loadImgByGlide(context, url, placeholder, view)
//    }

    fun loadImgByGlide(
        context: Context,
        url: String,
        view: ImageView
    ) {
        Glide.with(context)
            .load(url)
            .into(view)
        return
    }
}
