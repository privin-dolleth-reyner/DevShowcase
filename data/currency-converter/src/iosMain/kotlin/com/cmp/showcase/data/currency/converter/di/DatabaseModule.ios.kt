package com.cmp.showcase.data.currency.converter.di

import com.cmp.showcase.data.currency.converter.local.DatabaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module

val nativeDbModule = module {
    single<DatabaseFactory> {
        IosDatabaseFactory()
    }
}

actual val platformDbModule: Module
    get() = nativeDbModule