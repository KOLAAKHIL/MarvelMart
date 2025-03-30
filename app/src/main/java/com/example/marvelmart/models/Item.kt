package com.example.marvelmart.models

data class Item(
    val imageUrl: String,
    val name: String,
    val unitPrice: Double,
    val quantity: Int,
    val amount: Double
)