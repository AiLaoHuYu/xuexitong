package com.qq.xuexitong.activity

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.qq.xuexitong.R
import com.qq.xuexitong.base.BaseFragment
import com.qq.xuexitong.fragment.HomeFragment
import com.qq.xuexitong.fragment.MeFragment
import com.qq.xuexitong.fragment.MessageFragment
import com.qq.xuexitong.fragment.NoteFragment

class MainActivity : AppCompatActivity() {

    private lateinit var homeFragment: HomeFragment
    private lateinit var meFragment: MeFragment
    private lateinit var messageFragment: MessageFragment
    private lateinit var noteFragment: NoteFragment
    private lateinit var fragments: Array<BaseFragment>
    private lateinit var tvTitle: TextView

    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main_home -> {
                    replaceFragment(fragments[0], fragments[0].tag.toString())
                    tvTitle.text = "首页"
                    return@OnNavigationItemSelectedListener true
                }
                R.id.main_message -> {
                    replaceFragment(fragments[1], fragments[1].tag.toString())
                    tvTitle.text = "消息"
                    return@OnNavigationItemSelectedListener true
                }
                R.id.main_note -> {
                    replaceFragment(fragments[2], fragments[2].tag.toString())
                    tvTitle.text = "笔记"
                    return@OnNavigationItemSelectedListener true
                }
                R.id.main_me -> {
                    replaceFragment(fragments[3], fragments[3].tag.toString())
                    tvTitle.text = "我"
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        initView()
    }

    private fun initView() {
        val navigation: BottomNavigationView = findViewById(R.id.bv_menu)
        tvTitle = findViewById(R.id.tv_title)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.itemIconTintList = null
        navigation.itemTextColor =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
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
}