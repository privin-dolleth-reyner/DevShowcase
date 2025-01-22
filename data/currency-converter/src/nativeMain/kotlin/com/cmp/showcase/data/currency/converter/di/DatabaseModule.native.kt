package com.cmp.showcase.data.currency.converter.di

import org.koin.core.module.Module
import org.koin.dsl.module

val nativeDbModule = module {
    single {
        NativeDatabaseFactory()
    }
}

actual val platformDbModule: Module
    get() = nativeDbModule