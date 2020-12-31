package com.example.jalwa.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.CategoriesQuery
import com.example.jalwa.R
import com.example.jalwa.databinding.CategoryButtonBinding
import com.example.jalwa.utils.PlayerViewAdapter

class CategoriesRecyclerViewAdapter(
    private val categories: ArrayList<CategoriesQuery.Category>,
    private val getProductsFilteredByCategory: (String) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var selectedIndex = 0
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
        obj?.categoryButton?.isSelected = selectedIndex == position
        obj?.bind(category)
        obj?.categoryButton?.setOnClickListener {
            previousSelectedIndex = selectedIndex
            selectedIndex = position
            getProductsFilteredByCategory(category.category)
            PlayerViewAdapter.releaseAllPlayers()
            notifyItemChanged(previousSelectedIndex)
            notifyItemChanged(selectedIndex)
        }
    }

}