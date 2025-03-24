package com.example.marvelmart.repositories

import com.example.marvelmart.api.RetrofitClient
import com.example.marvelmart.models.Category
import com.example.marvelmart.models.CategoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class CategoryRepository {
    suspend fun getCategories(): Result<List<Category>> = withContext(Dispatchers.IO) {
        try {
            val response: Response<CategoryResponse> = RetrofitClient.apiService.getCategories()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.categories)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Error fetching categories: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}