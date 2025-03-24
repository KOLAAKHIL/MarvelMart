package com.example.marvelmart.repositories

import com.example.marvelmart.api.RetrofitClient
import com.example.marvelmart.models.ApiResponse
import com.example.marvelmart.models.LoginRequest
import com.example.marvelmart.models.LoginResponse
import com.example.marvelmart.models.RegisterRequest
import com.example.marvelmart.models.RegisterResponse
import com.example.marvelmart.models.User

class UserRepository {
    suspend fun registerUser(registerRequest: RegisterRequest): RegisterResponse {
        return RetrofitClient.apiService.registerUser(registerRequest)
    }

    suspend fun loginUser(loginRequest: LoginRequest):LoginResponse{
        return RetrofitClient.apiService.loginUser(loginRequest)
    }

}