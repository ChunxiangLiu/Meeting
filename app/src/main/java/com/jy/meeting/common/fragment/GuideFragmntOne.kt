package com.jy.meeting.common.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.jy.meeting.databinding.FragmentGuideOneBinding
import com.ximalife.library.base.BaseFragment
import com.ximalife.library.http.model.GuideMessageModel
import com.ximalife.library.util.RandomNameUtils
import com.ximalife.library.util.event.EvenBusUtil
import com.ximalife.library.util.event.EventBusCode

class GuideFragmntOne : BaseFragment<FragmentGuideOneBinding>(FragmentGuideOneBinding::inflate) {

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initListener() {

        binding.tvRandomName.setOnClickListener {
            binding.etUserNickname.setText(RandomNameUtils.fullName())
        }


        binding.etUserNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                var guideMessageModel = GuideMessageModel()
                guideMessageModel.text = s.toString()
                guideMessageModel.position = 0
                EvenBusUtil.instance()
                    .postEventMesage(EventBusCode.FRAGMNET_GUIDE_ONE, guideMessageModel)
            }

        })

    }
}