package com.ximalife.library.http.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ximalife.library.http.IBaseViewModelEvent

/**
 * baseview的封装
 */
open class BaseViewModel : ViewModel(), IBaseViewModelEvent {


    val baseActionEvent = MutableLiveData<BaseViewModelEvent>()


    override fun showLoading(msg: String) {
        val event = BaseViewModelEvent(BaseViewModelEvent.SHOW_LOADING_DIALOG)
        event.message = msg
        baseActionEvent.value = event
    }

    override fun dismissLoading() {
        val event = BaseViewModelEvent(BaseViewModelEvent.DISMISS_LOADING_DIALOG)
        baseActionEvent.value = event
    }

    override fun showToast(msg: String) {
        val event = BaseViewModelEvent(BaseViewModelEvent.SHOW_TOAST)
        event.message = msg
        baseActionEvent.value = event
    }

    override fun finishView() {
        val event = BaseViewModelEvent(BaseViewModelEvent.FINISH)
        baseActionEvent.value = event
    }

    override fun pop() {
        val event = BaseViewModelEvent(BaseViewModelEvent.POP)
        baseActionEvent.value = event
    }

    override fun toLogin() {
        val event = BaseViewModelEvent(BaseViewModelEvent.TOLOGIN)
        baseActionEvent.value = event
    }
}