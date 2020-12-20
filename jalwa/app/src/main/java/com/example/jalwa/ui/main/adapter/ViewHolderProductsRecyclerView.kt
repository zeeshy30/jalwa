package com.example.jalwa.ui.main.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.ProductsQuery
import com.example.jalwa.databinding.ProductViewPageBinding
import com.example.jalwa.ui.main.view.ProductDetailBottomSheet

class ViewHolderProductsRecyclerView(private val binding: ProductViewPageBinding)
    : RecyclerView.ViewHolder(binding.root) {
    private lateinit var productsRecyclerViewAdapter: ProductsRecyclerViewAdapter
    private lateinit var product: ProductsQuery.Product
    fun openBottomSheet() {
        val supportFragmentManager = (productsRecyclerViewAdapter.getContext() as AppCompatActivity).supportFragmentManager
        val bundle = Bundle()
        bundle.putString("photoUrl", product.photoUrl)
        bundle.putString("title", product.title)
        bundle.putString("price", product.price)
        ProductDetailBottomSheet().apply {
            arguments = bundle
            show(supportFragmentManager, ProductDetailBottomSheet.TAG)
        }
    }

    fun bind(
        product: ProductsQuery.Product,
        productsRecyclerViewAdapter: ProductsRecyclerViewAdapter
    ) {
        this.product = product
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
