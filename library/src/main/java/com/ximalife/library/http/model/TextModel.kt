package com.ximalife.library.http.model

class TextModel {
    var text: String = ""
    var isCheck: Boolean = false

    constructor(text: String, isCheck: Boolean) {
        this.text = text
        this.isCheck = isCheck
    }
}