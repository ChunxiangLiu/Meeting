package com.jy.meeting.common.fragment

import android.Manifest
import android.content.res.TypedArray
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.jy.meeting.R
import com.jy.meeting.common.adapter.CommonBaseRVAdapter
import com.jy.meeting.databinding.FragmentGuideEightBinding
import com.jy.meeting.databinding.FragmentGuideFiveBinding
import com.jy.meeting.databinding.FragmentGuideFourBinding
import com.jy.meeting.databinding.FragmentGuideSevenBinding
import com.jy.meeting.view.dialog.AvatarTipsDialog
import com.jy.meeting.view.dialog.FootPrintTipsDialog
import com.ximalife.library.base.BaseFragment
import com.ximalife.library.http.model.TxtWithPhotoModel
import com.ximalife.library.util.SettingUtils
import com.ximalife.library.view.CustomTextView

class GuideFragmentEight :
    BaseFragment<FragmentGuideEightBinding>(FragmentGuideEightBinding::inflate),
    FootPrintTipsDialog.onDialogItemClick {

    lateinit var textArray: TypedArray
    lateinit var imaArray: TypedArray

    var itemClick = false

    var txtWithPhotoModelList = ArrayList<TxtWithPhotoModel>()

    lateinit var footPrintTipsDialog: FootPrintTipsDialog

    override fun initView(savedInstanceState: Bundle?) {

        footPrintTipsDialog = FootPrintTipsDialog(mActivity!!)
        footPrintTipsDialog.setClickListener(this)
    }

    override fun initData() {
        txtWithPhotoModelList = ArrayList()
        textArray = resources.obtainTypedArray(R.array.item_footprint_text)
        imaArray = resources.obtainTypedArray(R.array.item_footprint_img)
        for (i in 0 until textArray.length()) {
            var txtWithPhotoModel = TxtWithPhotoModel()
            txtWithPhotoModel.Intro = textArray.getString(i).toString()
            txtWithPhotoModel.DrawableId = imaArray.getResourceId(i, 0)
            txtWithPhotoModelList.add(txtWithPhotoModel)
        }

        var adapter = object : CommonBaseRVAdapter<TxtWithPhotoModel>(
            R.layout.item_foot_print,
            txtWithPhotoModelList
        ) {
            override fun convertData(helper: BaseViewHolder, item: TxtWithPhotoModel) {
                var itemText = helper.getView<TextView>(R.id.tv_title)

                SettingUtils.setTopImg(mActivity!!, itemText, item.DrawableId)
                if (helper.adapterPosition == 3 || helper.adapterPosition == 4) {
                    itemText.setText("")
                } else {
                    itemText.setText(item.Intro)
                }

                helper.itemView.setOnClickListener {
                    if (!itemClick) {
                        footPrintTipsDialog.show()
                    } else {
                        showToast("1111")
                    }
                }
            }

        }
        binding.mRecyclerView.adapter = adapter

    }

    override fun initListener() {

    }

    override fun onClickOK() {
        itemClick = true
    }

    override fun onClickClose() {
        itemClick = true
    }


}