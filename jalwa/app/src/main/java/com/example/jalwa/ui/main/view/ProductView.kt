package com.example.jalwa.ui.main.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.jalwa.CategoriesQuery
import com.example.jalwa.ProductsFilteredByCategoryQuery
import com.example.jalwa.R
import com.example.jalwa.ui.main.adapter.CategoriesRecyclerViewAdapter
import com.example.jalwa.ui.main.adapter.ProductsRecyclerViewAdapter
import com.example.jalwa.ui.main.viewmodel.ProductViewModel
import com.example.jalwa.utils.PlayerViewAdapter
import com.example.jalwa.utils.RecyclerViewScrollListener
import kotlinx.android.synthetic.main.products.*
import kotlinx.android.synthetic.main.top_bar_of_product_page.*
import org.koin.android.ext.android.inject


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

class ProductView : Fragment() {
    private val viewModel: ProductViewModel by inject()
    private lateinit var scrollListener: RecyclerViewScrollListener
    private var currentPosition = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.products, container, false)
    }

    fun scrollToNextVideo() {
        videoViews.smoothScrollToPosition(currentPosition + 1)
    }

    private fun customScrollListener() {
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(videoViews)
        scrollListener = object : RecyclerViewScrollListener() {
            override fun onItemIsFirstVisibleItem(index: Int) {
                if (index != -1)
                    PlayerViewAdapter.playIndexThenPausePreviousPlayer(index)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState === RecyclerView.SCROLL_STATE_IDLE) {
                    currentPosition = (videoViews.layoutManager as LinearLayoutManager?)!!
                        .findFirstCompletelyVisibleItemPosition()
                    PlayerViewAdapter.playCurrentPlayingVideo()
                }
            }

        }
        videoViews.addOnScrollListener(scrollListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customScrollListener()
        viewModel.productsObservable.observe(viewLifecycleOwner, {
            if (videoViews != null && videoViews.adapter != null) {
                videoViews.adapter?.notifyDataSetChanged()
            }
            videoViews.apply {
                adapter = ProductsRecyclerViewAdapter(
                    activity as Activity,
                    viewModel.productList as ArrayList<ProductsFilteredByCategoryQuery.ProductsFilteredByCategory>,
                    this@ProductView
                )
            }
        })

        viewModel.categoriesObservable.observe(viewLifecycleOwner, {
            categories.apply {
                adapter = CategoriesRecyclerViewAdapter(
                    viewModel.categoryList as ArrayList<CategoriesQuery.Category>,
                    ::getProductsFilteredByCategory
                )
            }
        })
    }

    private fun getProductsFilteredByCategory(category: String) {
        viewModel.getProductsFilteredByCategory(category)
    }

    override fun onPause() {
        super.onPause()
        PlayerViewAdapter.pauseCurrentPlayingVideo()
    }

    override fun onResume() {
        super.onResume()
        PlayerViewAdapter.playCurrentPlayingVideo()
    }

    override fun onStop() {
        super.onStop()
        PlayerViewAdapter.pauseCurrentPlayingVideo()
    }

}

