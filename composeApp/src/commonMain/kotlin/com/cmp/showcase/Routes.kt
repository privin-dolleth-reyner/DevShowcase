package com.cmp.showcase

import com.cmp.showcase.ui.core.Routes
import kotlinx.serialization.Serializable

sealed interface AppRoutes {
    @Serializable
    data object Graph: Routes
    @Serializable
    data object Home: Routes
    @Serializable
    data object Currency: Routes
}