package com.jy.meeting.common.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import com.jy.meeting.AppApplication
import com.jy.meeting.R
import com.jy.meeting.UsersSeversManage
import com.jy.meeting.databinding.FragmentGuideOneBinding
import com.ximalife.library.base.BaseFragment
import com.ximalife.library.util.RandomNameUtils
import com.ximalife.library.util.event.EvenBusUtil
import com.ximalife.library.util.event.EventBusCode


class GuideFragmentOne : BaseFragment<FragmentGuideOneBinding>(FragmentGuideOneBinding::inflate) {
    var userNickName = ""

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initListener() {

        //随机昵称
        binding.tvRandomName.setOnClickListener {
            userNickName = RandomNameUtils.fullName()
            binding.etUserNickname.setText(userNickName)
            setBottomViewBg(userNickName)

        }


        binding.etUserNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                userNickName = s.toString()
                setBottomViewBg(userNickName)
            }

        })
        //下一步
        binding.llBottomLayout.tvNext.setOnClickListener {
            if (!TextUtils.isEmpty(binding.etUserNickname.text.toString())) {
                userNickName = binding.etUserNickname.text.toString()
                setNickName(userNickName)
                binding.llBottomLayout.tvNext.setBackgroundResource(R.drawable.drawable_guid_bt_select_bg)
                EvenBusUtil.instance().postEventMesage(EventBusCode.FRAGMENT_GUIDE_NEXT)
            } else {
                binding.llBottomLayout.tvNext.setBackgroundResource(R.drawable.drawable_guid_bt_bg)
            }
        }

    }

    /**
     * 设置文本
     */
    private fun setNickName(nickName: String) {
        binding.etUserNickname.setText(nickName)
        AppApplication.userBean.nickName = nickName
    }

    /**
     * 设置底部按钮背景颜色
     */
    private fun setBottomViewBg(nickName: String) {
        if (!TextUtils.isEmpty(nickName)) {
            binding.llBottomLayout.tvNext.setBackgroundResource(R.drawable.drawable_guid_bt_select_bg)
        } else {
            binding.llBottomLayout.tvNext.setBackgroundResource(R.drawable.drawable_guid_bt_bg)
        }
    }
}