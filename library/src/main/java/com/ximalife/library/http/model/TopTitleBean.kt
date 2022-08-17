package com.ximalife.library.http.model

class TopTitleBean {
    var firstPath: String? = null
    var name: String? = null
    var num = 0

    constructor(firstPath: String?, name: String?, num: Int) {
        this.firstPath = firstPath
        this.name = name
        this.num = num
    }
}