package com.cmp.showcase.features.currency.converter.di

import com.cmp.showcase.features.currency.converter.CurrencyConverterViewModel
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureModule = module {
    viewModelOf(::CurrencyConverterViewModel)
}

fun KoinApplication.currencyFeatureModule() = arrayOf(featureModule)