package com.ximalife.library.http

import java.lang.RuntimeException

sealed class BaseException(var errorMessage: String, val code: String = HttpConfig.CODE_UNKNOWN.toString()) : RuntimeException(errorMessage)

class ServerResultException(message: String, code: String = HttpConfig.CODE_UNKNOWN.toString()) : BaseException(message, code)

class ApiException(message: String, code: String = HttpConfig.CODE_API_EXCEPTION.toString()) : BaseException(message, code)