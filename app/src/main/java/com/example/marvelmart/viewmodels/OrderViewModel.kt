package com.example.marvelmart.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.FtsOptions
import com.example.marvelmart.api.ApiService
import com.example.marvelmart.api.RetrofitClient
import com.example.marvelmart.models.Order
import com.example.marvelmart.models.OrderResponse
import com.example.marvelmart.models.PlaceOrderRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderViewModel : ViewModel() {

    private val apiService: ApiService = RetrofitClient.apiService
    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _orderResponse = MutableLiveData<OrderResponse?>()
    val orderResponse: LiveData<OrderResponse?> = _orderResponse

    fun fetchUserOrders(userId: Int) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getUserOrders(userId)
                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _orders.value = response.body()?.orders ?: emptyList()
                    } else {
                        _errorMessage.value = "Failed to fetch orders: ${response.message()}"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                    _errorMessage.value = "Network error: ${e.message}"
                }
            }
        }
    }

    fun placeOrder(orderRequest: PlaceOrderRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.placeOrder(orderRequest)
                withContext(Dispatchers.Main) {
                    _orderResponse.value = response.body()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _errorMessage.value = e.message
                }
            }
        }
    }
}

