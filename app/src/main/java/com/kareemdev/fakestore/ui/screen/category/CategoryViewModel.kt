package com.kareemdev.fakestore.ui.screen.category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kareemdev.fakestore.data.remote.response.BaseResult
import com.kareemdev.fakestore.data.remote.response.product.CartResponse
import com.kareemdev.fakestore.data.remote.response.product.CategoryListResponse
import com.kareemdev.fakestore.data.remote.response.product.TitleCategoryResponse
import com.kareemdev.fakestore.data.remote.response.user.UserResponse
import com.kareemdev.fakestore.repositories.DataStoreRepository
import com.kareemdev.fakestore.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val dataStore: DataStoreRepository
) : ViewModel() {
    private val _isUserInfo = MutableLiveData<UserResponse>()
    val isUserInfo: LiveData<UserResponse?> get() = _isUserInfo

    private val _titleCategory = MutableLiveData<BaseResult<TitleCategoryResponse?>>(BaseResult.Idle)
    val titleCategory: LiveData<BaseResult<TitleCategoryResponse?>>
        get() = _titleCategory

    // Store a map of categories per title
    private val _listCategoryMap = MutableLiveData<Map<String, BaseResult<CategoryListResponse?>>>(emptyMap())
    val listCategoryMap: LiveData<Map<String, BaseResult<CategoryListResponse?>>>
        get() = _listCategoryMap

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

    fun getTitleCategory(token: String) {
        _titleCategory.value = BaseResult.Loading
        viewModelScope.launch {
            repository.getTitleCategory(token).collect { result ->
                _titleCategory.value = result
            }
        }
    }

    fun getListCategory(token: String, category: String) {
        val currentMap = _listCategoryMap.value ?: emptyMap()
        _listCategoryMap.value = currentMap + (category to BaseResult.Loading) // Update loading state for the category
        viewModelScope.launch {
            try {
                repository.getListCategory(token, category).collect { result ->
                    _listCategoryMap.value = currentMap + (category to result) // Update the result in the map
                    Log.d("CategoryViewModel", "Fetched categories for $category: $result")
                }
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error fetching categories for $category", e)
                _listCategoryMap.value = currentMap + (category to BaseResult.Failed(e))
            }
        }
    }
}
