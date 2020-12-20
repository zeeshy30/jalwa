package com.example.jalwa.ui.base

import com.example.jalwa.ui.main.viewmodel.LoginSignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val LoginSignupModelModule = module {
    viewModel { LoginSignupViewModel() }
}