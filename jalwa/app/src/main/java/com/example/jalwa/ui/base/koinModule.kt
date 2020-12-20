package com.example.jalwa.ui.base

import org.koin.core.module.Module

fun getListOfModules(): List<Module> {

    return (listOf(
        productViewModelModule,
        LoginSignupModelModule
    ))
}