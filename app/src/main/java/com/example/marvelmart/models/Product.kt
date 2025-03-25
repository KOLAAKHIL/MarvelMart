package com.example.marvelmart.models


data class Product(
    val productid: String,
    val productname: String,
    val description: String,
    val categoryid: String,
    val categoryname: String,
    val subcategoryid: String,
    val subcategoryname: String,
    val price: String,
    val averagerating: String,
    val productimage_url: String
)

data class ProductResponse(
    val status: Int,
    val message: String,
    val products: List<Product>?
)