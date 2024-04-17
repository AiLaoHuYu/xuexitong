package com.qq.xuexitong.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qq.xuexitong.App
import com.qq.xuexitong.base.BaseRvAdapter
import com.qq.xuexitong.R
import com.qq.xuexitong.activity.VideoDetailActivity
import com.qq.xuexitong.entity.VideoEntity

class RvAdapter(list: ArrayList<VideoEntity>) :
    BaseRvAdapter<VideoEntity, RvAdapter.MyViewHolder>(list) {

    private var mContext: Context = App.getInstance()
    private var contextList: ArrayList<VideoEntity> = list


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var video: ImageView = view.findViewById(R.id.video)
        private var time: TextView = view.findViewById(R.id.tv_time)

        fun bindData(data: VideoEntity, mContext: Context) {
            Log.d(App.TAG, "dataImgUrl " + data.imgUrl)
            Glide.with(mContext)
                .load(data.imgUrl)
                .into(video)
            time.text = data.videoLength
        }

    }

    override fun baseCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.rv_item, parent, false)
        return MyViewHolder(view)
    }

    override fun baseBindView(holder: MyViewHolder, position: Int) {
        holder.bindData(contextList[position], mContext)
    }

    override fun onItemClick(holder: MyViewHolder, position: Int) {
        //Item被点击后的回调
        Log.d(App.TAG, "onItemClick: $position")
        val intent = Intent(mContext, VideoDetailActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        val bundle = Bundle()
//        bundle.putString("imgUrl", contextList[position].imgUrl)
//        bundle.putString("userName",contextList[position].)
        intent.putExtra("imgUrl", contextList[position].imgUrl)
        App.getInstance().startActivity(intent)
    }

}