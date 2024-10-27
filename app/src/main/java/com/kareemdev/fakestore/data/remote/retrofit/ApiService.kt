package com.kareemdev.fakestore.data.remote.retrofit

import com.google.gson.JsonObject
import com.kareemdev.fakestore.data.remote.response.product.CartResponse
import com.kareemdev.fakestore.data.remote.response.product.CategoryListResponse
import com.kareemdev.fakestore.data.remote.response.product.DeleteCartResponse
import com.kareemdev.fakestore.data.remote.response.product.DetailProductResponse
import com.kareemdev.fakestore.data.remote.response.product.ProductResponse
import com.kareemdev.fakestore.data.remote.response.product.TitleCategoryResponse
import com.kareemdev.fakestore.data.remote.response.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService{

    @POST("auth/login")
    @Headers("Content-Type: application/json")
    suspend fun login(
        @Body request : JsonObject
    ): UserResponse?

    @GET("products")
    suspend fun getListProduct(
        @Header("Authorization") token: String
    ): ProductResponse?

    @GET("products/{id}")
    suspend fun getDetailProduct(
        @Header("Authorization") token: String,
        @Path("id") id : Int
    ): DetailProductResponse?

    @GET("products/{id}")
    suspend fun addToCart(
        @Header("Authorization") token: String?,
        @Path("id") id : Int,
        @Body request : JsonObject
    ): DetailProductResponse?

    @GET("products/categories")
    suspend fun getListTitleCategory(
        @Header("Authorization") token: String
    ): TitleCategoryResponse?

    @GET("products/category/{category}")
    suspend fun getListCategory(
        @Header("Authorization") token: String,
        @Path("category") category: String
    ): CategoryListResponse?

    @GET("carts")
    suspend fun getListCart(
        @Header("Authorization") token: String,
    ): CartResponse?

    @PUT("carts/{id}")
    suspend fun updateCart(
        @Header("Authorization") token: String,
        @Path("id") id : Int
    ): ProductResponse?

    @DELETE("carts/{id}")
    suspend fun deleteCart(
        @Header("Authorization") token: String,
        @Path("id") id : Int
    ): DeleteCartResponse?
}