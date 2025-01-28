package com.cmp.showcase.ui.core

import kotlinx.serialization.Serializable

sealed interface HomeScreenRoutes: Routes {
    @Serializable
    data object Graph: HomeScreenRoutes
    @Serializable
    data object Projects: HomeScreenRoutes
    @Serializable
    data object About: HomeScreenRoutes
    @Serializable
    data class ProjectDetail(val id: String): HomeScreenRoutes
}