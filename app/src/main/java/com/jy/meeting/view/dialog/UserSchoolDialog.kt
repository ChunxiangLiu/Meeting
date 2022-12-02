package com.jy.meeting.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.res.TypedArray
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter
import com.contrarywind.listener.OnItemSelectedListener
import com.jy.meeting.R
import com.jy.meeting.databinding.DialogEducationSelectBinding
import com.jy.meeting.databinding.DialogUserSchoolBinding
import com.ximalife.library.util.DisplayUtil

class UserSchoolDialog : Dialog {
    lateinit var binding: DialogUserSchoolBinding

    var textList = ArrayList<String>()


    private lateinit var setClickItemListener: onItemClickListener


    lateinit var textArray: TypedArray

    var userEducation = ""

    constructor(context: Context) : this(context, 0)

    constructor(mContext: Context, themeResId: Int) : super(mContext, R.style.dialog) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogUserSchoolBinding.inflate(layoutInflater)
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
        binding.tvClose.setOnClickListener {
            dismiss()
        }

        binding.etSearchUserSchool.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })


    }

    @JvmName("setClickListener1")
    fun setClickItemListener(listener: onItemClickListener) {
        this.setClickItemListener = listener
    }


    interface onItemClickListener {
        fun onItemClick(schoolName: String)
    }


}