package com.example.marvelmart.models

import android.media.Image
import com.google.gson.annotations.SerializedName


data class Product(

    val product_id: String,
    val product_name: String,
    val description: String,
    val category_id: String,
    val category_name: String,
    val subcategory_id: String,
    val subcategory_name: String,
    val price: String,
    val average_rating: String,
    val product_image_url: String
)

data class ProductResponse(
    val status: Int,
    val message: String,
    val products: List<Product>?
)

data class SearchProductResponse(
    val status: Int,
    val message: String,
    val product: DetailedProduct?
)

data class DetailedProduct(
    @SerializedName("product_id") val productId: String,
    @SerializedName("product_name") val productName: String,
    val description: String,
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("sub_category_id") val subCategoryId: String,
    val price: String,
    @SerializedName("average_rating") val averageRating: String,
    @SerializedName("product_image_url") val productImageUrl: String,
    @SerializedName("is_active") val isActive: String,
    val images: List<Image>,
    val specifications: List<Specification>,
    val reviews: List<Review>
)

data class Specification(
    @SerializedName("specification_id") val specificationId: String,
    val title: String,
    val specification: String,
    @SerializedName("display_order") val displayOrder: String
)

data class Review(
    @SerializedName("user_id") val userId: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("review_id") val reviewId: String,
    @SerializedName("review_title") val reviewTitle: String,
    val review: String,
    val rating: String,
    @SerializedName("review_date") val reviewDate: String
)

