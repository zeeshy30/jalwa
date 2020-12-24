package com.example.jalwa.ui.main.adapter

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.databinding.CategoryButtonBinding

class ViewHolderCategoriesRecyclerView(private val binding: CategoryButtonBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(category: String) {
        binding.apply {
            categoryName = category
            executePendingBindings()
        }
    }
}