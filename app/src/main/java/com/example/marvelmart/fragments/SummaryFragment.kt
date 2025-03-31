package com.example.marvelmart.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelmart.activities.OrderConfirmedActivity
import com.example.marvelmart.adapters.CartItemAdapter
import com.example.marvelmart.databinding.FragmentSummaryBinding
import com.example.marvelmart.models.CartItem
import com.example.marvelmart.models.DeliveryAddress
import com.example.marvelmart.models.OrderItem
import com.example.marvelmart.models.PlaceOrderRequest
import com.example.marvelmart.viewmodels.CartViewModel
import com.example.marvelmart.viewmodels.OrderViewModel
import kotlinx.coroutines.launch


class SummaryFragment : Fragment() {

    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartItemAdapter: CartItemAdapter
    private var cartItems: List<CartItem> = emptyList()
    private var totalBillAmount: Double = 0.0
    private var deliveryAddress: DeliveryAddress? = null
    private var paymentOption: String = "Cash On Delivery"

    private val cartViewModel: CartViewModel by lazy {
        ViewModelProvider(this).get(CartViewModel::class.java)
    }
    private val orderViewModel: OrderViewModel by lazy {
        ViewModelProvider(this).get(OrderViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadData()
        binding.confirmOrderButton.setOnClickListener{
            placeOrderAndNavigate()

        }
    }

    private fun setupRecyclerView() {
        binding.cartItemsRecyclerView.layoutManager = LinearLayoutManager(context)
        cartItemAdapter = CartItemAdapter(cartItems)
        binding.cartItemsRecyclerView.adapter = cartItemAdapter
    }

    fun updatePaymentOption(payment: String) {
        paymentOption = payment
        binding.paymentOptionTextView.text = payment
    }

    private fun loadData() {

        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            cartItems = items
            cartItemAdapter.updateItems(items)
            calculateTotalBill()
        }


        deliveryAddress = DeliveryAddress("HOME", "402 W. Marine Way, Suite 100, Ships Building,\nKodiak, Kodiak Island, AK, 99615")
        binding.deliveryAddressTextView.text = deliveryAddress.toString()

        binding.paymentOptionTextView.text = paymentOption
    }

    private fun calculateTotalBill() {
        totalBillAmount = cartItems.sumOf { it.productPrice * it.quantity }
        binding.totalBillAmountTextView.text = "$${"%.2f".format(totalBillAmount)}"
    }

    private fun placeOrderAndNavigate() {
        val orderItems = cartItems.map {
            OrderItem(it.productId.toInt(), it.quantity, it.productPrice)
        }

        val placeOrderRequest = deliveryAddress?.let {
            PlaceOrderRequest(
                userId = 1,
                it,
                orderItems,
                totalBillAmount,
                paymentOption
            )
        }

        lifecycleScope.launch {
            if (placeOrderRequest != null) {
                orderViewModel.placeOrder(placeOrderRequest)
            }
            orderViewModel.orderResponse.observe(viewLifecycleOwner) { response ->
                if (response != null && response.status == 0) {
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), OrderConfirmedActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), response?.message ?: "Order placement failed.", Toast.LENGTH_SHORT).show()
                }
            }

            orderViewModel.errorMessage.observe(viewLifecycleOwner){ error ->
                if (error != null) {
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
