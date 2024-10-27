package com.kareemdev.fakestore.data.remote.response.product

import com.google.gson.annotations.SerializedName

data class DetailProductResponse(
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("title")
    val title: String
)