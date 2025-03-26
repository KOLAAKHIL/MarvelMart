package com.example.marvelmart.repositories

import com.example.marvelmart.api.RetrofitClient
import com.example.marvelmart.models.Category
import com.example.marvelmart.models.CategoryResponse
import com.example.marvelmart.models.DetailedProduct
import com.example.marvelmart.models.SearchProductResponse
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

    suspend fun searchProduct(searchText: String): Result<DetailedProduct?> = withContext(Dispatchers.IO) {
        try {
            val response: SearchProductResponse = RetrofitClient.apiService.searchproduct(searchText)

            if (response.status == 0) {
                Result.success(response.product)
            } else {
                Result.failure(Exception("Error searching product: ${response.message}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}