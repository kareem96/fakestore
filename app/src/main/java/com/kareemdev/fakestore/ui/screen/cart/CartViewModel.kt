package com.kareemdev.fakestore.ui.screen.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kareemdev.fakestore.data.remote.response.BaseResult
import com.kareemdev.fakestore.data.remote.response.product.CartResponse
import com.kareemdev.fakestore.data.remote.response.product.DeleteCartResponse
import com.kareemdev.fakestore.data.remote.response.user.UserResponse
import com.kareemdev.fakestore.repositories.DataStoreRepository
import com.kareemdev.fakestore.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel@Inject constructor(
    private val repository: ProductRepository,
    private val dataStore: DataStoreRepository
) : ViewModel() {
    private val _isUserInfo = MutableLiveData<UserResponse>()
    val isUserInfo: LiveData<UserResponse?> get() = _isUserInfo

    private val _cart = MutableLiveData<BaseResult<CartResponse?>>(BaseResult.Idle)
    val cart: LiveData<BaseResult<CartResponse?>>
        get() = _cart

    private val _deleteCart = MutableLiveData<BaseResult<DeleteCartResponse?>>(BaseResult.Idle)
    val deleteCart: LiveData<BaseResult<DeleteCartResponse?>>
        get() = _deleteCart

    var token: String? = null
        private set

    init {
        viewModelScope.launch {
            dataStore.readUserInfo().collect { user ->
                _isUserInfo.value = user
                token = user.token
            }
        }
    }

    fun getUserInfo(): LiveData<UserResponse> {
        val userInfo = MutableLiveData<UserResponse>()
        viewModelScope.launch {
            dataStore.readUserInfo().collect { user ->
                userInfo.postValue(user)
            }
        }
        return userInfo
    }


    fun getListCart() {
        _cart.value = BaseResult.Loading
        viewModelScope.launch {
            token?.let {
                repository.getListCart(it).collect { result ->
                    _cart.value = result
                }
            }
        }
    }

    fun deleteCart(id:Int) {
        _deleteCart.value = BaseResult.Loading
        viewModelScope.launch {
            token?.let {
                repository.deleteCartById(it, id).collect { result ->
                    _deleteCart.value = result
                }
            }
        }
    }
}