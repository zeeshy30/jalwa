package com.example.jalwa.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.jalwa.R
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.ProductsQuery
import com.example.jalwa.databinding.ProductViewPageBinding
import com.example.jalwa.ui.main.view.ProductView
import com.example.jalwa.utils.ExoPlayerStateCallback
import com.example.jalwa.utils.PlayerViewAdapter
import com.google.android.exoplayer2.Player
import kotlinx.android.synthetic.main.exo_player_control_view.*
import kotlinx.android.synthetic.main.product_view_page.*
import kotlin.properties.Delegates

class ProductsRecyclerViewAdapter(
    private val context: Context,
    private val products: ArrayList<ProductsQuery.Product>,
    private val productView: ProductView
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ExoPlayerStateCallback {
    private var currentPosition by Delegates.notNull<Int>()
    private lateinit var binding: ProductViewPageBinding

    fun getContext (): Context {
        return context
    }
    override fun getItemCount(): Int {
        return products.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.product_view_page, parent,
            false
        )
        return ViewHolderProductsRecyclerView(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = products[position]
        currentPosition = position
        val obj = holder as ViewHolderProductsRecyclerView?
        obj?.bind(product, this@ProductsRecyclerViewAdapter)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        val position = holder.adapterPosition
        PlayerViewAdapter.releaseRecycledPlayers(position)
        super.onViewRecycled(holder)
    }

    override fun onFinishedPlaying(player: Player) {
        productView.scrollToNextVideo()
    }

    override fun onStartedPlaying(player: Player) {
        productView.playPause.visibility = View.VISIBLE
    }

    override fun onVideoBuffering(player: Player) {
        productView.playPause.visibility = View.INVISIBLE
    }

    override fun onVideoDurationRetrieved(duration: Long, player: Player) {
        productView.playPause.visibility = View.VISIBLE
    }
}