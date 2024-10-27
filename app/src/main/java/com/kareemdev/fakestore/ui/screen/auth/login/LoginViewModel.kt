package com.kareemdev.fakestore.ui.screen.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kareemdev.fakestore.data.remote.response.BaseResult
import com.kareemdev.fakestore.data.remote.response.user.UserResponse
import com.kareemdev.fakestore.repositories.DataStoreRepository
import com.kareemdev.fakestore.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository,
    private val dataStore: DataStoreRepository
) : ViewModel() {
    private val _loginResult = MutableLiveData<BaseResult<UserResponse?>>(BaseResult.Idle)
    val loginResult: LiveData<BaseResult<UserResponse?>>
        get() = _loginResult

    private val _isUserLoggedIn = MutableLiveData(false)
    val isUserLoggedIn: LiveData<Boolean> = _isUserLoggedIn

    fun login(user: String, password: String) {
        _loginResult.value = BaseResult.Loading
        viewModelScope.launch {
            repository.login(user, password).collect { result ->
                _loginResult.value = result
                if (result is BaseResult.Success) {
                    result.data?.let { data ->
                        dataStore.saveUserInfo(data)
                        _isUserLoggedIn.value = true // Update login status
                    }
                }
            }
        }
    }
}