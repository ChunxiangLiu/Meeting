package com.ximalife.library.base

import android.app.Application

abstract class BaseAppApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        application = this
        context = this
    }

    companion object {
        var application: BaseAppApplication? = null
        lateinit var context: BaseAppApplication

        fun getInstance(): BaseAppApplication {
            return application!!
        }


    }


}
