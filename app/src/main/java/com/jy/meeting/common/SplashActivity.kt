package com.jy.meeting.common

import android.os.Handler
import com.jy.meeting.databinding.ActivitySplashBinding
import com.jy.meeting.view.dialog.RegisterOrLoginDialog
import com.ximalife.library.Constant
import com.ximalife.library.base.BaseActivity
import com.ximalife.library.util.SPUtils
import com.ximalife.library.util.StartActivityUtil

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    private val mHandler = Handler()
    private val delayTime: Long = 2000


    override fun initView() {
        if (SPUtils.get(this, Constant.ISLOGIN, false) as Boolean) {
            StartActivityUtil.startActivity(this, GuidActivity::class.java)
        } else {
            setShowRegisterOrLoginDialog()
        }
    }


    private fun setShowRegisterOrLoginDialog() {
        mHandler.postDelayed(Runnable {
            var dialog = RegisterOrLoginDialog(this)
            dialog.show()
        }, delayTime)
    }

}