package com.kareemdev.fakestore.data.remote.response.user

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("token")
    val token: String?,
)