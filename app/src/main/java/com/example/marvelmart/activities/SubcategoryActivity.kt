package com.example.marvelmart.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelmart.adapters.SubcategoryAdapter
import com.example.marvelmart.api.RetrofitClient
import com.example.marvelmart.databinding.ActivitySubcategoryBinding
import com.example.marvelmart.repositories.SubcategoryRepository
import com.example.marvelmart.viewmodels.SubcategoryViewModel
import com.example.marvelmart.viewmodels.SubcategoryViewModelFactory

class SubcategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubcategoryBinding
    private lateinit var viewModel: SubcategoryViewModel
    private lateinit var subcategoryAdapter: SubcategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubcategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryId = intent.getIntExtra("categoryId", -1)
        val categoryName = intent.getStringExtra("categoryName") ?: "Subcategories"

        binding.toolbarTitle.text = categoryName

        // Initialize ApiService and Repository
        val apiService = RetrofitClient.apiService // Use RetrofitClient.apiService
        val repository = SubcategoryRepository(apiService)

        // Initialize ViewModel and ViewModelFactory
        val factory = SubcategoryViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[SubcategoryViewModel::class.java]

        subcategoryAdapter = SubcategoryAdapter(emptyList()) { subcategory ->
            // Handle subcategory click here (e.g., navigate to product listing)
            Toast.makeText(this, "Clicked: ${subcategory.subcategoryName}", Toast.LENGTH_SHORT).show()
        }

        binding.subcategoryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.subcategoryRecyclerView.adapter = subcategoryAdapter

        // Observe Subcategories LiveData
        viewModel.subcategories.observe(this) { subcategories ->
            if (subcategories != null) {
                subcategoryAdapter.updateSubcategories(subcategories)
            }
        }

        // Observe Error Message LiveData
        viewModel.errorMessage.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        // Observe Loading State LiveData
        viewModel.isLoading.observe(this) { isLoading ->
            binding.subcategoryProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Fetch subcategories if categoryId is valid
        if (categoryId != -1) {
            viewModel.fetchSubcategories(categoryId)
        }

        // Set up toolbar back navigation
        binding.menuIcon.setOnClickListener {
            onBackPressed()
        }
    }
}