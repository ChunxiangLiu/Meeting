package com.ximalife.library.http

interface RequestCallback<T> {
    fun onSuccess(data: T?)
}

interface RequestMultiplyCallback<T> : RequestCallback<T> {
    fun onFail(e: BaseException)
}