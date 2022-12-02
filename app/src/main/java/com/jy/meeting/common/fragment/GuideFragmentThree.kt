package com.jy.meeting.common.fragment

import android.os.Bundle
import android.text.TextUtils
import com.jy.meeting.AppApplication
import com.jy.meeting.R
import com.jy.meeting.databinding.FragmentGuideThreeBinding
import com.jy.meeting.view.dialog.EducationDialog
import com.jy.meeting.view.dialog.UserSchoolDialog
import com.ximalife.library.base.BaseFragment
import com.ximalife.library.util.event.EvenBusUtil
import com.ximalife.library.util.event.EventBusCode

class GuideFragmentThree :
    BaseFragment<FragmentGuideThreeBinding>(FragmentGuideThreeBinding::inflate),
    EducationDialog.onDialogItemClick, UserSchoolDialog.onItemClickListener {

    var userEducationName = ""
    var userSchoolName = ""

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initListener() {

        binding.tvEducation.setOnClickListener {
            var educationDialog = EducationDialog(getLContext())
            educationDialog.setClickListener(this)
            educationDialog.show()
        }

        binding.tvUserSchool.setOnClickListener {
            var userSchoolDialog = UserSchoolDialog(getLContext())
            userSchoolDialog.setClickItemListener(this)
            userSchoolDialog.show()
        }

        binding.llBottomLayout.ivTop.setOnClickListener {
            EvenBusUtil.instance().postEventMesage(EventBusCode.FRAGMENT_GUIDE_TOP)
        }

        binding.llBottomLayout.tvNext.setOnClickListener {

            if (TextUtils.isEmpty(userEducationName)) {
                showToast("学历不能为空")
                return@setOnClickListener
            } else {
                binding.llBottomLayout.tvNext.setBackgroundResource(R.drawable.drawable_guid_bt_select_bg)
                AppApplication.userBean.education = binding.tvEducation.text.toString().trim()
                AppApplication.userBean.schoolName = "大学名称"
                EvenBusUtil.instance().postEventMesage(EventBusCode.FRAGMENT_GUIDE_NEXT)
            }
        }

    }

    override fun onClickItem(educationName: String) {
        userEducationName = educationName
        binding.tvEducation.text = userEducationName
    }

    override fun onItemClick(schoolName: String) {

    }


}