package com.cmp.showcase.data.currency.converter.di

import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDbModule: Module
    get() = module {
        single {
            AndroidDataBaseFactory(get())
        }
    }