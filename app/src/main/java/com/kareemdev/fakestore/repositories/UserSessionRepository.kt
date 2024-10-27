package com.kareemdev.fakestore.repositories

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class UserSessionRepository @Inject constructor(
    private val dataStore: DataStoreRepository
) {
    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    suspend fun updateLoginStatus(isLoggedIn: Boolean) {
        _isUserLoggedIn.value = isLoggedIn
        // Save login status to data store or other persistent storage if needed
    }
}