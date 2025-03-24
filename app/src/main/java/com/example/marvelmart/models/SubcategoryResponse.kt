package com.example.marvelmart.models

import com.google.gson.annotations.SerializedName

data class SubcategoryResponse(
    val status: Int,
    val message: String?,
    val subcategories: List<Subcategory>?
)

data class Subcategory(
    @SerializedName("subcategory_id") val subcategoryId: String,
    @SerializedName("subcategory_name") val subcategoryName: String,
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("subcategory_image_url") val subcategoryImageUrl: String,
    @SerializedName("is_active") val isActive: String
)