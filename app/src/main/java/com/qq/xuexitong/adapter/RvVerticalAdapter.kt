package com.qq.xuexitong.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.qq.xuexitong.App
import com.qq.xuexitong.R
import com.qq.xuexitong.base.BaseRvAdapter

class RvVerticalAdapter(list: ArrayList<String>) :
    BaseRvAdapter<String, RvVerticalAdapter.MyViewHolder>(list) {

    private var mContext: Context = App.getInstance()
    private var contextList: ArrayList<String> = list
    private var mSelectedItem = -1
    private val buttonList: ArrayList<RadioButton> = ArrayList()


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: RadioButton = view.findViewById(R.id.home_title)
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
        buttonList.add(holder.title)
        holder.title.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (mSelectedItem >= 0)
                    buttonList[mSelectedItem].isChecked = false
                mSelectedItem = position
                onItemClick(holder, position)
            }
        }

    }

    override fun onItemClick(holder: MyViewHolder, position: Int) {
        //Item被点击后的回调
        Log.d(App.TAG, "onItemClick: $position title: ${holder.title.text}")
        holder.title.isChecked = true
        listener.onClick(holder.title.text as String)
    }

}