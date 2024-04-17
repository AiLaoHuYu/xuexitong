package com.qq.xuexitong.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qq.xuexitong.App
import com.qq.xuexitong.R
import com.qq.xuexitong.base.BaseRvAdapter

class RvVerticalAdapter(list: ArrayList<String>) :
    BaseRvAdapter<String, RvVerticalAdapter.MyViewHolder>(list) {

    private var mContext: Context = App.getInstance()
    private var contextList: ArrayList<String> = list


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var title: TextView = view.findViewById(R.id.home_title)
        fun bindData(text: String) {
            title.text = text
        }

    }

    override fun baseCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.home_top_rv_item, parent, false)
        return MyViewHolder(view)
    }

    override fun baseBindView(holder: MyViewHolder, position: Int) {
        holder.bindData(contextList[position])
    }

    override fun onItemClick(holder: MyViewHolder, position: Int) {
        //Item被点击后的回调
        Log.d(App.TAG, "onItemClick: $position")

    }

}