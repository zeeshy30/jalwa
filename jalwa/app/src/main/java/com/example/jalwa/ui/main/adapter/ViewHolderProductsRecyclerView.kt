package com.example.jalwa.ui.main.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.ProductsQuery
import com.example.jalwa.R
import com.squareup.picasso.Picasso

class ViewHolderProductsRecyclerView(v: View) : RecyclerView.ViewHolder(v) {
    private val title: TextView = v.findViewById(R.id.productTitle)
    private val imageView: ImageView = v.findViewById(R.id.productImage)
    private val videoView: VideoView = v.findViewById(R.id.videoView)
    private val productPrice: TextView = v.findViewById(R.id.productPrice)
    private val vendor: TextView = v.findViewById(R.id.vendor)

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

    fun bind(context: Context, product: ProductsQuery.Product) {
        title.text = product.title
        productPrice.text = "Rs. ${product.price}"
        vendor.text = product.vendor

        Picasso.with(context).load(product.photoUrl).into(imageView)
        playVideo(context, product.videoUrl)
    }

}