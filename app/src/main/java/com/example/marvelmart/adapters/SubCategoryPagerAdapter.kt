package com.example.marvelmart.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.marvelmart.fragments.ProductListFragment
import com.example.marvelmart.models.Product
import com.example.marvelmart.models.Subcategory

class SubcategoryPagerAdapter(fragmentActivity: FragmentActivity, private val subcategories: List<Subcategory>) :
    FragmentStateAdapter(fragmentActivity) {



    override fun getItemCount(): Int = subcategories.size

    override fun createFragment(position: Int): Fragment {
        val subcategory = subcategories[position]
        return ProductListFragment.newInstance(subcategory.subcategoryId.toInt())
    }

}