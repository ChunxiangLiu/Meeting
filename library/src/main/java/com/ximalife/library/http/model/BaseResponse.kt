package com.ximalife.library.http.model

import com.google.gson.annotations.SerializedName

class BaseResponse<T>(

    @SerializedName(value = "code") var code: Int,
    @SerializedName(value = "msg") var msg: String? = "",
    @SerializedName(value = "data", alternate = arrayOf("da")) var data: T
)

class errorbean(var code:String,var msg :String)
class OptionT<T>(val value: T)


