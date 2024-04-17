package com.qq.xuexitong.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qq.xuexitong.App
import com.qq.xuexitong.R
import com.qq.xuexitong.adapter.RvAdapter
import com.qq.xuexitong.adapter.RvVerticalAdapter
import com.qq.xuexitong.base.BaseFragment
import com.qq.xuexitong.entity.VideoEntity
import com.qq.xuexitong.view.PullRefreshView
import java.lang.ref.WeakReference

class HomeFragment : BaseFragment() {

    private final val TAG: String = HomeFragment::class.java.simpleName
    private lateinit var rvLatestNews: RecyclerView
    private lateinit var rvVerticalLatestNews: RecyclerView
    private lateinit var mPullRefreshView: PullRefreshView
    private lateinit var videoList: ArrayList<VideoEntity>
    private lateinit var titleList: ArrayList<String>
    private lateinit var mAdapter: RvAdapter
    private lateinit var mVerticalAdapter: RvVerticalAdapter
    private val weakHandler by lazy { WeakReferenceHandler(this) }
    private var lastY: Int = 0
    private var scrollY: Int = 0

    class WeakReferenceHandler(obj: HomeFragment) : Handler(Looper.getMainLooper()) {
        private val mRef: WeakReference<HomeFragment> = WeakReference(obj)

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            mRef.get()?.run {
                when (msg!!.what) {
                    else -> {}
                }
            }
        }
    }

    private var mContext: Context = App.getInstance()

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(App.TAG, "走进首页Fragment的初始化")
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        initData()
        mPullRefreshView = view.findViewById(R.id.pull_refresh)
        //top的横向的RecyclerView
//        rvVerticalLatestNews = view.findViewById(R.id.rv_title)
        rvVerticalLatestNews = mPullRefreshView.horizonRecyclerView!!
        rvVerticalLatestNews.layoutManager = LinearLayoutManager(
            App.getInstance().applicationContext,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        mVerticalAdapter = RvVerticalAdapter(titleList)
        rvVerticalLatestNews.adapter = mVerticalAdapter
        mPullRefreshView.addVerticalRecyclerView(rvVerticalLatestNews)
        //下方主题的RecyclerView
//        rvLatestNews = view.findViewById(R.id.rv_home)
        rvLatestNews = mPullRefreshView.recyclerView!!
        rvLatestNews.layoutManager = LinearLayoutManager(App.getInstance().applicationContext)
        mAdapter = RvAdapter(videoList)
        rvLatestNews.adapter = mAdapter
        mPullRefreshView.addRecyclerView(rvLatestNews)
        //设置刷新完成的监听回调
        mPullRefreshView.setOnRefreshListener(object : PullRefreshView.OnRefreshListener {
            override fun onRefresh() {
                //刷新操作
                mPullRefreshView.postDelayed({
                    mPullRefreshView.refreshComplete()
                }, 1000)
            }

        })

        return view
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initData() {
        videoList = ArrayList()
        titleList = ArrayList()
        videoList.add(
            VideoEntity(
                "https://images.pexels.com/photos/20065715/pexels-photo-20065715.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                "1:22",
                "美丽的风景图",
                "111",
                "wowwowo"
            )
        )
        videoList.add(
            VideoEntity(
                "https://images.pexels.com/photos/20442701/pexels-photo-20442701.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                "1:22",
                "美丽的风景图",
                "111",
                "wowwowo"
            )
        )
        videoList.add(
            VideoEntity(
                "https://images.pexels.com/photos/20398872/pexels-photo-20398872.jpeg?auto=compress&cs=tinysrgb&w=600&lazy=load",
                "1:22",
                "美丽的风景图",
                "111",
                "wowwowo"
            )
        )

        titleList.add("最新")
        titleList.add("文史")
        titleList.add("数理")
        titleList.add("宇宙")
        titleList.add("地球")
        titleList.add("海洋")
        titleList.add("生命")
    }

    override fun onDestroy() {
        super.onDestroy()
        weakHandler.removeCallbacksAndMessages(null)
    }

}