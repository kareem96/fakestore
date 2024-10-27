package com.kareemdev.fakestore.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kareemdev.fakestore.repositories.DataStoreRepository
import com.kareemdev.fakestore.ui.theme.Theme
import com.kareemdev.fakestore.ui.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel(){
    fun changeTheme(theme: Theme){
        val isDarkTheme = theme == Theme.Dark
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.setDataStoreValueByKey(
                key = Constants.PREFERENCES_KEY_DARK_THEME,
                value = isDarkTheme.toString()
            )
        }
    }

    fun logout(){
        viewModelScope.launch {
            dataStoreRepository.clearUserInfo()
        }
    }
}