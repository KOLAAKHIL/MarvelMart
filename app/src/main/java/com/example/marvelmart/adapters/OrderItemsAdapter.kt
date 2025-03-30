package com.example.marvelmart.adapters

import android.media.RouteListingPreference
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelmart.R
import com.example.marvelmart.models.Item

class OrderItemsAdapter(private val items: List<Item>) : RecyclerView.Adapter<OrderItemsAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImageView: ImageView = itemView.findViewById(R.id.itemImageView)
        val itemNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        val unitPriceTextView: TextView = itemView.findViewById(R.id.unitPriceTextView)
        val quantityTextView: TextView = itemView.findViewById(R.id.quantityTextView)
        val amountTextView: TextView = itemView.findViewById(R.id.amountTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item_layout, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = items[position]


        holder.itemImageView.setImageResource(R.drawable.realme_50)

        holder.itemNameTextView.text = item.name
        holder.unitPriceTextView.text = "Unit Price: $${item.unitPrice}"
        holder.quantityTextView.text = "Quantity: ${item.quantity}"
        holder.amountTextView.text = "Amount: $${item.amount}"
    }

    override fun getItemCount(): Int = items.size
}