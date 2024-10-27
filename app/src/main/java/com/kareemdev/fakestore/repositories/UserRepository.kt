package com.kareemdev.fakestore.repositories

import com.google.gson.JsonObject
import com.kareemdev.fakestore.data.remote.response.BaseResult
import com.kareemdev.fakestore.data.remote.response.user.UserResponse
import com.kareemdev.fakestore.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
){
    fun login(user:String, password:String): Flow<BaseResult<UserResponse?>> = flow {
        try {
            val request = JsonObject().apply {
                addProperty("username", user)
                addProperty("password", password)
            }
            val response = apiService.login(request)
            emit(BaseResult.Success(response))
        }catch (e: Exception){
            emit(BaseResult.Failed(e))
        }
    }
}