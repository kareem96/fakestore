package com.kareemdev.fakestore.ui.screen.splash

import androidx.lifecycle.ViewModel
import com.kareemdev.fakestore.repositories.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository
): ViewModel() {

    val userToken: Flow<String?> = dataStoreRepository.readUserInfo()
        .map { userInfo ->
            userInfo.token
        }.catch { emit(null) }
}