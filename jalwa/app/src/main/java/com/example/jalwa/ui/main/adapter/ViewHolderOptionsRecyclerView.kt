package com.example.jalwa.ui.main.adapter

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.R
import com.example.jalwa.databinding.VariantOptionButtonBinding

class ViewHolderOptionsRecyclerView(private val binding: VariantOptionButtonBinding) : RecyclerView.ViewHolder(binding.root) {
    val variantButton: TextView = binding.root.findViewById(R.id.variant)

    fun bind(variant: String) {
        binding.apply {
            option = variant
            executePendingBindings()
        }
    }
}