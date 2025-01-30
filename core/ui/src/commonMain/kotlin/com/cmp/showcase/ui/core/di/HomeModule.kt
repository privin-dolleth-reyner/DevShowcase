package com.cmp.showcase.ui.core.di

import com.cmp.showcase.ui.core.HomeScreenViewModel
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeScreenViewModel()
    }
}

fun KoinApplication.uiModule() = arrayOf(homeModule)