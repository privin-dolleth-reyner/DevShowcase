package com.cmp.showcase

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.cmp.showcase.ui.core.theme.baseline
import com.cmp.showcase.ui.core.theme.darkScheme
import com.cmp.showcase.ui.core.theme.lightScheme

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