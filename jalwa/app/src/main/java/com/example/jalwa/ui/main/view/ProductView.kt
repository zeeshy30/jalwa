package com.example.jalwa.ui.main.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.jalwa.ProductsQuery
import com.example.jalwa.R
import com.example.jalwa.ui.main.adapter.ProductsRecyclerViewAdapter
import com.example.jalwa.ui.main.viewmodel.ProductViewModel
import org.koin.android.ext.android.inject


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

class ProductView : Fragment() {
    val viewModel: ProductViewModel by inject()
    private lateinit var adapter: ProductsRecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView
//    private lateinit var videoView: VideoView
//    private lateinit var imageView: ImageView
//    private lateinit var title: TextView

//    private lateinit var adapter: ProductsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.products, container, false)
    }

    private fun findViews(view: View) {
        recyclerView = view.findViewById(R.id.videoViews)

//        imageView = view.findViewById(R.id.productImage)
//        videoView = view.findViewById(R.id.videoView)
//        title = view.findViewById(R.id.productTitle)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)
        val imageUrl = "https://upload.wikimedia.org/wikipedia/commons/9/91/F-15_vertical_deploy.jpg"
        val path =
                "https://firebasestorage.googleapis.com/v0/b/jalwa-8f0c1.appspot.com/o/VIDEO-2020-11-22-12-44-07.mp4?alt=media&token=4bd94395-f754-4bc9-85ad-ac4a043db286";

        viewModel.productsObservable.observe(viewLifecycleOwner, { result ->
            recyclerView.apply {
                adapter = ProductsRecyclerViewAdapter(
                    activity as Activity,
                    viewModel.list as ArrayList<ProductsQuery.Product>
                )
            }
            val snapHelper: SnapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(recyclerView)
        })
    }

}

