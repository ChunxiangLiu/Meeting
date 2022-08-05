package com.ximalife.library.holder

import android.content.Context
import android.widget.Toast
import com.ximalife.library.R
import com.ximalife.library.base.BaseAppApplication
import com.ximalife.library.view.MyToast
import com.ximalife.library.view.ToastUtil

class ContextHolder {
    companion object {
        val context: Context by lazy { BaseAppApplication.context }
    }
}

class ToastHolder {
    companion object {
        fun showToast(context: Context = ContextHolder.context, msg: String) {
            ToastUtil.showToast(context, R.layout.toast_dialog,msg)
        }
    }
}