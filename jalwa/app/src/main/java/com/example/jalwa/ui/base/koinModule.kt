package com.example.jalwa.ui.base

import org.koin.core.module.Module
import com.example.jalwa.ui.base.productViewModelModule

fun getListOfModules(): List<Module> {

    return (listOf(
        productViewModelModule
    ))
}