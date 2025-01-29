package com.cmp.showcase.ui.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavigation(val title: String, val icon: ImageVector) {
    Home("Home", Icons.Default.Home),
    About("About", Icons.Default.AccountCircle)
}