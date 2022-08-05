package com.jy.meeting.view.utils

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.util.Log
import com.jy.meeting.R
import com.jy.meeting.view.MyClickText

class MyTextUtils {

    fun setTxtSpan(mContext:Context,str: SpannableString, type: Int, rePlaceTxt: String) {
        var agreementList = str.toString().split(rePlaceTxt)
        var hanveDo = agreementList.get(0)
        for (index in 0..agreementList.size - 2) {
            Log.d("获取循环旅程开始", hanveDo)
            var agreement = MyClickText(mContext, type, mContext.resources.getColor(R.color.color_FC566A))
            str.setSpan(
                agreement, hanveDo.length, hanveDo.length + rePlaceTxt.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            hanveDo += rePlaceTxt
            hanveDo += agreementList.get(index + 1)
            Log.d("获取循环旅程结束", hanveDo)
        }
    }
}