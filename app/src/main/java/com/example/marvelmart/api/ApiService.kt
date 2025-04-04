package com.example.marvelmart.api


import com.example.marvelmart.models.Address
import com.example.marvelmart.models.AddressResponse
import com.example.marvelmart.models.CategoryResponse
import com.example.marvelmart.models.LoginRequest
import com.example.marvelmart.models.LoginResponse
import com.example.marvelmart.models.OrderResponse
import com.example.marvelmart.models.PlaceOrderRequest
import com.example.marvelmart.models.ProductDetailsResponse
import com.example.marvelmart.models.ProductResponse
import com.example.marvelmart.models.RegisterRequest
import com.example.marvelmart.models.RegisterResponse
import com.example.marvelmart.models.SearchProductResponse
import com.example.marvelmart.models.SubcategoryResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
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

    @GET("SubCategory/products/{sub_category_id}")
    suspend fun getProducts(@Path("sub_category_id") subcategoryId: Int): ProductResponse

    @GET("Product/search")
    suspend fun searchproduct(@Query("query") searchText: String): SearchProductResponse

    @GET("Product/details/{product_id}")
    fun getProductDetails(@Path("product_id") productId: String): Call<ProductDetailsResponse>

    @POST("User/address")
    suspend fun addAddress(@Body address: Address): Response<AddressResponse>


    @POST("/Order")
    suspend fun placeOrder(@Body orderRequest: PlaceOrderRequest): Response<OrderResponse>

    @GET("Order/userOrders/{user_id}")
    suspend fun getUserOrders(@Path("user_id") userId: Int): Response<OrderResponse>
}