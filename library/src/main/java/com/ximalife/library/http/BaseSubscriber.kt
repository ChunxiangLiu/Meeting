package com.ximalife.library.http

import com.ximalife.library.holder.ToastHolder
import com.ximalife.library.http.exception.DefaultException
import com.ximalife.library.http.model.OptionT
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import java.io.IOException

class BaseSubscriber<T> constructor(private val requestCallback: RequestCallback<T>,private val baseViewModelEvent: IBaseViewModelEvent) : DisposableObserver<OptionT<T>>() {
    override fun onComplete() {
    }

    override fun onNext(t: OptionT<T>) {
        requestCallback.onSuccess(t.value)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        val msg = e.message ?: "未知错误"
            if (requestCallback is RequestMultiplyCallback) {
                if(e is IOException){
                    requestCallback.onFail(ServerResultException("网络异常，请稍后重试",HttpConfig.CODE_UNKNOWN_NEKWORK.toString()))
                }else if(e is HttpException){
                    if(e.code() == 500){
                        requestCallback.onFail(ServerResultException("系统繁忙",HttpConfig.CODE_UNKNOWN_NEKWORK.toString()))
                    }else{
                        requestCallback.onFail(ServerResultException("网络异常，请稍后重试",HttpConfig.CODE_UNKNOWN_NEKWORK.toString()))
                    }

                }else if (e is BaseException) {
                    requestCallback.onFail(e)
                }else if(e is DefaultException){
                    requestCallback.onFail(ApiException(msg,HttpConfig.CODE_API_EXCEPTION.toString()))
                }else {
                    requestCallback.onFail(ServerResultException(msg))
                }
            } else {

                if(e is IOException){
                    ToastHolder.showToast(msg = "网络异常，请稍后重试")
                }else if(e is HttpException){
                    ToastHolder.showToast(msg = "系统繁忙")
                }else {
                    ToastHolder.showToast(msg = msg)
                }

            }

    }


    private fun gotoLogin() {
        baseViewModelEvent.toLogin()
    }

}