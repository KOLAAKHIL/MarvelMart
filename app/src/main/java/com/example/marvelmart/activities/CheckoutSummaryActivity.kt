package com.example.marvelmart.activities

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.marvelmart.R
import com.example.marvelmart.api.ApiService
import com.example.marvelmart.api.RetrofitClient
import com.example.marvelmart.databinding.ActivityCheckoutSummaryBinding
import com.example.marvelmart.databinding.DialogAddAddressBinding
import com.example.marvelmart.fragments.CartItemsFragment
import com.example.marvelmart.fragments.DeliveryFragment
import com.example.marvelmart.fragments.PaymentFragment
import com.example.marvelmart.fragments.SummaryFragment
import com.example.marvelmart.models.Address
import com.example.marvelmart.repositories.AddressRepository
import com.example.marvelmart.viewmodels.AddressViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CheckoutSummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutSummaryBinding
    private lateinit var viewModel: AddressViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService: ApiService = RetrofitClient.apiService
        val repository = AddressRepository(apiService)

        viewModel = ViewModelProvider(
            this,
            AddressViewModel.AddressViewModelFactory(repository)
        )[AddressViewModel::class.java]

        binding.addAddressButton.setOnClickListener {
            showAddAddressDialog()
        }
        binding.backArrow.setOnClickListener{
            finish()
        }

        val tabLayout: TabLayout = binding.tabLayout
        val viewPager: ViewPager2 = binding.viewPager


        val adapter = CheckoutPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Cart Items"
                1 -> "Delivery"
                2 -> "Payment"
                3 -> "Summary"
                else -> ""
            }
        }.attach()

        binding.nextButton.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem < adapter.itemCount - 1) {
                viewPager.currentItem = currentItem + 1
            }
        }

        viewModel.addressAddedSuccessfully.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Address added successfully", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAddAddressDialog() {
        val dialogView = DialogAddAddressBinding.inflate(layoutInflater)

        AlertDialog.Builder(this)
            .setView(dialogView.root)
            .setPositiveButton("Save Address") { dialog, _ ->
                val title = dialogView.addressTitleEditText.text.toString()
                val address = dialogView.addressDetailsEditText.text.toString()
                val addressData = Address(
                    user_id = 2,
                    title = title,
                    address = address
                )
                viewModel.addAddress(addressData)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    private inner class CheckoutPagerAdapter(activity: AppCompatActivity) :
        FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> CartItemsFragment()
                1 -> DeliveryFragment()
                2 -> PaymentFragment()
                3 -> SummaryFragment()
                else -> Fragment()
            }
        }
    }
}







