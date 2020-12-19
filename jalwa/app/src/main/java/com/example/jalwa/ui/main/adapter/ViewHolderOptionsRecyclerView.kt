package com.example.jalwa.ui.main.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.R

class ViewHolderOptionsRecyclerView(v: View) : RecyclerView.ViewHolder(v) {
    private val option: TextView = v.findViewById(R.id.variant)

    fun bind(variant: String) {
        option.text = variant
    }
}