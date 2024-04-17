package com.qq.xuexitong.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.qq.xuexitong.R

class GlideUtil() {
    fun loadImgByGlide(context: Context, url: String, view: ImageView) {
        loadImgByGlide(context, url, -1, view, 0, 0)
    }

    fun loadImgByGlide(context: Context, url: String, placeholder: Int, view: ImageView) {
        loadImgByGlide(context, url, placeholder, view, 0, 0)
    }

    private fun loadImgByGlide(
        context: Context,
        url: String,
        placeholder: Int,
        view: ImageView,
        width: Int,
        height: Int
    ) {
        if (width > 0 && height > 0) {
            if (placeholder != -1) {
                Glide.with(context)
                    .load(url)
                    .override(width, height)
                    .error(R.mipmap.ic_launcher)
                    .placeholder(placeholder)
                    .into(view)
                return
            }
            Glide.with(context)
                .load(url)
                .override(width, height)
                .error(R.mipmap.ic_launcher)
                .into(view)
            return
        }
        Glide.with(context)
            .load(url)
            .error(R.mipmap.ic_launcher)
            .into(view)
        return
    }
}
