package com.cmp.showcase

import androidx.compose.ui.window.ComposeUIViewController
import com.cmp.showcase.di.initKoin
import org.koin.dsl.bind
import org.koin.dsl.module

fun MainViewController() = ComposeUIViewController(configure = {
    initKoin {
        koin.loadModules(
            modules = listOf(
                module {
                    single {
                        IOSPlatformHandler()
                    }.bind<PlatformHandler>()
                }
            )
        )
    }
}) { App() }