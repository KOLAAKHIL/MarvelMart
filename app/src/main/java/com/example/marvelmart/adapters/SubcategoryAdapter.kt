package com.example.marvelmart.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelmart.R
import com.example.marvelmart.databinding.ItemSubcategoryBinding
import com.example.marvelmart.models.Subcategory

class SubcategoryAdapter(
    private var subcategories: List<Subcategory>,
    private val onItemClick: (Subcategory) -> Unit
) : RecyclerView.Adapter<SubcategoryAdapter.SubcategoryViewHolder>() {

    class SubcategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subcategoryName: TextView = itemView.findViewById(R.id.subcategoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubcategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subcategory, parent, false)
        return SubcategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubcategoryViewHolder, position: Int) {
        val subcategory = subcategories[position]
        holder.subcategoryName.text = subcategory.subcategoryName
        holder.itemView.setOnClickListener { onItemClick(subcategory) }
    }

    override fun getItemCount(): Int = subcategories.size

    fun updateSubcategories(subcategories: List<Subcategory>) {
        this.subcategories = subcategories
        notifyDataSetChanged()
    }
}