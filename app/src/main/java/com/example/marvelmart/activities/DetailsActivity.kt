package com.example.marvelmart.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.marvelmart.R
import com.example.marvelmart.databinding.ActivityDetailsBinding
import com.example.marvelmart.databinding.ProductItemBinding
import com.example.marvelmart.repositories.DetailsRepository
import com.example.marvelmart.viewmodels.DetailsViewModel

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = DetailsRepository()
        val factory = DetailsViewModel.DetailsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[DetailsViewModel::class.java]

        val productId = intent.getStringExtra("productId")
        if (productId != null) {
            viewModel.fetchProductDetails(productId)
        } else {
            Toast.makeText(this, "Product ID not found", Toast.LENGTH_SHORT).show()
            finish()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.productDetails.observe(this) { product ->
            if (product != null) {
                binding.productName.text = product.product_name
                binding.productDescription.text = product.description
                binding.productPrice.text = "$${product.price}"

                Glide.with(this)
                    .load(product.product_image_url)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(binding.productImage)
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage != null) {
                binding.errorMessage.text = errorMessage
                binding.errorMessage.visibility = View.VISIBLE
            } else {
                binding.errorMessage.visibility = View.GONE
            }
        }
    }
}