package com.jy.meeting.common.fragment

import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.os.Bundle
import android.text.TextUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.jy.meeting.R
import com.jy.meeting.common.adapter.CommonBaseRVAdapter
import com.jy.meeting.databinding.FragmentGuideFiveBinding
import com.jy.meeting.databinding.FragmentGuideFourBinding
import com.jy.meeting.databinding.FragmentGuideSixBinding
import com.jy.meeting.view.DoubleSlideSeekBar
import com.ximalife.library.base.BaseFragment
import com.ximalife.library.view.CustomTextView

class GuideFragmentSix :
    BaseFragment<FragmentGuideSixBinding>(FragmentGuideSixBinding::inflate) {

    lateinit var textArray: TypedArray

    var textList = ArrayList<String>()
    var index = 0

    override fun initView(savedInstanceState: Bundle?) {


    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun initData() {
        textArray = activity!!.getResources().obtainTypedArray(R.array.item_love_text)

        for (i in 0 until textArray.length()) {
            textList.add(textArray.getString(i).toString())
        }

        var adapter = object : CommonBaseRVAdapter<String>(R.layout.item_love_target, textList) {
            override fun convertData(helper: BaseViewHolder, item: String) {
                if (!TextUtils.isEmpty(item)) {
                    var itemText = helper.getView<CustomTextView>(R.id.item_love_target_text)
                    itemText.setText(item)
                    if (index == helper.adapterPosition) {
                        itemText.setStrokeColorAndWidth(context!!.resources.getColor(R.color.color_FB5C67))
                    } else {
                        itemText.setStrokeColorAndWidth(context!!.resources.getColor(R.color.transparent))
                    }
                    helper.itemView.setOnClickListener {
                        index = helper.adapterPosition
                        notifyDataSetChanged()
                    }
                }

            }

        }
        binding.mRecyclerView.adapter = adapter

    }

    override fun initListener() {
        binding.dbarUserAge.setOnRangeListener(object : DoubleSlideSeekBar.onRangeListener {
            override fun onRange(low: Float, big: Float) {
                binding.userLoveAge.setText(String.format("%.0f" , low) + "~" + String.format("%.0f" , big) + context!!.resources.getString(R.string.str_guid_two_text_age_tips))

            }

        })
    }
}