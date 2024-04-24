package com.qq.xuexitong.entity


data class UserResult(val result: String, val from: String, val data: UserResultEntity)

class UserEntity {
    lateinit var no: String
    lateinit var name: String
    lateinit var password: String
    lateinit var phone: String
    var age: Int = -1
    var sex: Int = -1
    var email: String? = null
    var roleId: Int = 2
    var isDelete = -1
    var createdUser: String? = null
    var createdTime: Long? = null
    var modifiedUser: String? = null
    var modifiedTime: Long? = null
}

class UserResultEntity {
    var code: Int = -1
    var message: String? = null
    var entity: UserEntity? = null
}