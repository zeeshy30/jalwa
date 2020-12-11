package com.example.jalwa.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.R

class OptionsRecyclerViewAdapter(private val context: Context, private val options: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return options.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.variant_option_button,
            parent,
            false
        )
        return ViewHolderOptionsRecyclerView(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val option = options[position]
        val obj = holder as ViewHolderOptionsRecyclerView?
        obj?.bind(option)
    }

}