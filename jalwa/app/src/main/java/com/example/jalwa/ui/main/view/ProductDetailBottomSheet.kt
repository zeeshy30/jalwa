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
import com.example.jalwa.ui.main.viewmodel.ProductDetailViewModel
import com.example.jalwa.utils.PlayerViewAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.product_detail_bottom_sheet.*
import org.koin.android.ext.android.inject


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
    private val viewModel: ProductDetailViewModel by inject()

    private var variantQuantity = 0
    private var variantType1: String = ""
    private var variantType2: String = ""
    private val variant1Set = mutableSetOf<String>()
    private val variant2Set = mutableSetOf<String>()

    private lateinit var binding: ProductDetailBottomSheetBinding
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

    private fun getProductSKUs() {
        arguments?.getString("handle")?.let { viewModel.getProductDetails(it) }
        viewModel.productSKUsObservable.observe(viewLifecycleOwner, {
            if(viewModel.isError.value != true) {
                it.value.productSKUs?.forEach { productSKU ->
                    if (productSKU.variant1 != "" && productSKU.variant1 != null) {
                        variantType1 = productSKU.variantType1
                        variant1Set.add(productSKU.variant1)
                    }
                    if (productSKU.variant2 != "" && productSKU.variant2 != null) {
                        variantType2 = productSKU.variantType2!!
                        variant2Set.add(productSKU.variant2)
                    }
                }
                binding.apply {
                    variant1 = variantType1
                    variant2 = variantType2
                }
                if (variant1Set.isEmpty()) {
                    options1.visibility = View.GONE
                    option1.visibility = View.GONE
                } else {
                    options1.apply {
                        adapter = OptionsRecyclerViewAdapter(variant1Set)
                    }
                }
                if (variant2Set.isEmpty()) {
                    options2.visibility = View.GONE
                    option2.visibility = View.GONE
                } else {
                    options2.apply {
                        adapter = OptionsRecyclerViewAdapter(variant2Set)
                    }
                }
            }
        })
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
        getProductSKUs()
    }
}