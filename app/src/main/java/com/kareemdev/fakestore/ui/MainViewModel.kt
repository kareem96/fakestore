package com.kareemdev.fakestore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kareemdev.fakestore.repositories.DataStoreRepository
import com.kareemdev.fakestore.repositories.UserSessionRepository
import com.kareemdev.fakestore.ui.theme.Theme
import com.kareemdev.fakestore.ui.theme.ThemeState
import com.kareemdev.fakestore.ui.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DataStoreRepository,
    private val userSessionRepository: UserSessionRepository
) : ViewModel() {
    private val _themeState: MutableStateFlow<ThemeState> = MutableStateFlow(ThemeState(theme = Theme.Light))
    val themeState: StateFlow<ThemeState> = _themeState

    private val _isUserLogIn: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUserLogIn: StateFlow<Boolean> = _isUserLogIn

    val isUserLoggedIn = userSessionRepository.isUserLoggedIn


    init {
        getTheme()
        checkUserLoginStatus()
    }

    private fun getTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            val isDarkTheme = repository.getDataStoreValueByKey(key = Constants.PREFERENCES_KEY_DARK_THEME).stateIn(viewModelScope).value
            _themeState.value = ThemeState(theme = if (isDarkTheme == "true") Theme.Dark else Theme.Light)
        }
    }

    private fun checkUserLoginStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val userToken = repository.readUserInfo().stateIn(viewModelScope).value
            _isUserLogIn.value = userToken.token?.isNotEmpty() == true
        }
    }

    fun setUserLoggedIn(isLoggedIn: Boolean) {
        _isUserLogIn.value = isLoggedIn
    }

    fun logout() {
        viewModelScope.launch {
            repository.clearUserInfo()
            userSessionRepository.updateLoginStatus(false)
            _isUserLogIn.value = false
        }
    }
}