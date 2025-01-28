package com.cmp.showcase.features.profile

import kotlinx.serialization.Serializable

sealed interface ProfileScreenRoutes {
    @Serializable
    data object Graph: ProfileScreenRoutes
    @Serializable
    data object Home: ProfileScreenRoutes
}