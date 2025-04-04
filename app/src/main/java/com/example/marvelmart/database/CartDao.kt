package com.example.marvelmart.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.marvelmart.models.CartItem
import kotlinx.coroutines.flow.Flow


@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getAllItems(): LiveData<List<CartItem>>

    @Insert
    suspend fun insertItem(cartItem: CartItem)

    @Update
    suspend fun updateItem(cartItem: CartItem)

    @Delete
    suspend fun deleteItem(cartItem: CartItem)

    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    fun getItemById(productId: String): LiveData<CartItem?>

    @Query("SELECT COUNT(*) FROM cart_items")
    fun getItemCount(): LiveData<Int>
}