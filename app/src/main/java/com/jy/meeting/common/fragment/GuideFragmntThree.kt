package com.jy.meeting.common.fragment

import android.os.Bundle
import android.text.TextUtils
import com.jy.meeting.databinding.FragmentGuideThreeBinding
import com.jy.meeting.view.dialog.EducationDialog
import com.jy.meeting.view.dialog.UserSchoolDialog
import com.ximalife.library.base.BaseFragment

class GuideFragmntThree :
    BaseFragment<FragmentGuideThreeBinding>(FragmentGuideThreeBinding::inflate),
    EducationDialog.onDialogItemClick, UserSchoolDialog.onItemClickListener {

    var userEducationName = ""
    var userSchoolName = ""

    override fun initView(savedInstanceState: Bundle?) {
        userSchoolName = "1111"
        if (!TextUtils.isEmpty(userEducationName) && !TextUtils.isEmpty(userSchoolName)) {


        }

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

    }

    override fun onClickItem(educationName: String) {
        binding.tvEducation.setText(educationName)
    }

    override fun onItemClick(schoolName: String) {

    }


}