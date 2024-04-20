package com.qq.xuexitong.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qq.xuexitong.App
import com.qq.xuexitong.R
import com.qq.xuexitong.base.BaseRvAdapter


class RvClassifyAdapter(list: ArrayList<String>) :
    BaseRvAdapter<String, RvClassifyAdapter.MyViewHolder>(list) {

    private var contextList: ArrayList<String> = list
    private val TAG: String = RvClassifyAdapter::class.java.simpleName

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.home_title)
        fun bindData(text: String) {
            title.text = text
        }

    }

    override fun onItemClick(holder: MyViewHolder, position: Int) {
        Log.d(TAG, "onItemClick: $position")
    }

    override fun baseCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(App.getInstance()).inflate(R.layout.classify_rv_item, parent, false)
        return MyViewHolder(view)
    }

    override fun baseBindView(holder: MyViewHolder, position: Int) {
        holder.bindData(contextList[position])
    }


}