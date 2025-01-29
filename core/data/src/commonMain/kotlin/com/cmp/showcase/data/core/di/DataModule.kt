package com.cmp.showcase.data.core.di

import com.cmp.showcase.data.core.AppSettings
import com.cmp.showcase.data.core.AppSettingsImpl
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

expect fun platformModule(): Module

val dataModule = module {
    single {
        AppSettingsImpl(get())
    }.bind<AppSettings>()
}

fun KoinApplication.coreDataModule() = arrayOf(platformModule(), dataModule)