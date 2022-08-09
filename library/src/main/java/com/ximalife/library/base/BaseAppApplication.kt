package com.ximalife.library.base

import android.app.Application

abstract class BaseAppApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        lateinit var application: BaseAppApplication

        fun getInstance(): BaseAppApplication {
            return application
        }


    }


}
