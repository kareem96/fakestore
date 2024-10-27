package com.kareemdev.fakestore.repositories.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kareemdev.fakestore.data.remote.response.user.UserResponse
import com.kareemdev.fakestore.ui.utils.Constants.PREFERENCES_NAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okio.IOException


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreOperationsImpl(context: Context) : DataStoreOperations {
    private object PreferencesKey {
        val userId = intPreferencesKey("user_id")
        val name = stringPreferencesKey("name")
        val userToken = stringPreferencesKey("user_token")
    }

    private val dataStore = context.dataStore
    override fun getDataStoreValueByKey(key: String): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[stringPreferencesKey(key)] ?: ""
            }
    }

    override suspend fun settDataStoreValueByKey(key: String?, value: String?): Unit =
        withContext(Dispatchers.IO) {
            dataStore.edit { preferences ->
                if (!key.isNullOrEmpty() && !value.isNullOrEmpty())
                    preferences[stringPreferencesKey(key)] = key
            }
        }

    override suspend fun saveUserInfo(user: UserResponse): Unit = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.userToken] = user.token.toString()
        }
    }

    override fun readUserInfo(): Flow<UserResponse> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                UserResponse(
                    token = preferences[PreferencesKey.userToken] ?: ""
                )
            }
    }

    override suspend fun clearUserInfo(): Unit = withContext(Dispatchers.IO){
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}