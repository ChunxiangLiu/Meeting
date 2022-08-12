package com.jy.meeting.common.fragment

import android.os.Bundle
import android.widget.SeekBar
import com.jy.meeting.R
import com.jy.meeting.databinding.FragmentGuideTwoBinding
import com.ximalife.library.base.BaseFragment
import com.ximalife.library.http.model.GuideMessageModel
import com.ximalife.library.util.event.EvenBusUtil
import com.ximalife.library.util.event.EventBusCode

class GuideFragmntTwo : BaseFragment<FragmentGuideTwoBinding>(FragmentGuideTwoBinding::inflate) {

    var selectGenderFlag = false//性别选择器标识

    override fun initView(savedInstanceState: Bundle?) {
        binding.tvUserAge.setText(
            binding.seekbarUserAge.progress.toString()
                    + resources.getString(R.string.str_guid_two_text_age_tips)
        )
        binding.tvUserHeight.setText(
            binding.seekbarUserHeight.progress.toString() + resources.getString(
                R.string.str_guid_two_text_height
            )
        )


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
                binding.tvUserAge.setText(progress.toString() + resources.getString(R.string.str_guid_two_text_age_tips))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        binding.seekbarUserHeight.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvUserHeight.setText(progress.toString() + resources.getString(R.string.str_guid_two_text_height))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })


    }

    fun setGenderFlagView(selectGenderFlag: Boolean) {
        if (selectGenderFlag) {
            binding.ivGenderWoman.setImageResource(R.mipmap.ic_woman_select)
            binding.ivGenderMan.setImageResource(R.mipmap.ic_man_unselect)
        } else {
            binding.ivGenderWoman.setImageResource(R.mipmap.ic_woman_unselect)
            binding.ivGenderMan.setImageResource(R.mipmap.ic_man_select)
        }
    }

}