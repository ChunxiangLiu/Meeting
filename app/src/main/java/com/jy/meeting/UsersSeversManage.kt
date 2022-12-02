package com.jy.meeting

/**
 * 用户管理类
 */
class UsersSeversManage {
    private var usersSeversManage: UsersSeversManage? = null

    fun getInstance(): UsersSeversManage? {
        if (usersSeversManage == null) {
            usersSeversManage = UsersSeversManage()
        }
        return usersSeversManage
    }

    //昵称
    var nickName: String? = null
    //性别
    var gender: String? = null
    //年龄
    var age: Int? = null
    //身高
    var height: Int? = null


}