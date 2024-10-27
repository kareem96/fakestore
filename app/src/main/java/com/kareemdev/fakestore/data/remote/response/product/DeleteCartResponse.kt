package com.kareemdev.fakestore.data.remote.response.product

import com.google.gson.annotations.SerializedName

data class DeleteCartResponse(
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("userId")
    val userId: String
)