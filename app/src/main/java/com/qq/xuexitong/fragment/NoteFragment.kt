package com.qq.xuexitong.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qq.xuexitong.R
import com.qq.xuexitong.base.BaseFragment

class NoteFragment : BaseFragment() {

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.note_fragment, container, false)
        return view
    }

    override fun initView(view: View) {
        TODO("Not yet implemented")
    }

    override fun initData() {
        TODO("Not yet implemented")
    }


}