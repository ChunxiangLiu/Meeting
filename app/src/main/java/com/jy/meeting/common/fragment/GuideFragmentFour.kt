package com.jy.meeting.common.fragment

import android.os.Bundle
import com.jy.meeting.AppApplication
import com.jy.meeting.databinding.FragmentGuideFourBinding
import com.ximalife.library.base.BaseFragment
import com.ximalife.library.util.event.EvenBusUtil
import com.ximalife.library.util.event.EventBusCode

class GuideFragmentFour :
    BaseFragment<FragmentGuideFourBinding>(FragmentGuideFourBinding::inflate) {

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initListener() {

        binding.tvUserJob.setOnClickListener {

        }

        binding.tvIncome.setOnClickListener {

        }

        binding.llBottomLayout.ivTop.setOnClickListener {
            EvenBusUtil.instance().postEventMesage(EventBusCode.FRAGMENT_GUIDE_TOP)
        }

        binding.llBottomLayout.tvNext.setOnClickListener {
            AppApplication.userBean.userJob = "工人"
            AppApplication.userBean.income = "10-20"
            EvenBusUtil.instance().postEventMesage(EventBusCode.FRAGMENT_GUIDE_NEXT)

        }
    }
}