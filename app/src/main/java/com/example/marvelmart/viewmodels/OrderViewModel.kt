package com.example.marvelmart.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.FtsOptions
import com.example.marvelmart.api.ApiService
import com.example.marvelmart.api.RetrofitClient
import com.example.marvelmart.models.Order
import com.example.marvelmart.models.OrderResponse
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

    fun fetchUserOrders(userId: Int) {
        _isLoading.value = true
        apiService.getUserOrders(userId).enqueue(object : Callback<OrderResponse> {
            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _orders.value = response.body()?.orders ?: emptyList()
                } else {
                    _errorMessage.value = "Failed to fetch orders: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Network error: ${t.message}"
            }
        })
    }
}