package com.example.jalwa.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.R
import com.example.jalwa.databinding.CategoryButtonBinding

class CategoriesRecyclerViewAdapter(private val context: Context, private val categories: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        obj?.bind(category)
    }

}