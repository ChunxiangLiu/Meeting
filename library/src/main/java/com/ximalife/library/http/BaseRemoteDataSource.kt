package com.ximalife.library.http

import android.annotation.SuppressLint
import com.ximalife.library.http.model.BaseResponse
import com.ximalife.library.http.model.OptionT
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

open class BaseRemoteDataSource(private val baseViewModelEvent: IBaseViewModelEvent) {


    /**
     * 获取api
     */
    protected fun <T : Any> getService(clz: Class<T>, host: String): T {
        return RetrofitManagement.instance.getService(clz, host)
    }

    protected fun <T : Any> getService(clz: Class<T>, host: String, auth: String): T {
        return RetrofitManagement.instance.getService(clz, host, auth)
    }


    /**
     * 显示dialog框
     */
    protected fun <T> execute(observable: Observable<BaseResponse<T>>, callback: RequestCallback<T>) {
            execute(observable, BaseSubscriber(callback,baseViewModelEvent), false)
    }

    /**
     * 不显示dialog框
     */
    protected fun <T> executeQuietly(observable: Observable<BaseResponse<T>>, callback: RequestCallback<T>) {
            execute(observable, BaseSubscriber(callback,baseViewModelEvent), true)

    }

    protected fun <T> execute(
        observable: Observable<BaseResponse<T>>,
        callback: RequestCallback<T>,
        showdialog: Boolean
    ) {
        execute(observable, BaseSubscriber(callback,baseViewModelEvent), showdialog)
    }


    @SuppressLint("CheckResult")
    private fun <T> execute(observable: Observable<BaseResponse<T>>, observer: Observer<OptionT<T>>, quietly: Boolean) {
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                if (!quietly) {
                    showLoadinig()
                }
            }.doFinally {
                if (!quietly) {
                    dismissLoading()
                }
            }.flatMap(object : Function<BaseResponse<T>, ObservableSource<OptionT<T>>> {
                override fun apply(t: BaseResponse<T>): ObservableSource<OptionT<T>> {
                    when {
                        t.code==200 -> {
                            val optionl: OptionT<T> = OptionT(t.data)
                            return createData(optionl)
                        }
                        else -> {
                            dismissLoading()
                            throw ServerResultException(t.msg ?: t.msg?:"未知错误", "")
                        }
                    }
                }

            }).subscribeWith(observer)
    }


    private fun <T> createData(t: T): Observable<T> {
        return Observable.create { emitter ->
            try {
                emitter.onNext(t)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    private fun showLoadinig() {
        baseViewModelEvent.showLoading()
    }

    private fun dismissLoading() {
        baseViewModelEvent.dismissLoading()
    }

    private fun showToast(msg: String) {
        baseViewModelEvent.showToast(msg)
    }
}