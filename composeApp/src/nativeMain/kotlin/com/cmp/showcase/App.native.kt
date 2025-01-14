package com.cmp.showcase

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.compose.darkScheme
import com.example.compose.lightScheme
import com.example.ui.theme.baseline

@Composable
actual fun MyTheme(darkTheme: Boolean, content: @Composable() () -> Unit) {
    val colorScheme = when {
        darkTheme -> darkScheme
        else -> lightScheme
    }

    MaterialTheme(
        colors = colorScheme,
        typography = baseline,
        content = content
    )
}