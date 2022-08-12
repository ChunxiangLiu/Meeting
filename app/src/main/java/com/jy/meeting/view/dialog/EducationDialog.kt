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
import com.jy.meeting.databinding.DialogEducationSelectBinding
import com.ximalife.library.util.DisplayUtil

class EducationDialog : Dialog {
    lateinit var binding: DialogEducationSelectBinding

    var textList = ArrayList<String>()


    private lateinit var clickListener: onDialogItemClick


    lateinit var textArray: TypedArray

    var userEducation = ""

    constructor(context: Context) : this(context, 0)

    constructor(mContext: Context, themeResId: Int) : super(mContext, R.style.dialog) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogEducationSelectBinding.inflate(layoutInflater)
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
        binding.wheelView.setCyclic(false)

        textArray = context.getResources().obtainTypedArray(R.array.item_education_text)
        for (i in 0 until textArray.length()) {
            textList.add(textArray.getString(i).toString())
        }
        binding.wheelView.setAdapter(ArrayWheelAdapter(textList))

    }

    private fun initListener() {
        binding.ivEducationDialogClaose.setOnClickListener {
            dismiss()
        }
        binding.tvEducationDialogSure.setOnClickListener {
            if (TextUtils.isEmpty(userEducation)) {
                userEducation = textArray.getString(0).toString()
            }
            clickListener.onClickItem(userEducation)
            dismiss()
        }

        binding.wheelView.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(index: Int) {
                userEducation = textArray.getString(index).toString()
//                Log.e("test", userEducation + "======选择的学历===========")
            }

        })
    }

    @JvmName("setClickListener1")
    fun setClickListener(listener: onDialogItemClick) {
        this.clickListener = listener
    }


    interface onDialogItemClick {
        fun onClickItem(item: String)
    }


}