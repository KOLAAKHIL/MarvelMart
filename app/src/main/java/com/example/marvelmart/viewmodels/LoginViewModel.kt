package com.example.marvelmart.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.marvelmart.models.LoginRequest
import com.example.marvelmart.models.LoginResponse
import com.example.marvelmart.models.RegisterResponse
import com.example.marvelmart.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repository: UserRepository): ViewModel() {


    private val _loginResponse: MutableLiveData<LoginResponse?> =
        MutableLiveData<LoginResponse?>(null)
    val loginResponse: MutableLiveData<LoginResponse?> = _loginResponse
    private val _errorMessage: MutableLiveData<String?> = MutableLiveData(null)
    val errorMessage: LiveData<String?> = _errorMessage

    fun loginUser(loginRequest: LoginRequest) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) { repository.loginUser(loginRequest) }
                _loginResponse.postValue(response)
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
                Log.e("LoginError", "Error logging in: ${e.message}", e)
            }

        }
    }


    class LoginViewModelFactory(private val repository: UserRepository) :
        ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
