package com.jy.meeting.common

import android.os.Handler
import com.jy.meeting.databinding.ActivitySplashBinding
import com.jy.meeting.view.dialog.RegisterOrLoginDialog
import com.ximalife.library.base.BaseActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    private val mHandler = Handler()
    private val delayTime: Long = 2000

    override fun getToolBar(): Int {
        return 0
    }

    override fun initView() {
        setShowRegisterOrLoginDialog()
    }


    private fun setShowRegisterOrLoginDialog() {
        mHandler.postDelayed(Runnable {
            var dialog = RegisterOrLoginDialog(this)
            dialog.show()
        }, delayTime)
    }

}