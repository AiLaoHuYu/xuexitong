package com.qq.xuexitong.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.qq.xuexitong.App
import com.qq.xuexitong.R
import com.qq.xuexitong.base.BaseFragment
import com.qq.xuexitong.fragment.HomeFragment
import com.qq.xuexitong.fragment.MeFragment
import com.qq.xuexitong.fragment.MessageFragment
import com.qq.xuexitong.fragment.NoteFragment
import java.util.*

class MainActivity : AppCompatActivity(), BottomNavigationBar.OnTabSelectedListener {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var homeFragment: HomeFragment
    private lateinit var meFragment: MeFragment
    private lateinit var messageFragment: MessageFragment
    private lateinit var noteFragment: NoteFragment
    private lateinit var fragments: Array<BaseFragment>
    private lateinit var tvTitle: TextView
    private lateinit var navigation: BottomNavigationBar
    private var clickIndex = 0
    private var lastClickTime = Calendar.getInstance().timeInMillis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        initView()
    }

    private fun initView() {
        navigation = findViewById(R.id.bv_menu)
        tvTitle = findViewById(R.id.tv_title)
        navigation.setBarBackgroundColor(R.color.white)
        navigation.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        navigation.setMode(BottomNavigationBar.MODE_SHIFTING);//适应大小
        navigation.addItem(
            BottomNavigationItem(R.drawable.home, "首页").setInactiveIconResource(R.drawable.home)
                .setActiveColorResource(R.color.gray)
        )
            .addItem(
                BottomNavigationItem(
                    R.drawable.message,
                    "消息"
                ).setInactiveIconResource(R.drawable.message)
                    .setActiveColorResource(R.color.gray)
            )
            .addItem(
                BottomNavigationItem(R.drawable.note, "笔记").setInactiveIconResource(R.drawable.note)
                    .setActiveColorResource(R.color.gray)

            )
            .addItem(
                BottomNavigationItem(R.drawable.me, "我").setInactiveIconResource(R.drawable.me)
                    .setActiveColorResource(R.color.gray)
            )
            .setFirstSelectedPosition(0)//默认显示面板
            .initialise()//初始化
        navigation.setTabSelectedListener(this)
        homeFragment = HomeFragment()
        messageFragment = MessageFragment()
        noteFragment = NoteFragment()
        meFragment = MeFragment()
        fragments = arrayOf(homeFragment, messageFragment, noteFragment, meFragment)
        fragments.forEach {
            addFragment(it, it.tag.toString())
            hideFragment(it)
        }
        showFragment(fragments[0])
    }

    //添加Fragment到FragmentList中
    private fun addFragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, fragment, tag)
        transaction.commit()
    }

    // 清空fragmentList的所有Fragment，替换成新的Fragment，注意Fragment里面的坑
    private fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment, tag)
        transaction.commit()
    }

    //把Fragment设置成显示状态，但是并没有添加到FragmentList中
    private fun showFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.show(fragment)
        transaction.commit()
    }

    //把Fragment设置成显示状态，但是并没有添加到FragmentList中
    private fun hideFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.hide(fragment)
        transaction.commit()
    }

    override fun onTabSelected(position: Int) {
        var isQuickClick = false
        //防止快速点击
        if (!isInTime()) {
            isQuickClick = true
        }
        when (position) {
            0 -> {
                replaceFragment(fragments[0], fragments[0].tag.toString())
                tvTitle.text = "首页"
                clickIndex = 0
            }
            1 -> {
                if (App.isLogin) {
                    replaceFragment(fragments[1], fragments[1].tag.toString())
                    tvTitle.text = "消息"
                    clickIndex = 1
                } else {
                    if (isQuickClick) {
                        navigation.selectTab(clickIndex)
                        isQuickClick = false
                    } else {
                        navigation.selectTab(clickIndex)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                }
            }
            2 -> {
                if (App.isLogin) {
                    replaceFragment(fragments[2], fragments[2].tag.toString())
                    tvTitle.text = "笔记"
                    clickIndex = 2
                } else {
                    if (isQuickClick) {
                        navigation.selectTab(clickIndex)
                        isQuickClick = false
                    } else {
                        navigation.selectTab(clickIndex)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                }
            }
            3 -> {
                replaceFragment(fragments[3], fragments[3].tag.toString())
                tvTitle.text = "我"
                clickIndex = 3
            }
        }
    }

    private fun isInTime(): Boolean {
        val currentTime = Calendar.getInstance().timeInMillis
        Log.d(
            TAG,
            "currentTime: $currentTime lastTime: $lastClickTime currentTime - lastClickTime: ${currentTime - lastClickTime}"
        )
        if (currentTime - lastClickTime < 250) {
            lastClickTime = currentTime
            return false
        }
        lastClickTime = currentTime
        return true
    }

    override fun onTabUnselected(position: Int) {

    }

    override fun onTabReselected(position: Int) {

    }
}