package com.example.marvelmart.models

data class Address(
    val user_id: Int,
    val title: String,
    val address: String
)

data class AddressResponse(
    val status: Int,
    val message: String
)