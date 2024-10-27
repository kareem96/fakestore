package com.kareemdev.fakestore.data.remote.response

import com.google.gson.annotations.SerializedName

data class BaseResponseObject<T>(
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("message")
    val message: String? = null
)