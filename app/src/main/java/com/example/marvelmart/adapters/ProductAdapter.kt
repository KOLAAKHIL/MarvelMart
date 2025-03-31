package com.example.marvelmart.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelmart.R
import com.example.marvelmart.databinding.ProductItemBinding
import com.example.marvelmart.models.Product

class ProductAdapter(
    private var products: List<Product>,
    private val clickListener: OnProductClickListener
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    interface OnProductClickListener {
        fun onProductClicked(product: Product)
        fun onAddToCartClicked(product: Product, quantity: Int)
    }

    inner class ProductViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productName.text = product.product_name
            binding.productDescription.text = product.description
            binding.productPrice.text = "$${product.price}"
            binding.productRating.rating = product.average_rating.toFloatOrNull() ?: 0f

            Glide.with(binding.root.context)
                .load(product.product_image_url)
                .placeholder(R.drawable.realme_50)
                .error(R.drawable.realme_50)
                .into(binding.productImage)

            binding.addToCartButton.setOnClickListener {
                clickListener.onAddToCartClicked(product, 1)
                binding.addToCartButton.visibility = View.GONE
                binding.quantityLayout.visibility = View.VISIBLE
            }

            binding.minusButton.setOnClickListener {
                val currentQuantity = binding.quantityTextView.text.toString().toInt()
                if (currentQuantity > 1) {
                    binding.quantityTextView.text = (currentQuantity - 1).toString()
                    clickListener.onAddToCartClicked(product, currentQuantity - 1)
                }
            }

            binding.plusButton.setOnClickListener {
                val currentQuantity = binding.quantityTextView.text.toString().toInt()
                binding.quantityTextView.text = (currentQuantity + 1).toString()
                clickListener.onAddToCartClicked(product, currentQuantity + 1)
            }

//            binding.quantityTextView.text = "1"
//            binding.quantityLayout.visibility = View.GONE

            itemView.setOnClickListener {
                clickListener.onProductClicked(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}