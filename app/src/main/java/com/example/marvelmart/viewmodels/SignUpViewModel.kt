package com.example.marvelmart.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.marvelmart.models.RegisterRequest
import com.example.marvelmart.models.RegisterResponse
import com.example.marvelmart.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupViewModel(private val repository: UserRepository) : ViewModel() {

    private val _registerResponse = MutableLiveData<RegisterResponse?>(null)
    val registerResponse: LiveData<RegisterResponse?> = _registerResponse

    fun registerUser(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO){ repository.registerUser(registerRequest)}
                _registerResponse.postValue(response)
            } catch (e: Exception) {
                _registerResponse.postValue(RegisterResponse(1, "Registration failed: ${e.message}"))
            }
        }
    }

    class SignupViewModelFactory(private val repository: UserRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SignupViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}