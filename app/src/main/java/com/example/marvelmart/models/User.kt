package com.example.marvelmart.models


data class User(
    val user_id: String,
    val full_name: String,
    val mobile_no: String,
    val email_id: String
)

data class RegisterRequest(
    val full_name: String,
    val mobile_no: String,
    val email_id: String,
    val password: String
)

data class RegisterResponse(
    val status: Int,
    val message: String
)

data class LoginRequest(
    val email_id: String,
    val password: String
)

data class LoginResponse(
    val user: User,
    val message: String
)
