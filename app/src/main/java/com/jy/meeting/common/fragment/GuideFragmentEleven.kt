package com.jy.meeting.common.fragment

import android.os.Bundle
import com.jy.meeting.common.adapter.SearchTagAdapter
import com.jy.meeting.databinding.FragmentGuideElevenBinding
import com.jy.meeting.databinding.FragmentGuideTenBinding
import com.ximalife.library.base.BaseFragment
import com.ximalife.library.http.model.TextModel
import com.ximalife.library.view.flow.FlowTagLayout
import com.ximalife.library.view.flow.OnTagSelectListener
import kotlinx.android.synthetic.main.item_dailog_foot_print.view.*

class GuideFragmentEleven :
    BaseFragment<FragmentGuideElevenBinding>(FragmentGuideElevenBinding::inflate) {

    var textList = ArrayList<TextModel>()

    var selectPosition = 0//选中的文本

    override fun initView(savedInstanceState: Bundle?) {


    }

    override fun initData() {
        textList.add(TextModel("java", false))
        textList.add(TextModel("javaEE", false))
        textList.add(TextModel("javaME", false))
        textList.add(TextModel("c", false))
        textList.add(TextModel("php", false))
        textList.add(TextModel("ios", false))
        textList.add(TextModel("c++", false))
        textList.add(TextModel("c#", false))
        textList.add(TextModel("Android", false))

        val historyAdapter = SearchTagAdapter(activity)
        //设置限制显示行数
        binding.mFlowLayout.setLimitLineCount(3)
        binding.mFlowLayout.setAdapter(historyAdapter)
        //设置流布局显示模式，单选，多选，点击
        binding.mFlowLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI)
        historyAdapter.clearAndAddAll(textList)

        //获取监听选中标签List
        binding.mFlowLayout.setOnTagSelectListener(object : OnTagSelectListener {
            override fun onItemSelect(parent: FlowTagLayout?, selectedList: MutableList<Int>) {
                for (condListBean in textList) {
                    condListBean.isCheck = false
                }

                for (pos in selectedList) {
                    textList.get(pos).isCheck = true
                }

                historyAdapter.notifyDataSetChanged()
            }

        })


    }


}