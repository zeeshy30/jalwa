package com.example.jalwa.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.ProductsQuery
import com.example.jalwa.R
import com.example.jalwa.ui.main.view.ProductDetailBottomSheet
import com.squareup.picasso.Picasso

class ViewHolderProductsRecyclerView(v: View) : RecyclerView.ViewHolder(v) {
    private val title: TextView = v.findViewById(R.id.productTitle)
    private val imageView: ImageView = v.findViewById(R.id.productImage)
    private val videoView: VideoView = v.findViewById(R.id.videoView)
    private val productPrice: TextView = v.findViewById(R.id.productPrice)
    private val vendor: TextView = v.findViewById(R.id.vendor)
    private val buyNowButton: AppCompatButton = v.findViewById(R.id.buyNowProduct)

    private fun playVideo(context: Context, url: String) {
        val controller = MediaController(context)
        controller.setMediaPlayer(videoView)
        controller.isEnabled = true
        controller.show()
        controller.setMediaPlayer(videoView)
        videoView.setMediaController(controller)
        videoView.setVideoPath(url)
        videoView.start()
    }

    private fun clickListener(context: Context) {
        val supportFragmentManager = (context as AppCompatActivity).supportFragmentManager
        buyNowButton.setOnClickListener {
            ProductDetailBottomSheet().apply {
                show(supportFragmentManager, ProductDetailBottomSheet.TAG)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun bind(context: Context, product: ProductsQuery.Product) {
        title.text = product.title
        productPrice.text = "Rs. ${product.price}"
        vendor.text = product.vendor
        clickListener(context)

        Picasso.with(context).load(product.photoUrl).into(imageView)
        playVideo(context, product.videoUrl)
    }

}