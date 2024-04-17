package com.qq.xuexitong.entity

class VideoEntity {
    var imgUrl: String? = null
    var videoLength: String? = null
    var videoDesc: String? = null
    var videoTitle: String? = null
    var videoAuthor: String? = null

    constructor(imgUrl: String, videoLength: String) {
        this.imgUrl = imgUrl
        this.videoLength = videoLength
        videoDesc = "这个是默认的备注"
        videoTitle = "这个是默认的标题"
        videoAuthor = "这个是默认的作者"
    }

    constructor(imgUrl: String, videoLength: String, videoDesc: String) {
        this.imgUrl = imgUrl
        this.videoLength = videoLength
        this.videoDesc = videoDesc
        videoTitle = "这个是默认的标题"
        videoAuthor = "这个是默认的作者"
    }

    constructor(imgUrl: String, videoLength: String, videoDesc: String,videoTitle:String) {
        this.imgUrl = imgUrl
        this.videoLength = videoLength
        this.videoDesc = videoDesc
        this.videoTitle = videoTitle
        videoAuthor = "这个是默认的作者"
    }

    constructor(imgUrl: String, videoLength: String, videoDesc: String,videoTitle:String,videoAuthor:String) {
        this.imgUrl = imgUrl
        this.videoLength = videoLength
        this.videoDesc = videoDesc
        this.videoTitle = videoTitle
        this.videoAuthor = videoAuthor
    }



}