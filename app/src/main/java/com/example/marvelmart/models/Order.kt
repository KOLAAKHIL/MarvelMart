package com.example.marvelmart.models

import com.google.gson.annotations.SerializedName

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

data class PlaceOrderRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("delivery_address") val deliveryAddress: DeliveryAddress,
    @SerializedName("items") val items: List<OrderItem>,
    @SerializedName("bill_amount") val billAmount: Double,
    @SerializedName("payment_method") val paymentMethod: String
)

data class DeliveryAddress(
    @SerializedName("title") val title: String,
    @SerializedName("address") val address: String
)


data class OrderItem(
    @SerializedName("product_id") val productId: Int,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("unit_price") val unitPrice: Double
)


