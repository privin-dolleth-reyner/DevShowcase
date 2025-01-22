package com.cmp.showcase.features.currency.converter.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.cmp.showcase.features.currency.converter.CurrencyConverterViewModel

actual val platformModule: Module
    get() = module {
        singleOf(::CurrencyConverterViewModel)
    }