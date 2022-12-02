package com.jy.meeting.common.fragment

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.View
import com.jy.meeting.R
import com.jy.meeting.databinding.FragmentGuideTwelveBinding
import com.ximalife.library.base.BaseFragment
import com.ximalife.library.util.MyClickSpan


class GuideFragmentTwelve :
    BaseFragment<FragmentGuideTwelveBinding>(FragmentGuideTwelveBinding::inflate) {


    override fun initView(savedInstanceState: Bundle?) {
        setPrivacyPolicy()
        setArtificial()


    }


    override fun initData() {


    }

    /**
     * 协议
     */
    private fun setPrivacyPolicy() {
        var privacyPolicy = resources.getString(R.string.str_authentication_privacy_policy)
        var style = SpannableStringBuilder()
        style.append(privacyPolicy)
        style.setSpan(object :
            MyClickSpan(mActivity, resources.getColor(R.color.color_333333), false) {
            override fun onClick(widget: View) {
                super.onClick(widget)
                //协议
                showToast("协议")
            }
        }, 2, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        binding.tvPrivacyPolicy.setHighlightColor(resources.getColor(R.color.transparent))
        binding.tvPrivacyPolicy.setMovementMethod(LinkMovementMethod.getInstance())//不设置点击会失效
        binding.tvPrivacyPolicy.setHintTextColor(resources.getColor(R.color.transparent))//不设置会有背景色
        binding.tvPrivacyPolicy.setText(style)
    }

    /**
     * 人工
     */
    private fun setArtificial() {
        var artificial = resources.getString(R.string.str_authentication_artificial)
        var style = SpannableStringBuilder()
        style.append(artificial)
        style.setSpan(object :
            MyClickSpan(mActivity, resources.getColor(R.color.color_FC566A), false) {
            override fun onClick(widget: View) {
                super.onClick(widget)
                //协议
                showToast("人工")
            }
        }, 5, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        binding.tvArtificial.setHighlightColor(resources.getColor(R.color.transparent))
        binding.tvArtificial.setMovementMethod(LinkMovementMethod.getInstance())//不设置点击会失效
        binding.tvArtificial.setHintTextColor(resources.getColor(R.color.transparent))//不设置会有背景色
        binding.tvArtificial.setText(style)
    }


}