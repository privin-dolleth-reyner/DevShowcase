package com.cmp.showcase.features.currency.converter.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        viewModelOf(::CurrencyConverterViewModel)
    }