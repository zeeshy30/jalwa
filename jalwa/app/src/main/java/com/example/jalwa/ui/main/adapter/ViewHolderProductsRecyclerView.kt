package com.example.jalwa.ui.main.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.ProductsFilteredByCategoryQuery
import com.example.jalwa.databinding.ProductViewPageBinding
import com.example.jalwa.ui.main.view.ProductDetailBottomSheet
import com.example.jalwa.utils.PlayerViewAdapter

class ViewHolderProductsRecyclerView(private val binding: ProductViewPageBinding)
    : RecyclerView.ViewHolder(binding.root) {
    private lateinit var productsRecyclerViewAdapter: ProductsRecyclerViewAdapter
    private lateinit var product: ProductsFilteredByCategoryQuery.Product
    fun openBottomSheet() {
        val supportFragmentManager = (productsRecyclerViewAdapter.getContext() as AppCompatActivity).supportFragmentManager
        val bundle = Bundle()
        PlayerViewAdapter.pauseCurrentPlayingVideo()
        bundle.putString("photoUrl", product.photoUrl)
        bundle.putString("handle", product.handle)
        bundle.putString("title", product.title)
        bundle.putString("price", product.price)
        bundle.putString("body", product.body)
        ProductDetailBottomSheet().apply {
            arguments = bundle
            show(supportFragmentManager, ProductDetailBottomSheet.TAG)
        }
    }

    fun bind(
        productModel: ProductsFilteredByCategoryQuery.Product,
        productsRecyclerViewAdapter: ProductsRecyclerViewAdapter
    ) {
        this.product = productModel
        this.productsRecyclerViewAdapter = productsRecyclerViewAdapter
        binding.apply {
            callback = productsRecyclerViewAdapter
            index = adapterPosition
            title = productModel.title
            price = productModel.price
            videoUrl = productModel.videoUrl
            photoUrl = productModel.photoUrl
            vendorName = productModel.vendor
            openDetailSheet = this@ViewHolderProductsRecyclerView
            executePendingBindings()
        }
    }
}
