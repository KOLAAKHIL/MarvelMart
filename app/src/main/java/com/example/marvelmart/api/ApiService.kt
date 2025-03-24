package com.example.marvelmart.api


import com.example.marvelmart.models.Category
import com.example.marvelmart.models.CategoryResponse
import com.example.marvelmart.models.LoginRequest
import com.example.marvelmart.models.LoginResponse
import com.example.marvelmart.models.RegisterRequest
import com.example.marvelmart.models.RegisterResponse
import com.example.marvelmart.models.SubcategoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("User/register")
    suspend fun registerUser(@Body user: RegisterRequest): RegisterResponse

    @Headers("Content-type:application/json")
    @POST("User/auth")
    suspend fun loginUser(@Body user:LoginRequest): LoginResponse

    @GET("Category")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("SubCategory")
    suspend fun getSubcategories(@Query("category_id") categoryId: Int): SubcategoryResponse

}
