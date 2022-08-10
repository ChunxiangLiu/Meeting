package com.jy.meeting

import android.os.Handler
import com.ximalife.library.base.BaseAppApplication


class AppApplication : BaseAppApplication() {

    companion object {
        var application: AppApplication? = null
        var mHandler = Handler()
        fun getInstance(): AppApplication {
            return application!!
        }
    }


    override fun onCreate() {
        super.onCreate()
        application = this
    }










}