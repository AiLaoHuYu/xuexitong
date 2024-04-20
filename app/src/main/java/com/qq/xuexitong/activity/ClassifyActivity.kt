package com.qq.xuexitong.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qq.xuexitong.App
import com.qq.xuexitong.R
import com.qq.xuexitong.adapter.RvClassifyAdapter
import com.qq.xuexitong.data.Data

class ClassifyActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var classifyRv: RecyclerView
    private lateinit var classifyRvAdapter: RvClassifyAdapter
    private lateinit var ivBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classify)
        //隐藏菜单栏
        supportActionBar?.hide()
        initView()

    }

    private fun initView() {
        ivBack = findViewById(R.id.iv_back)
        ivBack.setOnClickListener(this)
        classifyRv = findViewById(R.id.rv_classify)
        classifyRvAdapter = RvClassifyAdapter(Data.titleList)
        classifyRv.layoutManager =
            GridLayoutManager(App.getInstance(), 3, GridLayoutManager.VERTICAL, false)
        classifyRv.adapter = classifyRvAdapter
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.iv_back->{
                finish()
            }
        }
    }


}