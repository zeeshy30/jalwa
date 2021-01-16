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
import com.example.jalwa.GetProductSKUsQuery
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

    private var variantQuantity = 1
    private var variantType1: String = ""
    private var variantType2: String = ""
    private var variant1Selected = ""
    private var variant2Selected = ""
    private val variant1Set = mutableSetOf<String>()
    private val variant2Set = mutableSetOf<String>()
    private val variant1SetFiltered = mutableSetOf<String>()
    private val variant2SetFiltered = mutableSetOf<String>()

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
        PlayerViewAdapter.releaseAllPlayers()
        findNavController().navigate(R.id.action_login_signup_page, Bundle())
    }

    fun addToCart() {
        this.dismiss()
        PlayerViewAdapter.releaseAllPlayers()
        findNavController().navigate(R.id.action_login_signup_page, Bundle())
    }

    fun increaseQuantity() {
        if (viewModel.loading.value!!) {
            return
        }
        var max = 1
        if (variantType1 != "" && variantType2 != "") {
            viewModel.productSKUs.forEach {
                val productSKU = (it as GetProductSKUsQuery.ProductSKU)
                if (productSKU.variant1 == variant1Selected && productSKU.variant2 == variant2Selected)
                    max = productSKU.quantity.toInt()
            }
        } else if (variantType1 != "") {
            viewModel.productSKUs.forEach {
                val productSKU = (it as GetProductSKUsQuery.ProductSKU)
                if (productSKU.variant1 == variant1Selected)
                    max = productSKU.quantity.toInt()
            }
        } else {
            if (viewModel.productSKUs.isNotEmpty()) {
                max = (viewModel.productSKUs[0] as GetProductSKUsQuery.ProductSKU).quantity.toInt()
            }
        }
        if (variantQuantity < max ) {
            minus.alpha = 1f
            variantQuantity += 1
            binding.buyQuantity = variantQuantity.toString()
            if (variantQuantity == max) {
                plus.alpha = 0.5f
            }
        }
    }

    fun decreaseQuantity() {
        if (viewModel.loading.value!!) {
            return
        }
        if (variantQuantity > 1) {
            plus.alpha = 1f
            variantQuantity -= 1
            binding.buyQuantity = variantQuantity.toString()
            if (variantQuantity == 1) {
                minus.alpha = 0.5f
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        PlayerViewAdapter.playCurrentPlayingVideo()
    }

    private fun selectVariant1(variant: String) {
        variant1Selected = variant
        variant2SetFiltered.clear()
        viewModel.productSKUs.forEach {
            val productSKU = it as GetProductSKUsQuery.ProductSKU
            if (variant1Selected == productSKU.variant1) {
                productSKU.variant2?.let { it1 -> variant2SetFiltered.add(it1) }
            }
            if (variantType2 == "" || variant2Selected != "") {
                plus.alpha = 1f
            }
        }
        variantQuantity = 1
        minus.alpha = 0.5f
        binding.buyQuantity = variantQuantity.toString()
        options1.adapter?.notifyDataSetChanged()
        options2.adapter?.notifyDataSetChanged()
    }

    private fun selectVariant2(variant: String) {
        variant2Selected = variant
        variant1SetFiltered.clear()
        viewModel.productSKUs.forEach {
            val productSKU = it as GetProductSKUsQuery.ProductSKU
            if (variant2Selected == productSKU.variant2) {
                productSKU.variant1?.let { it1 -> variant1SetFiltered.add(it1) }
            }
            if (variant1Selected != "") {
                plus.alpha = 1f
            }
        }
        variantQuantity = 1
        minus.alpha = 0.5f
        binding.buyQuantity = variantQuantity.toString()
        options2.adapter?.notifyDataSetChanged()
        options1.adapter?.notifyDataSetChanged()
    }

    private fun getProductSKUs() {
        arguments?.getString("handle")?.let { viewModel.getProductDetails(it) }
        viewModel.productSKUsObservable.observe(viewLifecycleOwner, {
            if(viewModel.isError.value != true) {
                it.value.forEach { productSKU ->
                    if (productSKU?.variant1 != "" && productSKU?.variant1 != null) {
                        variantType1 = productSKU.variantType1!!
                        variant1Set.add(productSKU.variant1)
                        plus.alpha = 0.5f
                    }
                    if (productSKU?.variant2 != "" && productSKU?.variant2 != null) {
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
                        adapter = OptionsRecyclerViewAdapter(variant1Set, ::selectVariant1, variant1SetFiltered)
                    }
                }
                if (variant2Set.isEmpty()) {
                    options2.visibility = View.GONE
                    option2.visibility = View.GONE
                } else {
                    options2.apply {
                        adapter = OptionsRecyclerViewAdapter(variant2Set, ::selectVariant2, variant2SetFiltered)
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
        minus.alpha = 0.5f
        getProductSKUs()
    }
}