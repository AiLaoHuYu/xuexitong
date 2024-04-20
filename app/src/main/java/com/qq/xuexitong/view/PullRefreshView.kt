package com.qq.xuexitong.view


import androidx.recyclerview.widget.LinearLayoutManager
import android.animation.ValueAnimator
import androidx.recyclerview.widget.RecyclerView
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.*

import androidx.annotation.Nullable
import com.qq.xuexitong.App
import com.qq.xuexitong.R
import com.qq.xuexitong.activity.ClassifyActivity
import com.qq.xuexitong.utils.DensityUtil


class PullRefreshView(context: Context?, @Nullable attrs: AttributeSet?, defStyleAttr: Int) :
    LinearLayout(context, attrs, defStyleAttr) {
    /**
     * 默认初始状态
     */
    @State
    private var mState = State.INIT

    /**
     * 是否被拖拽
     */
    private var mIsDragging = false

    /**
     * 上下文
     */
    private val mContext: Context?

    /**
     * RecyclerView
     */
    var recyclerView: RecyclerView? = null

    /**
     * RecyclerViewVertical
     */
    var horizonRecyclerView: RecyclerView? = null

    /**
     * 刷新图标
     */
    var ivRefresh: ImageView? = null

    /**
     * 顶部刷新头
     */
    private var mHeaderView: View? = null

    /**
     * 初始Y的坐标
     */
    private var mInitMotionY = 0

    /**
     * 上一次Y的坐标
     */
    private var mLastMotionY = 0

    /**
     * 手指触发滑动的临界距离
     */
    private var mSlopTouch = 0

    /**
     * 触发刷新的临界值
     */
    private val mRefreshHeight = 200

    /**
     * 滑动时长
     */
    private val mDuring = 300

    /**
     * 用户刷新监听器
     */
    private var mOnRefreshListener: OnRefreshListener? = null

    /**
     * 刷新文字提示
     */
    private var mRefreshTip: TextView? = null

    /**
     * 是否可拖拽, 因为在刷新头自由滑动和刷新状态的时候，
     * 我们应该保持界面不被破坏
     */
    private var mIsCanDrag = true

    /**
     * 头部布局
     */
    private var mHeaderLayoutParams: LayoutParams? = null

    /**
     * 列表布局
     */
    private var mListLayoutParams: LayoutParams? = null

    /**
     * 顶部的父布局
     */
    private var relativeLayout: RelativeLayout? = null

    /**
     * 全局的父布局
     */
    private var scrollView: ScrollView? = null

    /**
     * ScrollView只能包裹一层
     */
    private var linearLayout: LinearLayout? = null

    /**
     * 列表布局
     */
    private var mHorizonListLayoutParams: RelativeLayout.LayoutParams? = null

    /**
     * 属性动画
     */
    private var mValueAnimator: ValueAnimator? = null
    /// 分割 ///
    /**
     * @param context
     */
    constructor(context: Context?) : this(context, null) {}

    /**
     * @param context
     * @param attrs
     */
    constructor(context: Context?, @Nullable attrs: AttributeSet?) : this(context, attrs, 0) {}

    /**
     * 设置RecyclerView
     *
     * @param recyclerView
     */
    fun addRecyclerView(recyclerView: RecyclerView?) {
        if (recyclerView == null) {
            return
        }
        val view: View = findViewWithTag(LIST_TAG)
        if (view != null) {
            removeView(view)
        }
        this.recyclerView = recyclerView
        this.recyclerView!!.tag = LIST_TAG
        addView(recyclerView, mListLayoutParams)
    }

    /**
     * 设置RecyclerView
     *
     * @param recyclerView
     */
    fun addVerticalRecyclerView(recyclerView: RecyclerView?) {
        if (recyclerView == null) {
            return
        }
        val view: View = findViewWithTag(VERTICAL_LIST_TAG)
        if (view != null) {
            removeView(view)
        }
        this.horizonRecyclerView = recyclerView
        this.horizonRecyclerView!!.tag = LIST_TAG
        if (relativeLayout != null) {
            removeView(relativeLayout)
            relativeLayout!!.removeView(horizonRecyclerView)
        }
        relativeLayout!!.addView(horizonRecyclerView, mListLayoutParams)
        addView(relativeLayout)
    }

    /**
     * 设置自定义刷新头部
     * @param headerView
     */
    fun addHeaderView(headerView: View?) {
        if (headerView == null) {
            return
        }
        val view: View = findViewWithTag(HEADER_TAG)
        if (view != null) {
            removeView(view)
        }
        mHeaderView = headerView
        mHeaderView!!.tag = HEADER_TAG
        addView(mHeaderView, mHeaderLayoutParams)
    }

    /**
     * @param onRefreshListener
     */
    fun setOnRefreshListener(onRefreshListener: OnRefreshListener?) {
        mOnRefreshListener = onRefreshListener
    }

    /**
     * 初始化View
     */
    private fun initView() {
        orientation = VERTICAL
        val context: Context = context
        /** 1、添加刷新头Header  */
        mHeaderView = LayoutInflater.from(context).inflate(R.layout.layout_refresh_header, null)
        mHeaderView!!.tag = HEADER_TAG
        mRefreshTip = mHeaderView!!.findViewById(R.id.content)
        ivRefresh = mHeaderView!!.findViewById(R.id.iv_refresh)
        mHeaderLayoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            DensityUtil.dip2px(mContext!!, 500F)
        )
        this.addView(mHeaderView, mHeaderLayoutParams)
        //顶部的view布局
        relativeLayout = RelativeLayout(context)
        //右侧的图片
        val button = Button(context)
        //设置图片大小
        val layoutParams = RelativeLayout.LayoutParams(80, 80)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
        layoutParams.rightMargin = 10
        layoutParams.topMargin = 10
        button.layoutParams = layoutParams
        button.setBackgroundResource(R.drawable.bars)
        button.setOnClickListener{
            Log.d(TAG, "点击了分类的按钮")
            val intent = Intent(context,ClassifyActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            App.getInstance().startActivity(intent)
        }
        relativeLayout!!.addView(button)
        //顶部的RecyclerView
        horizonRecyclerView = RecyclerView(context)
        horizonRecyclerView!!.tag = VERTICAL_LIST_TAG
        horizonRecyclerView!!.setPadding(0, 0, 100, 0)
        mHorizonListLayoutParams =
            RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 80)
        mHorizonListLayoutParams!!.addRule(RelativeLayout.CENTER_VERTICAL)
        relativeLayout!!.addView(horizonRecyclerView, mHorizonListLayoutParams)
        this.addView(relativeLayout)
        /** 2、添加内容RecyclerView  */
        recyclerView = RecyclerView(context)
        recyclerView!!.tag = LIST_TAG
        mListLayoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        this.addView(recyclerView, mListLayoutParams)
        /** 3、一开始的时候要让Header看不见，设置向上的负paddingTop  */
        setPadding(0, -DensityUtil.dip2px(mContext, 500F), 0, 0)
        val viewConfiguration = ViewConfiguration.get(context)
        mSlopTouch = viewConfiguration.scaledTouchSlop
        setState(State.INIT)
    }

    /**
     * 设置状态，每个状态下，做不同的事情
     *
     * @param state 状态
     */
    private fun setState(@State state: Int) {
        Log.e(TAG, "setState: $state")
        when (state) {
            State.INIT -> initState()
            State.DRAGGING -> dragState()
            State.READY -> readyState()
            State.REFRESHING -> refreshState()
            State.FLING -> flingState()
            else -> {}
        }
        mState = state
    }

    /**
     * 处理初始化状态方法
     */
    private fun initState() {

        // 只有在初始状态时，恢复成可拖拽
        ivRefresh!!.visibility = VISIBLE
        ivRefresh!!.setBackgroundResource(R.drawable.loading)
        mIsCanDrag = true
        mIsDragging = false
        mRefreshTip!!.text = "下拉刷新"
    }

    /**
     * 处理拖拽时方法
     */
    private fun dragState() {
        mIsDragging = true
    }

    /**
     * 拖拽距离超过header高度时，如何处理
     */
    private fun readyState() {
        mRefreshTip!!.text = "松手刷新"
    }

    /**
     * 用户刷新时，如何处理
     */
    private fun refreshState() {
        if (mOnRefreshListener != null) {
            mOnRefreshListener!!.onRefresh()
        }
        mIsCanDrag = false
        ivRefresh!!.setBackgroundResource(R.drawable.refresh)
        mRefreshTip!!.text = "正在刷新,请稍后..."
    }

    /**
     * 自由滚动时，如何处理
     */
    private fun flingState() {
        mIsDragging = false
        mIsCanDrag = false
        /** 自由滚动状态可以从两个状态进入：
         * 1、READY状态。
         * 2、其他状态。
         *
         * ！滑动均需要平滑滑动
         */
        if (mState == State.READY) {
            Log.e(TAG, "flingState: 从Ready状态开始自由滑动")
            // 从准备状态进入，刷新头滑到 200 的位置
            smoothScroll(scrollY, -mRefreshHeight)
        } else {
            Log.e(TAG, "flingState: 松手后，从其他状态开始自由滑动")
            // 从刷新状态进入，刷新头直接回到最初默认的位置
            // 即: 滑出界面，ScrollY 变成 0
            smoothScroll(scrollY, 0)
            ivRefresh!!.visibility = GONE
        }
    }

    /**
     * 光滑滚动
     * @param startPos 开始位置
     * @param targetPos 结束位置
     */
    private fun smoothScroll(startPos: Int, targetPos: Int) {

        // 如果有动画正在播放，先停止
        if (mValueAnimator != null && mValueAnimator!!.isRunning) {
            mValueAnimator!!.cancel()
            mValueAnimator!!.end()
            mValueAnimator = null
        }

        // 然后开启动画
        mValueAnimator = ValueAnimator.ofInt(scrollY, targetPos)
        mValueAnimator!!.addUpdateListener(AnimatorUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            scrollTo(0, value)
            if (scrollY == targetPos) {
                if (targetPos != 0) {
                    setState(State.REFRESHING)
                } else {
                    setState(State.INIT)
                }
            }
        })
        mValueAnimator!!.duration = mDuring.toLong()
        mValueAnimator!!.start()
    }

    /**
     * 是否准备好触发下拉的状态了
     */
    private val isReadyToPull: Boolean
        private get() {
            if (recyclerView == null) {
                return false
            }
            val manager = recyclerView!!.layoutManager as LinearLayoutManager? ?: return false
            if (recyclerView != null && recyclerView!!.adapter != null) {
                val child: View = recyclerView!!.getChildAt(0)
                val height: Int = child.height
                Log.e(
                    TAG,
                    "height: $height  recyclerView!!.height: ${recyclerView!!.height} manager.findFirstVisibleItemPosition(): ${manager.findFirstCompletelyVisibleItemPosition()}"
                )
                return if (height > recyclerView!!.height) {
                    child.top === 0 && manager.findFirstVisibleItemPosition() == 0
                } else {
                    manager.findFirstCompletelyVisibleItemPosition() == 0
                }
            }
            return false
        }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        Log.e(TAG, "onInterceptTouchEvent: action = $action ev.y = ${ev.y}")
        if (!mIsCanDrag) {
            return true
        }
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mIsDragging = false
            return false
        }
        if (mIsDragging && action == MotionEvent.ACTION_MOVE) {
            return true
        }
        when (action) {
            MotionEvent.ACTION_MOVE -> {
                val diff = (ev.y - mLastMotionY).toInt()
                if (Math.abs(diff) > mSlopTouch && diff > 1 && isReadyToPull) {
                    mLastMotionY = ev.y.toInt()
                    mIsDragging = true
                }
            }
            MotionEvent.ACTION_DOWN -> if (isReadyToPull) {
                setState(State.INIT)
                mInitMotionY = ev.y.toInt()
                mLastMotionY = ev.y.toInt()
            }
            else -> {}
        }
        return mIsDragging
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        Log.e(TAG, "onTouchEvent: action = $action")
        if (!mIsCanDrag) {
            return false
        }
        when (action) {
            MotionEvent.ACTION_DOWN -> if (isReadyToPull) {
                setState(State.INIT)
                mInitMotionY = event.y.toInt()
                mLastMotionY = event.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> if (mIsDragging) {
                mLastMotionY = event.y.toInt()
                setState(State.DRAGGING)
                pullScroll()
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                mIsDragging = false
                setState(State.FLING)
            }
            else -> {}
        }
        return true
    }

    /**
     * 下拉移动界面，拉出刷新头
     */
    private fun pullScroll() {
        /** 滚动值 = 初始值 - 结尾值  */
        val scrollValue = (mInitMotionY - mLastMotionY) / 3
        if (scrollValue > 0) {
            scrollTo(0, 0)
            return
        }
        if (Math.abs(scrollValue) > mRefreshHeight
            && mState == State.DRAGGING
        ) {
            // 约定：如果偏移量超过 200(这个值，表示是否可以启动刷新的临界值，可任意定),
            // 那么状态变成 State.READY
            Log.e(TAG, "pullScroll: 超过了触发刷新的临界值")
            setState(State.READY)
        }
        scrollTo(0, scrollValue)
    }

    /**
     * 刷新完成，需要调用方主动发起，才能完成将刷新头收起
     */
    fun refreshComplete() {
        mRefreshTip!!.text = "刷新完成！"
        setState(State.FLING)
    }

    annotation class State {
        companion object {
            /**
             * 初始状态
             */
            var INIT = 1

            /**
             * 手指拖拽状态
             */
            var DRAGGING = 2

            /**
             * 就绪状态，松开手指后，可以刷新
             */
            var READY = 3

            /**
             * 刷新状态，这个状态下，用户用于发起刷新请求
             */
            var REFRESHING = 4

            /**
             * 松开手指，顶部自然回弹的状态，有两种表现
             * 1、手指释放时的高度大于刷新头的高度。
             * 2、手指释放时的高度小于刷新头的高度。
             */
            var FLING = 5
        }
    }

    /**
     * 用户刷新状态的操作
     */
    interface OnRefreshListener {
        fun onRefresh()
    }

    companion object {
        /**
         * 头部tag
         */
        const val HEADER_TAG = "HEADER_TAG"

        /**
         * 列表tag
         */
        const val LIST_TAG = "LIST_TAG"

        /**
         * 列表tag
         */
        const val VERTICAL_LIST_TAG = "VERTICAL_LIST_TAG"

        /**
         * tag
         */
        private const val TAG = "PullRefreshView"
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    init {
        mContext = context
        initView()
    }
}
