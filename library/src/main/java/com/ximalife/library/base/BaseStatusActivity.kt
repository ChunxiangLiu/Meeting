package com.ximalife.library.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.ximalife.library.R

abstract class BaseStatusActivity<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) : BaseActivity<VB>(inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (findViewById<View>(R.id.back) != null) {
            findViewById<View>(R.id.back).setOnClickListener { onBackPressed() }
        }

        //设置标题文字
        if (findViewById<TextView>(R.id.v_title) != null) {
            findViewById<TextView>(R.id.v_title).text = setTitle()
        }
    }

    abstract fun setTitle(): String
}