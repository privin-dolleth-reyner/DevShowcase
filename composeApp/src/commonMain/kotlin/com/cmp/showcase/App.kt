package com.cmp.showcase

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.cmp.showcase.features.currency.converter.CurrencyConverterScreen
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
expect fun MyTheme(darkTheme: Boolean, content: @Composable() () -> Unit)

@Composable
@Preview
fun App() {
    MyTheme(isSystemInDarkTheme()) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Routes.Graph
        ) {
            navigation<Routes.Graph>(
                startDestination = Routes.Home
            ){
                composable<Routes.Home> {
                    HomeScreen(navController)
                }
            }
            navigation<CurrencyConverterRoutes.Graph>(
                startDestination = CurrencyConverterRoutes.Home
            ){
                composable<CurrencyConverterRoutes.Home> {
                    CurrencyConverterScreen()
                }
            }
        }
    }
}
