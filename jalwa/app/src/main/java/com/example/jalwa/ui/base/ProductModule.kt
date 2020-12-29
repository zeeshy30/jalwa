package com.example.jalwa.ui.base

import com.example.jalwa.ui.main.viewmodel.ProductDetailViewModel
import com.example.jalwa.ui.main.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val productViewModelModule = module {
    viewModel { ProductViewModel() }
    viewModel { ProductDetailViewModel() }
}