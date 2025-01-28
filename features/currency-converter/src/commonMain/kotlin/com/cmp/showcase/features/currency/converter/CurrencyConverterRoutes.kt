package com.cmp.showcase.features.currency.converter

import com.cmp.showcase.ui.core.Routes
import kotlinx.serialization.Serializable

sealed interface CurrencyConverterRoutes: Routes {
    @Serializable
    data object Graph: CurrencyConverterRoutes
    @Serializable
    data object Home: CurrencyConverterRoutes
    @Serializable
    data class SelectCurrency(val isBaseCurrency: Boolean): CurrencyConverterRoutes
}