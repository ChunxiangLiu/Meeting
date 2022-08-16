package com.jy.meeting.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.res.TypedArray
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter
import com.contrarywind.listener.OnItemSelectedListener
import com.jy.meeting.R
import com.jy.meeting.databinding.DialogAvatarTipsBinding
import com.jy.meeting.databinding.DialogEducationSelectBinding
import com.ximalife.library.util.DisplayUtil

class AvatarTipsDialog : Dialog {
    lateinit var binding: DialogAvatarTipsBinding


    private lateinit var clickListener: onDialogItemClick


    constructor(context: Context) : this(context, 0)

    constructor(mContext: Context, themeResId: Int) : super(mContext, R.style.dialog) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogAvatarTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setLayout(
            DisplayUtil.getScreenWidth(context),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window!!.setGravity(Gravity.BOTTOM)
        setCanceledOnTouchOutside(false)

        initData()

        initListener()


    }

    private fun initData() {

    }

    private fun initListener() {
        binding.ivDialogClaose.setOnClickListener {
            dismiss()
        }
        binding.ctPhoto.setOnClickListener {
            //相册
            dismiss()
        }
        binding.ctCamer.setOnClickListener {
            //相机
            dismiss()
        }

    }

    @JvmName("setClickListener1")
    fun setClickListener(listener: onDialogItemClick) {
        this.clickListener = listener
    }


    interface onDialogItemClick {
        fun onClickItem(item: String)
    }


}