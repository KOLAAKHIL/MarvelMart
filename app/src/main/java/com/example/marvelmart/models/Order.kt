package com.example.marvelmart.models

data class Order(
    val orderId: String,
    val addressTitle: String,
    val address: String,
    val billAmount: String,
    val paymentMethod: String,
    val orderStatus: String,
    val orderDate: String,
    val items: List<Item>
)

data class OrderResponse(
    val status: Int,
    val message: String,
    val orders: List<Order>
)