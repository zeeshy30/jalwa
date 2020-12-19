package com.example.jalwa.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.jalwa.R
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.ProductsQuery
import com.example.jalwa.databinding.ProductViewPageBinding
import com.example.jalwa.utils.ExoPlayerStateCallback
import com.example.jalwa.utils.PlayerViewAdapter
import com.google.android.exoplayer2.Player


class ProductsRecyclerViewAdapter(
    private val context: Context,
    private val products: ArrayList<ProductsQuery.Product>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ExoPlayerStateCallback {

    fun getContext (): Context {
        return context
    }
    override fun getItemCount(): Int {
        return products.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ProductViewPageBinding>(
            LayoutInflater.from(parent.context),
            R.layout.product_view_page, parent,
            false
        )
        return ViewHolderProductsRecyclerView(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = products[position]
        val obj = holder as ViewHolderProductsRecyclerView?
        obj?.bind(product, this@ProductsRecyclerViewAdapter)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        val position = holder.adapterPosition
        PlayerViewAdapter.releaseRecycledPlayers(position)
        super.onViewRecycled(holder)
    }

    override fun onFinishedPlaying(player: Player) {
        println("bufferrrr3")
    }

    override fun onStartedPlaying(player: Player) {
        println("bufferrrr1")
    }

    override fun onVideoBuffering(player: Player) {
        println("bufferrrr")
    }

    override fun onVideoDurationRetrieved(duration: Long, player: Player) {
        println("duraaationnn: $duration")
    }
}