package com.example.marvelmart.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelmart.R
import com.example.marvelmart.activities.DetailsActivity
import com.example.marvelmart.adapters.ProductAdapter
import com.example.marvelmart.databinding.FragmentProductListBinding
import com.example.marvelmart.models.Product
import com.example.marvelmart.repositories.ProductRepository
import com.example.marvelmart.viewmodels.ProductViewModel

class ProductListFragment : Fragment(), ProductAdapter.OnProductClickListener {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private lateinit var productAdapter: ProductAdapter
    private val productViewModel: ProductViewModel by viewModels {
        ProductViewModel.ProductViewModelFactory(ProductRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter = ProductAdapter(emptyList(), this)


        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewProducts.adapter = productAdapter

        val subcategoryId = arguments?.getInt(ARG_SUBCATEGORY_ID) ?: -1
        if (subcategoryId != -1) {
            productViewModel.fetchProducts(subcategoryId)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        productViewModel.products.observe(viewLifecycleOwner) { products ->
            if (products != null) {
                Log.d("Products", "list of products is ${products.toString()}")
                productAdapter.updateProducts(products)
            }
        }

        productViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_SUBCATEGORY_ID = "subcategory_id"

        fun newInstance(subcategoryId: Int): ProductListFragment {
            val fragment = ProductListFragment()
            val args = Bundle().apply {
                putInt(ARG_SUBCATEGORY_ID, subcategoryId)
            }
            fragment.arguments = args
            return fragment
        }
    }
        override fun onProductClicked(product: Product) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("productId", product.product_id)
            startActivity(intent)
        }
    }