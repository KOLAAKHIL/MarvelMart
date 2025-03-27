package com.example.marvelmart.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marvelmart.models.ProductDetails
import com.example.marvelmart.models.ProductDetailsResponse
import com.example.marvelmart.repositories.DetailsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel(private val repository: DetailsRepository) : ViewModel() {

    private val _productDetails = MutableLiveData<ProductDetails?>()
    val productDetails: LiveData<ProductDetails?> = _productDetails

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchProductDetails(productId: String) {
        repository.getProductDetails(productId).enqueue(object : Callback<ProductDetailsResponse> {
            override fun onResponse(
                call: Call<ProductDetailsResponse>,
                response: Response<ProductDetailsResponse>
            ) {
                if (response.isSuccessful) {
                    _productDetails.value = response.body()?.product
                } else {
                    _errorMessage.value = "Failed to fetch product details"
                }
            }

            override fun onFailure(call: Call<ProductDetailsResponse>, t: Throwable) {
                _errorMessage.value = "Network error: ${t.message}"
            }
        })
    }


    class DetailsViewModelFactory(private val repository: DetailsRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}