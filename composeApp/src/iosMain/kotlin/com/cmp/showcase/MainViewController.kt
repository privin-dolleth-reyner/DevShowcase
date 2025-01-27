package com.cmp.showcase

import androidx.compose.ui.window.ComposeUIViewController
import com.cmp.showcase.di.initKoin

fun MainViewController() = ComposeUIViewController(configure = {
    initKoin()
}) { App() }