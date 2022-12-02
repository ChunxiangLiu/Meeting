package com.jy.meeting.common.fragment

import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import com.jy.meeting.AppApplication
import com.jy.meeting.R
import com.jy.meeting.UsersSeversManage
import com.jy.meeting.databinding.FragmentGuideTwoBinding
import com.ximalife.library.base.BaseFragment
import com.ximalife.library.http.model.GuideMessageModel
import com.ximalife.library.util.event.EvenBusUtil
import com.ximalife.library.util.event.EventBusCode
import kotlinx.android.synthetic.main.item_flow_text.view.*

class GuideFragmentTwo : BaseFragment<FragmentGuideTwoBinding>(FragmentGuideTwoBinding::inflate) {

    var selectGenderFlag = false//性别选择器标识

    override fun initView(savedInstanceState: Bundle?) {

        binding.tvUserAge.text =
            String.format(
                resources.getString(R.string.str_guid_two_text_age_tips),
                binding.seekbarUserAge.progress
            )
        binding.tvUserHeight.text =
            String.format(
                resources.getString(R.string.str_guid_two_text_age_tips),
                binding.seekbarUserHeight.progress
            )


        binding.llBottomLayout.tvNext.setBackgroundResource(R.drawable.drawable_guid_bt_select_bg)


    }


    override fun initListener() {
        binding.ivGenderMan.setOnClickListener {
            selectGenderFlag = false
            setGenderFlagView(selectGenderFlag)
        }

        binding.ivGenderWoman.setOnClickListener {
            selectGenderFlag = true
            setGenderFlagView(selectGenderFlag)
        }

        binding.seekbarUserAge.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvUserAge.text = String.format(
                    resources.getString(R.string.str_guid_two_text_age_tips),
                    progress
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        binding.seekbarUserHeight.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvUserHeight.text = String.format(
                    resources.getString(R.string.str_guid_two_text_height_tips),
                    progress
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        binding.llBottomLayout.ivTop.setOnClickListener {
            EvenBusUtil.instance().postEventMesage(EventBusCode.FRAGMENT_GUIDE_TOP)
        }

        binding.llBottomLayout.tvNext.setOnClickListener {
            AppApplication.userBean.gender = if (selectGenderFlag) "女" else "男"
            AppApplication.userBean.age = binding.seekbarUserAge.progress
            AppApplication.userBean.height = binding.seekbarUserHeight.progress
            EvenBusUtil.instance().postEventMesage(EventBusCode.FRAGMENT_GUIDE_NEXT)

        }


    }

    /**
     * 性别选择view
     */
    private fun setGenderFlagView(selectGenderFlag: Boolean) {
        if (selectGenderFlag) {
            binding.ivGenderWoman.setImageResource(R.mipmap.ic_woman_select)
            binding.ivGenderMan.setImageResource(R.mipmap.ic_man_unselect)
        } else {
            binding.ivGenderWoman.setImageResource(R.mipmap.ic_woman_unselect)
            binding.ivGenderMan.setImageResource(R.mipmap.ic_man_select)
        }
    }

}