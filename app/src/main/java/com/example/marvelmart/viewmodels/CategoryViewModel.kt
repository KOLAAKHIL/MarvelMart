package com.example.marvelmart.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.marvelmart.models.Category
import com.example.marvelmart.models.DetailedProduct
import com.example.marvelmart.repositories.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _searchedProduct = MutableLiveData<DetailedProduct?>()
    val searchProduct: MutableLiveData<DetailedProduct?> = _searchedProduct

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchCategories() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getCategories()
                .onSuccess { categories ->
                    _categories.value = categories
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.message ?: "Unknown error occurred"
                }
                .also { _isLoading.value = false }
        }
    }

    fun searchProduct(searchText: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.searchProduct(searchText)
                .onSuccess { detailedProduct ->
                    _searchedProduct.value = detailedProduct
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.message ?: "Unknown error occurred"
                }
                .also { _isLoading.value = false }
        }
    }
}

class CategoryViewModelFactory(private val repository: CategoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}