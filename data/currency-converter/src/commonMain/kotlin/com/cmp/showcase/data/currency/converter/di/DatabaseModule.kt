package com.cmp.showcase.data.currency.converter.di

import com.cmp.showcase.data.currency.converter.local.LocalDataSource
import org.koin.core.module.Module
import org.koin.dsl.module


expect val platformDbModule : Module


val dbModule = module {
    single {
        LocalDataSource(get())
    }
}
