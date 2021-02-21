package com.example.jalwa.ui.main.adapter

import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.CategoriesQuery
import com.example.jalwa.R
import com.example.jalwa.databinding.CategoryButtonBinding

class ViewHolderCategoriesRecyclerView(private val binding: CategoryButtonBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val categoryButton: AppCompatButton = binding.root.findViewById(R.id.categoryButton)

    fun bind(category: CategoriesQuery.Category) {
        binding.apply {
            categoryName = category.category
            executePendingBindings()
        }
    }
}