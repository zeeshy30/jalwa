package com.example.jalwa.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.CategoriesQuery
import com.example.jalwa.R
import com.example.jalwa.databinding.CategoryButtonBinding
import com.example.jalwa.utils.PlayerViewAdapter

class CategoriesRecyclerViewAdapter(
    private val categories: ArrayList<CategoriesQuery.Category?>,
    private val getProductsFilteredByCategory: (String, Int) -> Unit,
    private var selectedCategory: Int
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var previousSelectedIndex = -1
    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<CategoryButtonBinding>(
            LayoutInflater.from(parent.context),
            R.layout.category_button,
            parent,
            false
        )
        return ViewHolderCategoriesRecyclerView(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val category = categories[position]
        val obj = holder as ViewHolderCategoriesRecyclerView?
        obj?.categoryButton?.isSelected = selectedCategory == position
        obj?.bind(category!!)
        obj?.categoryButton?.setOnClickListener {
            previousSelectedIndex = selectedCategory
            selectedCategory = position
            getProductsFilteredByCategory(category!!.category, position)
            PlayerViewAdapter.releaseAllPlayers()
            notifyItemChanged(previousSelectedIndex)
            notifyItemChanged(selectedCategory)
        }
    }

}