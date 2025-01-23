package com.cmp.showcase.features.currency.converter.di

import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.cmp.showcase.features.currency.converter.CurrencyConverterViewModel

val featureModule = module {
    viewModelOf(::CurrencyConverterViewModel)
}

expect val platformModule: Module

fun KoinApplication.currencyFeatureModule() = arrayOf(featureModule, platformModule)