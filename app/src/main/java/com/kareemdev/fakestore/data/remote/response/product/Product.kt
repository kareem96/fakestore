package com.kareemdev.fakestore.data.remote.response.product


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("productId")
    val productId: Int,
    @SerializedName("quantity")
    val quantity: Int
)