package com.example.marvelmart.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marvelmart.R
import com.example.marvelmart.adapters.CategoryAdapter
import com.example.marvelmart.databinding.ActivityCategoryBinding
import com.example.marvelmart.databinding.ActivityMainBinding
import com.example.marvelmart.repositories.CategoryRepository
import com.example.marvelmart.viewmodels.CategoryViewModel
import com.example.marvelmart.viewmodels.CategoryViewModelFactory

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var viewModel: CategoryViewModel
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = CategoryRepository()
        val factory = CategoryViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[CategoryViewModel::class.java]


        categoryAdapter = CategoryAdapter(emptyList()) { categoryId, categoryName ->
            fetchSubcategories(categoryId, categoryName)
        }

        binding.categoryRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.categoryRecyclerView.adapter = categoryAdapter

        // Observe LiveData from ViewModel
        viewModel.categories.observe(this) { categories ->
            categoryAdapter.updateCategories(categories)
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.categoryProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }


        viewModel.fetchCategories()
    }

    private fun fetchSubcategories(categoryId: Int, categoryName: String) {

        val intent = Intent(this, SubcategoryActivity::class.java)
        intent.putExtra("categoryId", categoryId)
        intent.putExtra("categoryName", categoryName)
        startActivity(intent)
    }
}