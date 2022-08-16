package com.jy.meeting.common.fragment

import android.Manifest
import android.content.res.TypedArray
import android.os.Bundle
import android.text.TextUtils
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
import com.ximalife.library.base.BaseFragment
import com.ximalife.library.view.CustomTextView

class GuideFragmentEight :
    BaseFragment<FragmentGuideEightBinding>(FragmentGuideEightBinding::inflate) {

    lateinit var textArray: TypedArray

    var textList = ArrayList<String>()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        textArray = resources.obtainTypedArray(R.array.item_footprint_text)

        for (i in 0 until textArray.length()) {
            textList.add(textArray.getString(i).toString())
        }

        var adapter = object : CommonBaseRVAdapter<String>(R.layout.item_foot_print, textList) {
            override fun convertData(helper: BaseViewHolder, item: String) {
                if (!TextUtils.isEmpty(item)) {
                    var itemText = helper.getView<TextView>(R.id.tv_title)
                    itemText.setText(item)
//                    if (index == helper.adapterPosition) {
//                        itemText.setStrokeColorAndWidth(context!!.resources.getColor(R.color.color_FB5C67))
//                    } else {
//                        itemText.setStrokeColorAndWidth(context!!.resources.getColor(R.color.transparent))
//                    }
//                    helper.itemView.setOnClickListener {
//                        index = helper.adapterPosition
//                        notifyDataSetChanged()
//                    }
                }

            }

        }
        binding.mRecyclerView.adapter = adapter
    }

    override fun initListener() {

    }
}