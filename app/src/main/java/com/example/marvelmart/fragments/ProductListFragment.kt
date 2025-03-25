package com.example.marvelmart.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelmart.adapters.ProductAdapter
import com.example.marvelmart.databinding.FragmentProductListBinding
import com.example.marvelmart.repositories.ProductRepository
import com.example.marvelmart.viewmodels.ProductViewModel

class ProductListFragment : Fragment() {

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

        productAdapter = ProductAdapter(emptyList())
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
}