package com.cmp.showcase.features.currency.converter.di

import org.koin.core.module.Module
import org.koin.dsl.module
import com.cmp.showcase.features.currency.converter.CurrencyConverterViewModel
import org.koin.core.module.dsl.viewModelOf

actual val platformModule: Module
    get() = module {
        viewModelOf(::CurrencyConverterViewModel)
    }