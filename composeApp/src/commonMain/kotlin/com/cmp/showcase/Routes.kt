package com.cmp.showcase

import com.cmp.showcase.ui.core.Routes
import kotlinx.serialization.Serializable

sealed interface AppRoutes: Routes {
    @Serializable
    data object Graph: AppRoutes
    @Serializable
    data class Home(val showAbout: Boolean = false): AppRoutes
}