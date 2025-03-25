package com.example.marvelmart.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.marvelmart.models.Subcategory
import com.example.marvelmart.repositories.SubcategoryRepository
import kotlinx.coroutines.launch

class SubcategoryViewModel(private val repository: SubcategoryRepository) : ViewModel() {

    private val _subcategories = MutableLiveData<List<Subcategory>?>()
    val subcategories: MutableLiveData<List<Subcategory>?> = _subcategories

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    fun fetchSubcategories(categoryId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getSubcategories(categoryId)
                if (response.status == 0 && response.subcategories != null) {
                    _subcategories.value = response.subcategories
                } else {
                    _errorMessage.value = response.message ?: "Failed to fetch subcategories"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "An error occurred"
            }
        }
    }


    class SubcategoryViewModelFactory(private val repository: SubcategoryRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SubcategoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SubcategoryViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
