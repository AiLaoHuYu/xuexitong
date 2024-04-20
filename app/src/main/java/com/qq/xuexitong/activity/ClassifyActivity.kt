package com.qq.xuexitong.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qq.xuexitong.App
import com.qq.xuexitong.R
import com.qq.xuexitong.adapter.RvClassifyAdapter
import com.qq.xuexitong.data.Data

class ClassifyActivity: AppCompatActivity(){

    private lateinit var classifyRv:RecyclerView
    private lateinit var classifyRvAdapter:RvClassifyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classify)
        //隐藏菜单栏
        supportActionBar?.hide()
        initView()

    }

    private fun initView() {
        classifyRv = findViewById(R.id.rv_classify)
        classifyRvAdapter = RvClassifyAdapter(Data.titleList)
        classifyRv.layoutManager = GridLayoutManager(App.getInstance(),3,GridLayoutManager.VERTICAL,false)
        classifyRv.adapter=classifyRvAdapter
    }


}