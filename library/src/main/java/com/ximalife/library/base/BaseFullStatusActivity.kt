package com.ximalife.library.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.ximalife.library.R
import com.ximalife.library.util.ScreenUtil

abstract class BaseFullStatusActivity<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) : BaseActivity<VB>(inflate) {

    /**
     * true 黑色 false 白色
     */
    open fun setStatusColor() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        SoftKeyboardFixerForFullscreen.assistActivity(this)
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
            ScreenUtil.setLightStatusBar(this, setStatusColor())
        }

        //填充状态栏高度
        if (findViewById<View>(R.id.v_status) != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                val layoutParams = findViewById<View>(R.id.v_status).getLayoutParams()
                layoutParams.height = getStatusBarHeight()
                findViewById<View>(R.id.v_status).setLayoutParams(layoutParams)
            }
        }


        if (findViewById<View>(R.id.back) != null) {
            findViewById<View>(R.id.back).setOnClickListener { onBackPressed() }
        }

        //设置标题文字
        if (findViewById<TextView>(R.id.v_title) != null) {
            findViewById<TextView>(R.id.v_title).text = setTitle()
        }
    }

    open fun setTitle() =""
}