package com.qq.xuexitong.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.qq.xuexitong.App
import com.qq.xuexitong.R
import com.qq.xuexitong.activity.LoginActivity
import com.qq.xuexitong.base.BaseFragment

class MeFragment : BaseFragment(), View.OnClickListener {

    private lateinit var rlLogin: RelativeLayout
    private lateinit var rlTodo: RelativeLayout
    private lateinit var rlClass: RelativeLayout
    private lateinit var rlNotebook: RelativeLayout
    private lateinit var rlYun: RelativeLayout
    private lateinit var rlGroup: RelativeLayout
    private lateinit var rlBookshelf: RelativeLayout
    private lateinit var rlSetting: RelativeLayout


    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.me_fragment, container, false)
        initView(view)
        return view
    }

    override fun initView(view: View) {
        rlLogin = view.findViewById(R.id.rl_login)
        rlTodo = view.findViewById(R.id.rl_todo)
        rlClass = view.findViewById(R.id.rl_class)
        rlNotebook = view.findViewById(R.id.rl_noteBook)
        rlYun = view.findViewById(R.id.rl_yun)
        rlGroup = view.findViewById(R.id.rl_group)
        rlBookshelf = view.findViewById(R.id.rl_bookshelf)
        rlSetting = view.findViewById(R.id.rl_setting)
        rlLogin.setOnClickListener(this)
        rlTodo.setOnClickListener(this)
        rlClass.setOnClickListener(this)
        rlNotebook.setOnClickListener(this)
        rlYun.setOnClickListener(this)
        rlGroup.setOnClickListener(this)
        rlBookshelf.setOnClickListener(this)
        rlSetting.setOnClickListener(this)
    }

    override fun initData() {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View?) {
        if (!App.isLogin) {
            val intent = Intent(App.getInstance(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            App.getInstance().startActivity(intent)
        }
    }
}