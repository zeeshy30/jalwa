package com.example.jalwa.ui.main.view

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jalwa.R
import com.example.jalwa.databinding.ProductDetailBottomSheetBinding
import com.example.jalwa.ui.main.adapter.OptionsRecyclerViewAdapter
import com.example.jalwa.utils.PlayerViewAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.product_detail_bottom_sheet.*


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

    private lateinit var binding: ProductDetailBottomSheetBinding
    private var variantQuantity = 0
    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.product_detail_bottom_sheet,
            container,
            false
        )
        return binding.root
    }

    fun buyNow() {
        this.dismiss()
        findNavController().navigate(R.id.action_login_signup_page, Bundle())
    }

    fun addToCart() {
        this.dismiss()
        findNavController().navigate(R.id.action_login_signup_page, Bundle())
    }

    fun increaseQuantity() {
        variantQuantity += 1
        binding.buyQuantity = variantQuantity.toString()
    }

    fun decreaseQuantity() {
        if (variantQuantity > 0) {
            variantQuantity -= 1
            binding.buyQuantity = variantQuantity.toString()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        PlayerViewAdapter.playCurrentPlayingVideo()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val priceText = arguments?.getString("price")
        binding.apply {
            title = arguments?.getString("title")
            price = "Rs. $priceText"
            buyQuantity = variantQuantity.toString()
            photoUrl = arguments?.getString("photoUrl")
            body = arguments?.getString("body")
            productDetailBottomSheetCallBacks = this@ProductDetailBottomSheet
            executePendingBindings()
        }

        val list1 = ArrayList<String>()
        list1.add("XS")
        list1.add("Small")
        list1.add("Medium")
        list1.add("Large")

        if (list1.isEmpty()) {
            options2.visibility = View.GONE
            option2.visibility = View.GONE
        } else {
            options1.apply {
                adapter = OptionsRecyclerViewAdapter(list1)
            }
        }

        val list2 = ArrayList<String>()
        list2.add("Red")
        list2.add("Black")
        list2.add("Green")
        list2.add("Yellow")
        list2.add("Brown")
        list2.add("Orange")
        list2.add("White")
        if (list2.isEmpty()) {
            options2.visibility = View.GONE
            option2.visibility = View.GONE
        } else {
            options2.apply {
                adapter = OptionsRecyclerViewAdapter(list2)
            }
        }
    }
}