package com.cmp.showcase.data.currency.converter.di

import com.cmp.showcase.data.currency.converter.local.DatabaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDbModule: Module
    get() = module {
        single<DatabaseFactory> {
            AndroidDataBaseFactory(get())
        }
    }