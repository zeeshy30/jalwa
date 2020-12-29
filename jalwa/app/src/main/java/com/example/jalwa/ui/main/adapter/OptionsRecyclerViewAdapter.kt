package com.example.jalwa.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.R
import com.example.jalwa.databinding.ProductViewPageBinding
import com.example.jalwa.databinding.VariantOptionButtonBinding

class OptionsRecyclerViewAdapter(private val options: MutableSet<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return options.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<VariantOptionButtonBinding>(
            LayoutInflater.from(parent.context),
            R.layout.variant_option_button,
            parent,
            false
        )
        return ViewHolderOptionsRecyclerView(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val option = options.elementAt(position)
        val obj = holder as ViewHolderOptionsRecyclerView?
        obj?.bind(option)
    }

}