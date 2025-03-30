package com.example.marvelmart.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val productId: String,
    val productName: String,
    val productDescription: String,
    val productPrice: Double,
    val imageUrl: String,
    var quantity: Int = 1
)