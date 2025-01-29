package com.cmp.showcase.data.core.di

import com.cmp.showcase.data.core.createIosDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single {
        createIosDataStore()
    }
}