package com.jy.meeting.common

import android.text.*
import android.text.style.AbsoluteSizeSpan
import com.jy.meeting.R
import com.jy.meeting.databinding.ActivityRegisterOrLoginBinding
import com.ximalife.library.base.BaseActivity
import com.ximalife.library.util.CommonUtils

class RegisterOrLoginActivity :
    BaseActivity<ActivityRegisterOrLoginBinding>(ActivityRegisterOrLoginBinding::inflate) {

    var numText = ""



    override fun initView() {

        //设置hint的字体大小
        var ss = SpannableString(resources.getString(R.string.str_register_yzm_tips))
        var sizeSpan = AbsoluteSizeSpan(14, true)
        ss.setSpan(sizeSpan, 0, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.etNum.setHint(SpannedString(ss))


        binding.etNum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (!TextUtils.isEmpty(s.toString())) {
                    numText = s.toString();
                    if (CommonUtils.isMobile(numText)) {
                        binding.registerOrLogin.setBackgroundResource(R.mipmap.ic_dialog_register_or_login_bt_bg)
                    } else {
                        binding.registerOrLogin.setBackgroundResource(R.mipmap.ic_register_bt_unselect)
                    }
                }
            }

        })

    }

    override fun initListener() {
        binding.registerOrLogin.setOnClickListener {

        }

    }

}