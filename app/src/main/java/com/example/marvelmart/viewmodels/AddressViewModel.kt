package com.example.marvelmart.viewmodels

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.marvelmart.models.Address
import com.example.marvelmart.models.AddressResponse
import com.example.marvelmart.repositories.AddressRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class AddressViewModel(private val repository: AddressRepository) : ViewModel() {

    val addAddressResult = MutableLiveData<AddressResponse?>()
    val errorMessage = MutableLiveData<String>()
    val addressAddedSuccessfully = MutableLiveData<Boolean>()

    fun addAddress(address: Address) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: Response<AddressResponse> = repository.addAddress(address)
                withContext(Dispatchers.Main) {
                    if (response.body()?.status == 200) {
//                        addAddressResult.value = response.body()
                        addressAddedSuccessfully.value = true;
                    } else {
                        errorMessage.value = "Failed to add address"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    errorMessage.value = "Network error: ${e.message}"
                }
            }
        }
    }

    class AddressViewModelFactory(private val repository: AddressRepository) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddressViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddressViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}