package com.ximalife.library.util

import android.graphics.Rect

class PointInRectUtil {
    companion object {
        fun pointInRect(x: Float, y: Float, width: Int, height: Int): Boolean {
            if (x < 0 || x > width) {
                return false

            } else if (y < 0 || y > height) {
                return false
            }else return true
        }
    }
}