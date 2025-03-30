package com.example.marvelmart.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marvelmart.R
import com.example.marvelmart.databinding.FragmentCartItemsBinding

class CartItemsFragment : Fragment() {

    private lateinit var binding: FragmentCartItemsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.totalBillAmount.text = "$640"


        binding.item1Image.setImageResource(R.drawable.realme_50)
        binding.item1Name.text = "RealMe Nazro 50"
        binding.item1UnitPrice.text = "Unit Price: $200"
        binding.item1Quantity.text = "Quantity: 1"
        binding.item1Amount.text = "Amount: $200"


    }
}