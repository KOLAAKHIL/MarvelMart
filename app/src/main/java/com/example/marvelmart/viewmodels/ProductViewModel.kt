package com.example.marvelmart.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.marvelmart.models.Product
import com.example.marvelmart.models.ProductResponse
import com.example.marvelmart.repositories.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _products = MutableLiveData<List<Product>?>()
    val products: LiveData<List<Product>?> = _products

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchProducts(subcategoryId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response: ProductResponse? = repository.getProducts(subcategoryId)
                if (response?.status == 0 && response.products != null) {
                    _products.value = response.products
                } else {
                    _errorMessage.value = response?.message ?: "Failed to fetch products"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }


    class ProductViewModelFactory(private val repository: ProductRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProductViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}