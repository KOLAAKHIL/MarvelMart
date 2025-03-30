package com.example.marvelmart.repositories

import com.example.marvelmart.api.ApiService
import com.example.marvelmart.models.Address
import com.example.marvelmart.models.AddressResponse
import retrofit2.Response

class AddressRepository(private val apiService: ApiService) {

    suspend fun addAddress(address: Address): Response<AddressResponse> {
        return apiService.addAddress(address)
    }
}