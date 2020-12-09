package com.example.jalwa.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.ProductsQuery
import com.example.jalwa.R
import com.squareup.picasso.Picasso

class ProductsRecyclerViewAdapter(private val context: Context, private val products: ArrayList<ProductsQuery.Product>) :
    RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.productTitle)
        val imageView: ImageView = itemView.findViewById(R.id.productImage)
        val videoView: VideoView = itemView.findViewById(R.id.videoView)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.product_view_page,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]

        holder.title.text = product.title
        holder.productPrice.text = "Rs. ${product.price}"

        Picasso.with(context).load(product.photoUrl).into(holder.imageView)

        val controller = MediaController(this.context)
        controller.setMediaPlayer(holder.videoView)
        controller.isEnabled = true
        controller.show()
        controller.setMediaPlayer(holder.videoView)
        holder.videoView.setMediaController(controller)
        holder.videoView.setVideoPath(product.videoUrl)
        holder.videoView.start()
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.videoView.start()
    }

}