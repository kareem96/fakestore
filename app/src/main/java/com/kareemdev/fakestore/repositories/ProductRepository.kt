package com.kareemdev.fakestore.repositories

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.kareemdev.fakestore.data.remote.response.BaseResult
import com.kareemdev.fakestore.data.remote.response.product.CartResponse
import com.kareemdev.fakestore.data.remote.response.product.CategoryListResponse
import com.kareemdev.fakestore.data.remote.response.product.DeleteCartResponse
import com.kareemdev.fakestore.data.remote.response.product.DetailProductResponse
import com.kareemdev.fakestore.data.remote.response.product.ProductItem
import com.kareemdev.fakestore.data.remote.response.product.ProductResponse
import com.kareemdev.fakestore.data.remote.response.product.TitleCategoryResponse
import com.kareemdev.fakestore.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun getListProduct(token: String): Flow<BaseResult<ProductResponse?>> = flow {
        try {
            val response = apiService.getListProduct(token)
            emit(BaseResult.Success(response))
        }catch (e:Exception){
            emit(BaseResult.Failed(e))
        }
    }

    fun getDetailProduct(token: String, id: Int): Flow<BaseResult<DetailProductResponse?>> = flow {
        try {
            val response = apiService.getDetailProduct(token, id)
            emit(BaseResult.Success(response))
        }catch (e:Exception){
            emit(BaseResult.Failed(e))
        }
    }

    fun getTitleCategory(token: String): Flow<BaseResult<TitleCategoryResponse?>> = flow {
        try {
            val response = apiService.getListTitleCategory(token)
            emit(BaseResult.Success(response))
        }catch (e:Exception){
            emit(BaseResult.Failed(e))
        }
    }

    fun getListCategory(token: String, category:String): Flow<BaseResult<CategoryListResponse?>> = flow {
        try {
            val response = apiService.getListCategory(token, category)
            emit(BaseResult.Success(response))
        }catch (e:Exception){
            emit(BaseResult.Failed(e))
        }
    }

    fun getListCart(token: String): Flow<BaseResult<CartResponse?>> = flow {
        try {
            val response = apiService.getListCart(token)
            emit(BaseResult.Success(response))
        }catch (e:Exception){
            emit(BaseResult.Failed(e))
        }
    }

    fun addToCart(token: String?, userId: Int, date: String, products: List<ProductItem>): Flow<BaseResult<DetailProductResponse?>> = flow {
        try {
            // Build the JSON request object
            val request = JsonObject().apply {
                addProperty("userId", userId)
                addProperty("date", date)

                // Create a JSON array for the products
                val productArray = JsonArray()
                products.forEach { product ->
                    val productObject = JsonObject().apply {
                        addProperty("productId", product.productId)
                        addProperty("quantity", product.quantity)
                    }
                    productArray.add(productObject)
                }
                add("products", productArray)
            }

            // Call the API to add to cart, passing the request object
            val response = apiService.addToCart(token, userId, request)
            emit(BaseResult.Success(response))
        } catch (e: Exception) {
            emit(BaseResult.Failed(e))
        }
    }

    fun deleteCartById(token: String, id: Int): Flow<BaseResult<DeleteCartResponse?>> = flow {
        try {
            val response = apiService.deleteCart(token, id)
            emit(BaseResult.Success(response))
        }catch (e:Exception){
            emit(BaseResult.Failed(e))
        }
    }
}