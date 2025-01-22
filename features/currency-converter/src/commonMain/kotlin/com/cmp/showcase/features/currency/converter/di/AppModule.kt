package com.cmp.showcase.features.currency.converter.di

import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.module

val featureModule = module {

}

expect val platformModule: Module

fun KoinApplication.currencyFeatureModule() = arrayOf(featureModule, platformModule)