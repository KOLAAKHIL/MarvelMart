package com.example.marvelmart.repositories

import com.example.marvelmart.api.RetrofitClient
import com.example.marvelmart.models.ProductDetailsResponse
import retrofit2.Call

class DetailsRepository {
    fun getProductDetails(productId: String): Call<ProductDetailsResponse> {
        return RetrofitClient.apiService.getProductDetails(productId)
    }
}