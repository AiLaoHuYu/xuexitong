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
import com.qq.xuexitong.base.BaseRvAdapter
import com.qq.xuexitong.data.Data
import com.qq.xuexitong.entity.VideoEntity
import com.qq.xuexitong.view.PullRefreshView
import java.lang.ref.WeakReference

class HomeFragment : BaseFragment() {

    companion object {
        var mInstance: HomeFragment = HomeFragment()
        fun getInstance(): HomeFragment {
            if (mInstance == null) {
                synchronized(HomeFragment::class.java) {
                    if (mInstance == null) {
                        mInstance = HomeFragment()
                    }
                }
            }
            return mInstance
        }

        val weakHandler by lazy { WeakReferenceHandler(getInstance()) }
    }

    private final val TAG: String = HomeFragment::class.java.simpleName
    private lateinit var rvLatestNews: RecyclerView
    private lateinit var rvVerticalLatestNews: RecyclerView
    private lateinit var mPullRefreshView: PullRefreshView
    private lateinit var mAdapter: RvAdapter
    private lateinit var mVerticalAdapter: RvVerticalAdapter
    private var currentType = ""


    class WeakReferenceHandler(obj: HomeFragment) : Handler(Looper.getMainLooper()) {
        private val mRef: WeakReference<HomeFragment> = WeakReference(obj)

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            mRef.get()?.run {
                Log.d(TAG, "handleMessage: ${msg!!.what} ${msg!!.obj} ")
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
        initView(view)
        return view
    }

    override fun initView(view: View) {
        mPullRefreshView = view.findViewById(R.id.pull_refresh)
        //top的横向的RecyclerView
        rvVerticalLatestNews = mPullRefreshView.horizonRecyclerView!!
        rvVerticalLatestNews.layoutManager = LinearLayoutManager(
            App.getInstance(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        mVerticalAdapter = RvVerticalAdapter(Data.titleList)
        rvVerticalLatestNews.adapter = mVerticalAdapter
        mPullRefreshView.addVerticalRecyclerView(rvVerticalLatestNews)
        //下方主题的RecyclerView
        rvLatestNews = mPullRefreshView.recyclerView!!
        rvLatestNews.layoutManager = LinearLayoutManager(App.getInstance())
        mAdapter = RvAdapter(Data.videoList)
        rvLatestNews.adapter = mAdapter
        mPullRefreshView.addRecyclerView(rvLatestNews)

        mVerticalAdapter.setOnClickListener(object : BaseRvAdapter.OnClickListener {
            override fun onClick(text: String) {
                currentType = text
                notifyDataChangeByType(text)
            }
        })


        //设置刷新完成的监听回调
        mPullRefreshView.setOnRefreshListener(object : PullRefreshView.OnRefreshListener {
            override fun onRefresh() {
                //刷新操作
                mPullRefreshView.postDelayed({
                    notifyDataChangeByType(currentType)
                    mPullRefreshView.refreshComplete()
                }, 1000)
            }

        })

    }

    override fun initData() {
        Data.addTitleList()
        Data.addVideoList()
    }

    override fun onDestroy() {
        super.onDestroy()
        weakHandler.removeCallbacksAndMessages(null)
    }

    //提醒数据变化，并修改对应的list
    fun notifyDataChangeByType( type:String){
        val changeVideoList = ArrayList<VideoEntity>()
        changeVideoList.add(
            VideoEntity(
                "https://images.pexels.com/photos/20065715/pexels-photo-20065715.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                "1:22",
                "美丽的风景图",
                "111",
                "wowwowo"
            )
        )
        changeVideoList.add(
            VideoEntity(
                "https://images.pexels.com/photos/20442701/pexels-photo-20442701.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                "1:32",
                "美丽的风景111图",
                "53",
                "12455"
            )
        )
        changeVideoList.add(
            VideoEntity(
                "https://images.pexels.com/photos/20398872/pexels-photo-20398872.jpeg?auto=compress&cs=tinysrgb&w=600&lazy=load",
                "1:33",
                "111",
                "111",
                "22221"
            )
        )
        Data.changeVideoList(changeVideoList)
        mAdapter.notifyData(changeVideoList)
    }

}