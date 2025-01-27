package com.cmp.showcase

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.cmp.showcase.features.currency.converter.CurrencyConverterScreen
import com.cmp.showcase.features.currency.converter.CurrencyConverterViewModel
import com.cmp.showcase.features.currency.converter.SelectCurrencyScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
expect fun MyTheme(darkTheme: Boolean, content: @Composable() () -> Unit)

@Composable
@Preview
fun App() {
    MyTheme(isSystemInDarkTheme()) {
        val navController = rememberNavController()
        val viewModel : CurrencyConverterViewModel = koinViewModel()
        NavHost(
            navController = navController,
            startDestination = Routes.Graph
        ) {
            navigation<Routes.Graph>(
                startDestination = Routes.Home
            ) {
                composable<Routes.Home> {
                    HomeScreen(navController)
                }
            }

            navigation<CurrencyConverterRoutes.Graph>(
                startDestination = CurrencyConverterRoutes.Home
            ) {

                composable<CurrencyConverterRoutes.Home> {
                    CurrencyConverterScreen(
                        viewModel = viewModel,
                        onChangeCurrency = {
                            navController.navigate(CurrencyConverterRoutes.SelectCurrency(it))
                        },
                        onBackClick = {
                            navController.navigateUp()
                        })
                }
                composable<CurrencyConverterRoutes.SelectCurrency> {
                    val args = it.arguments
                    SelectCurrencyScreen(
                        viewModel = viewModel,
                        isBaseCurrency = args?.getBoolean("isBaseCurrency") ?: false,
                        onBackClick = {
                            navController.navigateUp()
                        },
                        onSelect = {
                            navController.navigateUp()
                        })
                }
            }
        }
    }
}
