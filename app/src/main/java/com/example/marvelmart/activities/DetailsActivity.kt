package com.example.marvelmart.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.marvelmart.R
import com.example.marvelmart.adapters.ReviewAdapter
import com.example.marvelmart.databinding.ActivityDetailsBinding
import com.example.marvelmart.models.Review
import com.example.marvelmart.models.Specification
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
        binding.backArrow.setOnClickListener{
            finish()
        }
        binding.buttonChtout.setOnClickListener{
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
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


                displaySpecifications(product.specifications)


                displayReviews(product.reviews)
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

    private fun displaySpecifications(specifications: List<Specification>) {
        val specificationsLayout = binding.specificationsLayout
        specificationsLayout.removeAllViews()

        for (spec in specifications) {
            val textView = TextView(this)
            textView.text = "${spec.title}: ${spec.specification}"
            specificationsLayout.addView(textView)
        }
    }

    private fun displayReviews(reviews: List<Review>) {
        binding.reviewsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.reviewsRecyclerView.adapter = ReviewAdapter(reviews)
    }
}