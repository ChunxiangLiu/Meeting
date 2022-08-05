package com.jy.meeting.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.view.Gravity
import android.view.WindowManager
import com.jy.meeting.R
import com.jy.meeting.databinding.DialogRegisterOrLoginBinding
import com.jy.meeting.view.utils.MyTextUtils
import com.ximalife.library.util.DisplayUtil

class RegisterOrLoginDialog : Dialog {
    lateinit var binding: DialogRegisterOrLoginBinding

    constructor(context: Context) : this(context, 0)

    constructor(mContext: Context, themeResId: Int) : super(mContext, R.style.dialog) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogRegisterOrLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setLayout(
            DisplayUtil.getScreenWidth(context),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window!!.setGravity(Gravity.BOTTOM)
        setCanceledOnTouchOutside(false)

        binding.tvRegisterOrlogin.setOnClickListener {

        }

        val str = SpannableString(context.getString(R.string.dailog_register_or_login_bt_tips))
        MyTextUtils().setTxtSpan(
            context,
            str,
            0,
             context.getString(R.string.user_ys)
        )
        MyTextUtils().setTxtSpan(
            context,
            str,
            1,
             context.getString(R.string.user_xy)
        )
        binding.tvTvRegisterOrloginText.setText(str)
        binding.tvTvRegisterOrloginText.setMovementMethod(LinkMovementMethod.getInstance())//不设置 没有点击事件
        binding.tvTvRegisterOrloginText.setHighlightColor(Color.TRANSPARENT) //设置点击后的颜色为透明

    }


}