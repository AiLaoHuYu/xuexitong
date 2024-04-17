package com.qq.xuexitong.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragAdapter(fm: FragmentManager, fragments: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {

    private val mFragments: ArrayList<Fragment> = fragments

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "最新"
            1 -> return "文史"
            2 -> return "数理"
            3 -> return "宇宙"
        }
        return "null"
    }


}