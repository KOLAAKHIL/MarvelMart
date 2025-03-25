package com.example.marvelmart.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.marvelmart.adapters.ProductAdapter
import com.example.marvelmart.adapters.SubcategoryAdapter
import com.example.marvelmart.adapters.SubcategoryPagerAdapter
import com.example.marvelmart.api.RetrofitClient
import com.example.marvelmart.databinding.ActivitySubcategoryBinding
import com.example.marvelmart.repositories.ProductRepository
import com.example.marvelmart.repositories.SubcategoryRepository
import com.example.marvelmart.viewmodels.ProductViewModel
import com.example.marvelmart.viewmodels.SubcategoryViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SubcategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubcategoryBinding
    private lateinit var viewPager: ViewPager2
    private val subcategoryViewModel: SubcategoryViewModel by viewModels {
        SubcategoryViewModel.SubcategoryViewModelFactory(SubcategoryRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubcategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryId = intent.getIntExtra("categoryId", -1)
        val categoryName = intent.getStringExtra("categoryName") ?: "Subcategories"

        binding.titleText.text = categoryName

        viewPager = binding.viewPager

        subcategoryViewModel.fetchSubcategories(categoryId)

        binding.backArrow.setOnClickListener{
            finish()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        subcategoryViewModel.subcategories.observe(this) { subcategories ->
            if (subcategories != null) {
                setupViewPager(subcategories)
            }
        }

        subcategoryViewModel.errorMessage.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupViewPager(subcategories: List<com.example.marvelmart.models.Subcategory>) {
        val pagerAdapter = SubcategoryPagerAdapter(this, subcategories)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = subcategories[position].subcategoryName
        }.attach()
    }
}