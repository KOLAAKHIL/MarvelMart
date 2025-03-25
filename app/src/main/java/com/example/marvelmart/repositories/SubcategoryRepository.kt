package com.example.marvelmart.repositories

import com.example.marvelmart.api.RetrofitClient.apiService
import com.example.marvelmart.models.SubcategoryResponse

class SubcategoryRepository() {

    suspend fun getSubcategories(categoryId: Int): SubcategoryResponse {
        return apiService.getSubcategories(categoryId)
    }
}