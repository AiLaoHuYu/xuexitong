package com.qq.xuexitong.data

import com.qq.xuexitong.entity.VideoEntity

open class Data {

    companion object{
        var videoList: ArrayList<VideoEntity> = ArrayList()
        var titleList: ArrayList<String> = ArrayList()


        fun addVideoList(){
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
        }

        fun addTitleList(){
            titleList.add("最新")
            titleList.add("文史")
            titleList.add("数理")
            titleList.add("宇宙")
            titleList.add("地球")
            titleList.add("海洋")
            titleList.add("生命")
        }


    }


}