package com.example.marvelmart.repositories

import com.example.marvelmart.api.ApiService
import com.example.marvelmart.models.SubcategoryResponse

class SubcategoryRepository(private val apiService: ApiService) {

    suspend fun getSubcategories(categoryId: Int): SubcategoryResponse {
        return apiService.getSubcategories(categoryId)
    }
}