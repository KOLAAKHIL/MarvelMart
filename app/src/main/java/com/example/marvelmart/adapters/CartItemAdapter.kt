package com.example.marvelmart.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelmart.R
import com.example.marvelmart.models.CartItem

class CartItemAdapter(
    private var cartItems: List<CartItem>,
    private val itemQuantityChangeListener: ItemQuantityChangeListener? = null
) : RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {

    interface ItemQuantityChangeListener {
        fun onQuantityChanged(cartItem: CartItem, newQuantity: Int)
    }

    inner class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImageView: ImageView = itemView.findViewById(R.id.itemImageView)
        val itemNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        val unitPriceTextView: TextView = itemView.findViewById(R.id.unitPriceTextView)
        val quantityTextView: TextView = itemView.findViewById(R.id.quantityTextView)
        val amountTextView: TextView = itemView.findViewById(R.id.amountTextView)
        val minusButton: View? = itemView.findViewById(R.id.minusButton)
        val plusButton: View? = itemView.findViewById(R.id.plusButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_item_layout, parent, false)
        return CartItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val currentItem = cartItems[position]

        holder.itemNameTextView.text = currentItem.productName
        holder.unitPriceTextView.text = "Unit Price: $${"%.2f".format(currentItem.productPrice)}"
        holder.quantityTextView.text = "Quantity: ${currentItem.quantity}"
        holder.amountTextView.text = "Amount: $${"%.2f".format(currentItem.productPrice * currentItem.quantity)}"

        Glide.with(holder.itemImageView.context)
            .load(currentItem.imageUrl)
            .placeholder(R.drawable.ic_launcher_background) // Default placeholder image
            .error(R.drawable.ic_launcher_background) // Default error image
            .into(holder.itemImageView)



        holder.minusButton?.setOnClickListener {
            val currentQuantity = currentItem.quantity
            if (currentQuantity > 1) {
                itemQuantityChangeListener?.onQuantityChanged(currentItem, currentQuantity - 1)
            }
        }

        holder.plusButton?.setOnClickListener {
            val currentQuantity = currentItem.quantity
            itemQuantityChangeListener?.onQuantityChanged(currentItem, currentQuantity + 1)
        }
    }

    override fun getItemCount() = cartItems.size

    fun updateItems(newCartItems: List<CartItem>) {
        cartItems = newCartItems
        notifyDataSetChanged()
    }
}