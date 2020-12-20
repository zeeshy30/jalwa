package com.example.jalwa.ui.main.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import com.example.jalwa.R
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.ui.main.adapter.OptionsRecyclerViewAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso


class AutoFillRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    private var manager: GridLayoutManager? = null
    private var columnWidth = -1

    init {
        if (attrs != null) {
            val attrsArray = intArrayOf(
                android.R.attr.columnWidth
            )
            val array = context.obtainStyledAttributes(attrs, attrsArray)
            columnWidth = array.getDimensionPixelSize(0, -1)
            array.recycle()
        }
        manager = GridLayoutManager(context, 1)
        layoutManager = manager
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        if (columnWidth > 0) {
            val spanCount = 1.coerceAtLeast(measuredWidth / columnWidth)
            manager!!.spanCount = spanCount
        }
    }
}

class ProductDetailBottomSheet: BottomSheetDialogFragment() {
    companion object {
        const val TAG = "ProductDetailBottomSheet"
    }
//    private val title = arguments?.getString("title")
//    private val price =
    private lateinit var title: TextView
    private lateinit var price: TextView
    private lateinit var productImage: ImageView
    private lateinit var options1: AutoFillRecyclerView
    private lateinit var options2: AutoFillRecyclerView
    private lateinit var buyNow: AppCompatButton
    private lateinit var addToCart: AppCompatButton

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_detail_bottom_sheet, container, false)
    }

    private fun findViews(view: View) {
        options1 = view.findViewById(R.id.options1)
        options2 = view.findViewById(R.id.options2)
        addToCart = view.findViewById(R.id.addToCart)
        buyNow = view.findViewById(R.id.buyNow)
        productImage = view.findViewById(R.id.productImage)
        title = view.findViewById(R.id.productTitle)
        price = view.findViewById(R.id.productPrice)
    }

    private fun clickListener() {
        buyNow.setOnClickListener {
            this.dismiss()
            findNavController().navigate(R.id.action_login_signup_page, Bundle())
        }
        addToCart.setOnClickListener {
            this.dismiss()
            findNavController().navigate(R.id.action_login_signup_page, Bundle())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)
        clickListener()

        title.text = arguments?.getString("title")
        val priceText = arguments?.getString("price")
        price.text = "Rs. ${priceText}"
        Picasso.with(context).load(arguments?.getString("photoUrl"))
                .resize(50, 70)
                .into(productImage)
        val list = ArrayList<String>()
        list.add("XS")
        list.add("Small")
        list.add("Medium")
        list.add("Large")

        options1.apply {
            adapter = OptionsRecyclerViewAdapter(
                activity as Activity,
                list
            )
        }

        val list2 = ArrayList<String>()
        list2.add("Red")
        list2.add("Black")
        list2.add("Green")
        list2.add("Yellow")
        list2.add("Brown")
        list2.add("Orange")
        list2.add("White")

        options2.apply {
            adapter = OptionsRecyclerViewAdapter(
                activity as Activity,
                list2
            )
        }
    }
}