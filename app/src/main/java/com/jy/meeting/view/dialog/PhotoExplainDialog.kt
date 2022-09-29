package com.jy.meeting.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.res.TypedArray
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import androidx.viewpager.widget.ViewPager
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter
import com.contrarywind.listener.OnItemSelectedListener
import com.jy.meeting.R
import com.jy.meeting.common.adapter.FootPrintAdapter
import com.jy.meeting.databinding.DialogAvatarTipsBinding
import com.jy.meeting.databinding.DialogEducationSelectBinding
import com.jy.meeting.databinding.DialogFootPrintBinding
import com.jy.meeting.databinding.DialogPhotoExplainBinding
import com.jy.meeting.view.utils.ZoomOutPageTransformer
import com.ximalife.library.http.model.TxtWithPhotoModel
import com.ximalife.library.util.DisplayUtil
import java.lang.reflect.Array

/**
 * 我的足迹 图片描述弹窗
 */
class PhotoExplainDialog : Dialog {
    lateinit var binding: DialogPhotoExplainBinding


//    private lateinit var clickListener: onDialogItemClick


    var txtWithPhotoModelList = ArrayList<TxtWithPhotoModel>()


    constructor(context: Context) : this(context, 0)

    constructor(mContext: Context, themeResId: Int) : super(mContext, R.style.dialog) {

    }

    constructor(
        context: Context,
        txtWithPhotoModelLists: ArrayList<TxtWithPhotoModel>,
    ) : super(context, R.style.dialog) {

        this.txtWithPhotoModelList = txtWithPhotoModelLists
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogPhotoExplainBinding.inflate(layoutInflater)
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
        binding.mViewPager.adapter = FootPrintAdapter(context, txtWithPhotoModelList)
//        binding.mViewPager.pageMargin = 20
        binding.mViewPager.offscreenPageLimit = txtWithPhotoModelList.size
        binding.mViewPager.setPageTransformer(true, ZoomOutPageTransformer())//设置画廊模式
        binding.mViewPager.setCurrentItem(0)
    }

    private fun initListener() {

        binding.mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        //返回
        binding.ivDialogClaose.setOnClickListener {
            dismiss()
        }

        //跳过
        binding.tvJump.setOnClickListener {
            dismiss()
        }


//
//        binding.ctPhoto.setOnClickListener {
//            if (clickListener != null) {
//                clickListener.onClickOK()
//            }
//            dismiss()
//        }


    }

//    @JvmName("setClickListener1")
//    fun setClickListener(listener: onDialogItemClick) {
//        this.clickListener = listener
//    }
//
//
//    interface onDialogItemClick {
//        fun onClickOK()
//        fun onClickClose()
//    }


}