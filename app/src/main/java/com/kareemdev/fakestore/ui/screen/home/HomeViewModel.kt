package com.kareemdev.fakestore.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kareemdev.fakestore.data.remote.response.BaseResult
import com.kareemdev.fakestore.data.remote.response.product.DetailProductResponse
import com.kareemdev.fakestore.data.remote.response.product.ProductItem
import com.kareemdev.fakestore.data.remote.response.product.ProductResponse
import com.kareemdev.fakestore.data.remote.response.user.UserResponse
import com.kareemdev.fakestore.repositories.DataStoreRepository
import com.kareemdev.fakestore.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val repository: ProductRepository,
) : ViewModel() {

    private val _isUserInfo = MutableLiveData<UserResponse>()
    val isUserInfo: LiveData<UserResponse?> get() = _isUserInfo

    private val _getListProduct = MutableLiveData<BaseResult<ProductResponse?>>(BaseResult.Idle)
    val getListProduct: LiveData<BaseResult<ProductResponse?>> get() = _getListProduct

    private val _getNoteById = MutableLiveData<BaseResult<DetailProductResponse?>>(BaseResult.Idle)
    val getNoteById: LiveData<BaseResult<DetailProductResponse?>> get() = _getNoteById

    private val _addToCart = MutableLiveData<BaseResult<DetailProductResponse?>>(BaseResult.Idle)
    val addToCart: LiveData<BaseResult<DetailProductResponse?>> get() = _addToCart

    /*private val _deleteNoteById =
        MutableLiveData<BaseResult<BaseResponseObject<NoteResponse?>>>(BaseResult.Idle)
    val deleteNoteById: LiveData<BaseResult<BaseResponseObject<NoteResponse?>>> get() = _deleteNoteById*/

    var token: String? = null
        private set

    init {
        viewModelScope.launch {
            dataStoreRepository.readUserInfo().collect { user ->
                _isUserInfo.value = user
                token = user.token
            }
        }
    }

    fun getUserInfo(): LiveData<UserResponse> {
        val userInfo = MutableLiveData<UserResponse>()
        viewModelScope.launch {
            dataStoreRepository.readUserInfo().collect { user ->
                userInfo.postValue(user)
            }
        }
        return userInfo
    }

    fun getListNote(token: String) {
        _getListProduct.value = BaseResult.Loading
        viewModelScope.launch {
            repository.getListProduct(token).collect { result ->
                _getListProduct.value = result
            }
        }
    }


    fun getNoteById(id: Int) {
        _getNoteById.value = BaseResult.Loading
        viewModelScope.launch {
            token?.let {
                repository.getDetailProduct(it, id).collect { result ->
                    _getNoteById.value = result
                }
            }
        }
    }

    val products = listOf(
        ProductItem(productId = 5, quantity = 1),
        ProductItem(productId = 1, quantity = 5)
    )

    fun addToCart(id: Int, title:String, description:String) {
        _getNoteById.value = BaseResult.Loading
        viewModelScope.launch {
            token?.let {
                repository.addToCart(token, userId = 5, date = "2020-02-03", products = products).collect { result ->
                    _addToCart.value = result
                }
            }
        }
    }


    /*fun deleteNoteById(id: Int) {
        _getNoteById.value = BaseResult.Loading
        viewModelScope.launch {
            token?.let {
                repository.deleteNoteById(it, id).collect { result ->
                    _deleteNoteById.value = result
                }
            }
        }
    }*/

}