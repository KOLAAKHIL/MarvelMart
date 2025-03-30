package com.example.marvelmart.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelmart.adapters.OrderItemsAdapter
import com.example.marvelmart.databinding.ActivityOrderConfirmedBinding
import com.example.marvelmart.viewmodels.OrderViewModel

class OrderConfirmedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderConfirmedBinding
    private lateinit var orderAdapter: OrderItemsAdapter
    private lateinit var orderViewModel: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderConfirmedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.itemsRecyclerView.layoutManager = LinearLayoutManager(this)

        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        orderViewModel.orders.observe(this) { orders ->
            if (orders.isNotEmpty()) {
                val order = orders[0]
                orderAdapter = OrderItemsAdapter(order.items)
                binding.itemsRecyclerView.adapter = orderAdapter

                binding.orderIdTextView.text = "Order ID: #${order.orderId}"
                binding.orderStatusTextView.text = "Order Status: ${order.orderStatus}"
                binding.totalBillAmountTextView.text = "Total Bill Amount: $${order.billAmount}"
                binding.deliveryAddressTextView.text = order.address
            }
        }

//        orderViewModel.errorMessage.observe(this) { errorMessage ->
//
//        }
//
//        orderViewModel.isLoading.observe(this) { isLoading ->
//
//        }

        orderViewModel.fetchUserOrders(1)
    }
}