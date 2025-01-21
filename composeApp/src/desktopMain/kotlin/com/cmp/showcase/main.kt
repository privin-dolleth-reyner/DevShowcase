package com.cmp.showcase

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.cmp.showcase.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Compose Showcase",
        ) {
            App()
        }
    }
}