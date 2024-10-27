package com.kareemdev.fakestore.repositories.datastore

import com.kareemdev.fakestore.data.remote.response.user.UserResponse
import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    fun getDataStoreValueByKey(key: String): Flow<String>

    suspend fun settDataStoreValueByKey(key: String?, value: String?)

    suspend fun saveUserInfo(user: UserResponse)

    fun readUserInfo(): Flow<UserResponse>

    suspend fun clearUserInfo()
}