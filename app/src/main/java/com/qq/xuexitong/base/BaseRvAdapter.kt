package com.qq.xuexitong.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRvAdapter<T, VH : RecyclerView.ViewHolder?>(list: ArrayList<T>) :
    RecyclerView.Adapter<VH>() {

    private var dataList: ArrayList<T> = list
    lateinit var listener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return baseCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder?.itemView?.setOnClickListener(View.OnClickListener {
            onItemClick(holder, position)
        })
        baseBindView(holder, position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    interface OnClickListener {
        fun onClick(text: String)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.listener = onClickListener
    }


    abstract fun onItemClick(holder: VH, position: Int)

    abstract fun baseCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    abstract fun baseBindView(holder: VH, position: Int)

    open fun notifyData(poiItemList: ArrayList<T>?) {
        if (poiItemList != null) {
            val previousSize: Int = dataList.size
            dataList.clear()
            notifyItemRangeRemoved(0, previousSize)
            dataList.addAll(poiItemList)
            notifyItemRangeInserted(0, poiItemList.size)
        }
    }

}