package com.cmp.showcase

import android.app.Activity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.cmp.showcase.ui.core.theme.baseline
import com.cmp.showcase.ui.core.theme.darkScheme
import com.cmp.showcase.ui.core.theme.lightScheme

@Composable
actual fun MyTheme(darkTheme: Boolean, content: @Composable() () -> Unit) {
    val colorScheme = when {
        darkTheme -> darkScheme
        else -> lightScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
    MaterialTheme(
        colors = colorScheme,
        typography = baseline,
        content = content
    )
}