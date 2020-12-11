package com.example.jalwa.ui.main.adapter

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.R

class ViewHolderCategoriesRecyclerView(v: View) : RecyclerView.ViewHolder(v) {
    private val categoryButton: AppCompatButton = v.findViewById(R.id.category)


    fun bind(category: String) {
        categoryButton.text = category
    }
}