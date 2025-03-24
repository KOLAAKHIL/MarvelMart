package com.example.marvelmart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelmart.R
import com.example.marvelmart.databinding.ItemSubcategoryBinding
import com.example.marvelmart.models.Subcategory

class SubcategoryAdapter(
    private var subcategories: List<Subcategory>,
    private val onItemClick: (Subcategory) -> Unit = {}
) : RecyclerView.Adapter<SubcategoryAdapter.SubcategoryViewHolder>() {

    inner class SubcategoryViewHolder(val binding: ItemSubcategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubcategoryViewHolder {
        val binding = ItemSubcategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubcategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubcategoryViewHolder, position: Int) {
        val subcategory = subcategories[position]
        holder.binding.subcategoryName.text = subcategory.subcategoryName
        Glide.with(holder.binding.subcategoryImage.context)
            .load(subcategory.subcategoryImageUrl) // Use subcategoryImageUrl
            .placeholder(R.drawable.ic_launcher_background) // Optional: Placeholder image
            .error(R.drawable.ic_launcher_background) // Optional: Error image
            .into(holder.binding.subcategoryImage)

        holder.itemView.setOnClickListener {
            onItemClick(subcategory)
        }
    }

    override fun getItemCount(): Int {
        return subcategories.size
    }

    fun updateSubcategories(newList: List<Subcategory>) {
        subcategories = newList
        notifyDataSetChanged()
    }
}