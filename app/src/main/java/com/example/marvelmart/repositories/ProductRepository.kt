package com.example.marvelmart.repositories

import android.adservices.adid.AdId
import com.example.marvelmart.api.ApiService
import com.example.marvelmart.api.RetrofitClient.apiService
import com.example.marvelmart.models.Product
import com.example.marvelmart.models.ProductResponse
import com.example.marvelmart.models.SubcategoryResponse

class ProductRepository() {

    suspend fun getProducts(subCategoryId: Int): ProductResponse {
        return apiService.getProducts(subCategoryId)
    }
}