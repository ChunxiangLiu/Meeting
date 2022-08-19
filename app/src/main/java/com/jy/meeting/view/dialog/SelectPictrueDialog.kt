package com.jy.meeting.view.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.jy.meeting.R
import com.jy.meeting.common.adapter.BitmapAdapter
import com.jy.meeting.databinding.DialogSelectPictrueBinding
import com.phz.photopicker.config.ImagePickerConstant
import com.phz.photopicker.config.SelectMode
import com.phz.photopicker.intent.PickImageIntent
import com.phz.photopicker.intent.PreViewImageIntent
import com.phz.photopicker.util.UsageUtil
import com.ximalife.library.Constant
import com.ximalife.library.util.DisplayUtil
import java.util.*

class SelectPictrueDialog : Dialog {
    lateinit var binding: DialogSelectPictrueBinding


    private lateinit var clickListener: onDialogItemClick

    /**
     * 点击GridView时点击的位置和路径，这2个默认值是0和{@link Constant.PLUS}
     */
    private val gridViewItemClickPosition = 0
    private val gridViewItemClickPath = ""


    /**
     * 允许上传照片最大数量
     */
    private val INT_MAXSIZE_IMG = 9

    private val REQUEST_CAMERA_CODE = 10
    private val REQUEST_PREVIEW_CODE = 20

    lateinit var adapter: BitmapAdapter


    /**
     * 图片路径，和graidview的填充器有关 (可能包含plus加号)
     */
    private val imagePathsList = java.util.ArrayList<String>()


    constructor(context: Context) : this(context, 0)

    constructor(mContext: Context, themeResId: Int) : super(mContext, R.style.dialog) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogSelectPictrueBinding.inflate(layoutInflater)
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


        //获取全部的图片
        toPickPhoto(gridViewItemClickPosition, gridViewItemClickPath)

        val cols = UsageUtil.getNumColumn(context)
        binding.myGridView.setNumColumns(cols)
        imagePathsList.add(Constant.PLUS)

        var adapter = BitmapAdapter(imagePathsList, context)
        binding.myGridView.setAdapter(adapter)


    }


    /**
     * 跳转到图片选择器
     */
    private fun toPickPhoto(position: Int, path: String) {
        if (Constant.PLUS.equals(path)) {
            val intent = PickImageIntent(context)
            //设置为多选模式
            intent.setSelectModel(SelectMode.MULTI)
            // 是否拍照
            intent.setIsShowCamera(false)
            //设置最多选择照片数量
            if (imagePathsList.size > 0 && imagePathsList.size < INT_MAXSIZE_IMG + 1) {
                // 最多选择照片数量
                intent.setSelectedCount(INT_MAXSIZE_IMG + 1 - imagePathsList.size)
            } else {
                intent.setSelectedCount(0)
            }
            // 已选中的照片地址， 用于回显选中状态
            ownerActivity!!.startActivityForResult(intent, REQUEST_CAMERA_CODE)
        } else {
            val intent = PreViewImageIntent(context)
            intent.setCurrentItem(position)
            if (imagePathsList.contains(Constant.PLUS)) {
                imagePathsList.remove(Constant.PLUS)
            }
            intent.setPhotoPaths(imagePathsList)
            ownerActivity!!.startActivityForResult(intent, REQUEST_PREVIEW_CODE)
        }
    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CAMERA_CODE -> if (data != null) {
                    val list = data.getStringArrayListExtra(ImagePickerConstant.EXTRA_RESULT)
                    updateGridView(list)
                }
                REQUEST_PREVIEW_CODE -> if (data != null) {
                    val ListExtra = data.getStringArrayListExtra(ImagePickerConstant.EXTRA_RESULT)
                    if (imagePathsList != null) {
                        imagePathsList.clear()
                    }
                    imagePathsList.addAll(ListExtra)
                    if (imagePathsList.size < INT_MAXSIZE_IMG) {
                        imagePathsList.add(Constant.PLUS)
                    }

                    adapter = BitmapAdapter(imagePathsList, context)
                    binding.myGridView.setAdapter(adapter)
                }
            }
        }
    }

    /**
     * 更新界面
     *
     * @param list 选择照片的路径列表
     */
    private fun updateGridView(list: ArrayList<String>) {
        if (imagePathsList.contains(Constant.PLUS)) {
            imagePathsList.remove(Constant.PLUS)
        }
        imagePathsList.addAll(list)
        /** 小于INT_MAXSIZE_IMG时显示添加图片item(也就是plus) */
        if (imagePathsList.size < INT_MAXSIZE_IMG) {
            imagePathsList.add(Constant.PLUS)
        }
        adapter = BitmapAdapter(imagePathsList, context)
        binding.myGridView.setAdapter(adapter)
    }


    private fun initListener() {
        binding.ivDialogClaose.setOnClickListener {
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

