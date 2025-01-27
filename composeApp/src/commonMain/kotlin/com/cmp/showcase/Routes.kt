package com.cmp.showcase

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object Graph: Routes
    @Serializable
    data object Home: Routes
    @Serializable
    data object Currency: Routes
}

sealed interface HomeScreenRoutes {
    @Serializable
    data object Graph: Routes
    @Serializable
    data object Projects: Routes
    @Serializable
    data object About: Routes
    @Serializable
    data class ProjectDetail(val id: String): Routes
}

sealed interface CurrencyConverterRoutes: Routes {
    @Serializable
    data object Graph: CurrencyConverterRoutes
    @Serializable
    data object Home: CurrencyConverterRoutes
    @Serializable
    data class SelectCurrency(val isBaseCurrency: Boolean): CurrencyConverterRoutes
}

enum class BottomNavigation(val title: String, val icon: ImageVector) {
    Home("Home", Icons.Default.Home),
    About("About", Icons.Default.AccountCircle)
}