package com.ximalife.library.http

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.ximalife.library.holder.ToastHolder
import com.ximalife.library.http.viewmodel.BaseViewModel
import com.ximalife.library.http.viewmodel.BaseViewModelEvent

interface IBaseViewModelEvent {

    fun showLoading(msg: String)

    fun showLoading() {
        showLoading("")
    }

    fun dismissLoading()

    fun showToast(msg: String)

    fun finishView()

    fun pop()

    fun toLogin()
}


interface IIBaseViewModelEventObserver : IBaseViewModelEvent {

    fun initViewModel(): BaseViewModel? {
        return null
    }

    fun initViewModelList(): MutableList<BaseViewModel>? {
        return null
    }

    fun getLifecycleOwner(): LifecycleOwner

    fun initViewModelEvent() {
        var list: MutableList<BaseViewModel>? = null
        val initViewModelList = initViewModelList()
        if (initViewModelList.isNullOrEmpty()) {
            val baseViewModel = initViewModel()
            baseViewModel?.let {
                list = mutableListOf(baseViewModel)
            }
        } else {
            list = initViewModelList
        }

        list?.let {
            observeEvent(list!!)
        }
    }


    fun observeEvent(viewModelList: MutableList<BaseViewModel>) {
        for (viewModel in viewModelList) {
            viewModel.baseActionEvent.observe(getLifecycleOwner(), Observer { it ->
                it?.let {
                    when (it.action) {
                        BaseViewModelEvent.SHOW_LOADING_DIALOG -> {
                            showLoading(it.message)
                        }
                        BaseViewModelEvent.DISMISS_LOADING_DIALOG -> {
                            dismissLoading()
                        }
                        BaseViewModelEvent.SHOW_TOAST -> {
                            showToast(it.message)
                        }
                        BaseViewModelEvent.FINISH -> {
                            finishView()
                        }
                        BaseViewModelEvent.POP -> {
                            pop()
                        }
                        BaseViewModelEvent.TOLOGIN -> {
                            toLogin()
                        }
                    }
                }

            })
        }
    }

    fun getLContext(): Context

    fun <T> startActivity(clazz: Class<T>) {
        getLContext().startActivity(Intent(getLContext(), clazz))
    }


    override fun showToast(msg: String) {
        ToastHolder.showToast(msg = msg)
    }
}
