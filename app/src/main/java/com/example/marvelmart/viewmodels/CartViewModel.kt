package com.example.marvelmart.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.marvelmart.database.CartDatabase
import com.example.marvelmart.models.CartItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val cartDao = CartDatabase.getDatabase(application).cartDao()
    val cartItems: LiveData<List<CartItem>> = cartDao.getAllItems()

    val cartItemCount: LiveData<Int> = MediatorLiveData<Int>().apply {
        addSource(cartItems) { items ->
            value = items.size
        }
    }


    val totalPrice: LiveData<Double> = MediatorLiveData<Double>().apply {
        addSource(cartItems) { items ->
            value = items.sumOf { it.productPrice * it.quantity }
        }
    }


    fun insert(cartItem: CartItem) = viewModelScope.launch(Dispatchers.IO) {
        cartDao.insertItem(cartItem)
    }

    fun delete(cartItem: CartItem) = viewModelScope.launch(Dispatchers.IO) {
        cartDao.deleteItem(cartItem)
    }

    fun update(cartItem: CartItem) = viewModelScope.launch(Dispatchers.IO) {
        cartDao.updateItem(cartItem)
    }


}