package com.example.jalwa.ui.main.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.ProductsQuery
import com.example.jalwa.databinding.ProductViewPageBinding
import com.example.jalwa.ui.main.view.ProductDetailBottomSheet

class ViewHolderProductsRecyclerView(private val binding: ProductViewPageBinding)
    : RecyclerView.ViewHolder(binding.root) {
    private lateinit var productsRecyclerViewAdapter: ProductsRecyclerViewAdapter
    fun openBottomSheet() {
        val supportFragmentManager = (productsRecyclerViewAdapter.getContext() as AppCompatActivity).supportFragmentManager
        ProductDetailBottomSheet().apply {
            show(supportFragmentManager, ProductDetailBottomSheet.TAG)
        }
    }

    fun bind(
        product: ProductsQuery.Product,
        productsRecyclerViewAdapter: ProductsRecyclerViewAdapter
    ) {
        this.productsRecyclerViewAdapter = productsRecyclerViewAdapter
        binding.apply {
            url = product.videoUrl
            callback = productsRecyclerViewAdapter
            index = adapterPosition
            title = product.title
            vendorName = product.vendor
            price = "Rs. ${product.price}"
            photoUrl = product.photoUrl
            openDetailSheet = this@ViewHolderProductsRecyclerView
            executePendingBindings()
        }
    }
}
