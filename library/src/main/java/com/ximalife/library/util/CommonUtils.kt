package com.ximalife.library.util

import java.util.regex.Pattern

object CommonUtils {

    fun isMobile(mobile: String): Boolean {
        val pattern = "^(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57]|19[89]|166)[0-9]{8}"
        val r = Pattern.compile(pattern)
        val m = r.matcher(mobile)
        return m.matches()
    }



}