package com.example.marvelmart.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelmart.R
import com.example.marvelmart.adapters.CartItemAdapter
import com.example.marvelmart.databinding.ActivityCartBinding
import com.example.marvelmart.databinding.ActivityCheckoutSummaryBinding
import com.example.marvelmart.models.CartItem
import com.example.marvelmart.viewmodels.CartViewModel
import java.text.DecimalFormat

class CartActivity : AppCompatActivity(), CartItemAdapter.ItemQuantityChangeListener {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartItemAdapter: CartItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]

        cartItemAdapter = CartItemAdapter(emptyList(), this)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = cartItemAdapter

        binding.checkoutButton.setOnClickListener{
            val intent = Intent(this, CheckoutSummaryActivity::class.java)
            startActivity(intent)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        cartViewModel.cartItems.observe(this) { cartItems ->
            cartItemAdapter.updateItems(cartItems)
        }

        cartViewModel.totalPrice.observe(this) { totalPrice ->
            val formattedPrice = DecimalFormat("$#.00")
            binding.totalBillAmount.text = "Total Bill Amount: $formattedPrice"
        }
    }

    override fun onQuantityChanged(cartItem: CartItem, newQuantity: Int) {
        val updatedCartItem = cartItem.copy(quantity = newQuantity)
        cartViewModel.update(updatedCartItem)
    }
}