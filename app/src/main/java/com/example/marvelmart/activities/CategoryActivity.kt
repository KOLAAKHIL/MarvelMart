package com.example.marvelmart.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marvelmart.R
import com.example.marvelmart.adapters.CategoryAdapter
import com.example.marvelmart.databinding.ActivityCategoryBinding
import com.example.marvelmart.databinding.ActivityMainBinding
import com.example.marvelmart.repositories.CategoryRepository
import com.example.marvelmart.viewmodels.CategoryViewModel
import com.example.marvelmart.viewmodels.CategoryViewModelFactory
import com.google.android.material.navigation.NavigationView

class CategoryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var viewModel: CategoryViewModel
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = CategoryRepository()
        val factory = CategoryViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[CategoryViewModel::class.java]

        drawerLayout = binding.drawerLayout
        navigationView = binding.navView
        toolbar = binding.toolbar

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        categoryAdapter = CategoryAdapter(emptyList()) { categoryId, categoryName ->
            fetchSubcategories(categoryId, categoryName)
        }

//        binding.menuIcon.setOnClickListener {
//            drawerLayout.openDrawer(navigationView)
//        }

        binding.searchIcon.setOnClickListener {
            binding.searchEditText.visibility = View.VISIBLE
            val searchText = binding.searchEditText.text.toString()
            if (searchText.isNotEmpty()) {
                viewModel.searchCategories(searchText)
            } else {
                Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show()
            }
        }

        binding.categoryRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.categoryRecyclerView.adapter = categoryAdapter


        viewModel.categories.observe(this) { categories ->
            categoryAdapter.updateCategories(categories)
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.categoryProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString()
                if (searchText.isNotEmpty()) {
                    viewModel.searchCategories(searchText)
                } else {
                    viewModel.fetchCategories()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        viewModel.fetchCategories()
    }

    private fun fetchSubcategories(categoryId: Int, categoryName: String) {
        val intent = Intent(this, SubcategoryActivity::class.java)
        intent.putExtra("categoryId", categoryId)
        intent.putExtra("categoryName", categoryName)
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {

                Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_cart -> {

                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Cart clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_orders -> {
                val intent = Intent(this, OrderConfirmedActivity::class.java)
                startActivity(intent)

                Toast.makeText(this, "Orders clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_profile -> {
//                val intent = Intent(this, ProfileActivity::class.java)
//                startActivity(intent)
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(navigationView)
        return true
    }
}