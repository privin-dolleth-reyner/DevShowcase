package com.cmp.showcase.di

import com.cmp.showcase.PlatformHandler
import com.cmp.showcase.getPlatformHandler
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {
        getPlatformHandler()
    }.bind<PlatformHandler>()
}