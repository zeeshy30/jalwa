package com.example.jalwa.ui.main.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.ProductsQuery
import com.example.jalwa.databinding.ProductViewPageBinding
import com.example.jalwa.ui.main.view.ProductDetailBottomSheet
import com.squareup.picasso.Picasso

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
//class ViewHolderProductsRecyclerView(v: View) : RecyclerView.ViewHolder(v) {
//    private val title: TextView = v.findViewById(R.id.productTitle)
//    private val imageView: ImageView = v.findViewById(R.id.productImage)
//    private val videoView: VideoView = v.findViewById(R.id.videoView)
//    private val productPrice: TextView = v.findViewById(R.id.productPrice)
//    private val vendor: TextView = v.findViewById(R.id.vendor)
//    private val buyNowButton: AppCompatButton = v.findViewById(R.id.buyNowProduct)
//
//    private fun playVideo(context: Context, url: String) {
//        val controller = MediaController(context)
//        controller.setMediaPlayer(videoView)
//        controller.isEnabled = true
//        controller.show()
//        controller.setMediaPlayer(videoView)
//        videoView.setMediaController(controller)
//        videoView.setVideoPath(url)
//        videoView.start()
//    }
//
//    private fun clickListener(context: Context) {
//        val supportFragmentManager = (context as AppCompatActivity).supportFragmentManager
//        buyNowButton.setOnClickListener {
//            ProductDetailBottomSheet().apply {
//                show(supportFragmentManager, ProductDetailBottomSheet.TAG)
//            }
//        }
//    }
//
//    @SuppressLint("SetTextI18n")
//    fun bind(context: Context, product: ProductsQuery.Product) {
//        title.text = product.title
//        productPrice.text = "Rs. ${product.price}"
//        vendor.text = product.vendor
//        clickListener(context)
//
//        Picasso.with(context).load(product.photoUrl).into(imageView)
//        playVideo(context, product.videoUrl)
//    }
//
//}