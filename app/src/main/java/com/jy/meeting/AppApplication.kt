package com.jy.meeting

import android.os.Handler
import com.jy.meeting.bean.UserBean
import com.ximalife.library.base.BaseAppApplication
import java.util.*


class AppApplication : BaseAppApplication() {


    companion object {
        var application: AppApplication? = null
        var mHandler = Handler()
        var userBean = UserBean()
        fun getInstance(): AppApplication {
            return application!!
        }
    }


    override fun onCreate() {
        super.onCreate()
        application = this

        initApp()

    }

    private fun initApp() {


    }


}