package com.example.marvelmart.models


data class CategoryResponse(
    val status: Int,
    val message: String,
    val categories: List<Category>
)

data class Category(
    val category_id: String,
    val category_name: String,
    val category_image_url: String,
    val is_active: String
)