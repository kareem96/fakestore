package com.kareemdev.fakestore.repositories

import com.kareemdev.fakestore.data.remote.response.user.UserResponse
import com.kareemdev.fakestore.repositories.datastore.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepository @Inject constructor(
    private val dataStore: DataStoreOperations
){
    fun getDataStoreValueByKey(key:String): Flow<String> {
        return dataStore.getDataStoreValueByKey(key)
    }

    suspend fun setDataStoreValueByKey(key:String?, value:String?){
        dataStore.settDataStoreValueByKey(key, value)
    }

    suspend fun saveUserInfo(user: UserResponse?){
        if (user != null) {
            dataStore.saveUserInfo(user)
        }
    }

    fun readUserInfo(): Flow<UserResponse> {
        return dataStore.readUserInfo()
    }
    suspend fun clearUserInfo(){
        dataStore.clearUserInfo()
    }

}