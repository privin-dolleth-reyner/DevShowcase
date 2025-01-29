package com.cmp.showcase.features.profile

import com.cmp.showcase.ui.core.Routes
import kotlinx.serialization.Serializable

sealed interface ProfileScreenRoutes: Routes {
    @Serializable
    data object Graph: ProfileScreenRoutes
    @Serializable
    data object Home: ProfileScreenRoutes
}