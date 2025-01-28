package com.cmp.showcase

import ProfileScreen
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.cmp.showcase.features.currency.converter.CurrencyConverterRoutes
import com.cmp.showcase.features.currency.converter.CurrencyConverterScreen
import com.cmp.showcase.features.currency.converter.CurrencyConverterViewModel
import com.cmp.showcase.features.currency.converter.SelectCurrencyScreen
import com.cmp.showcase.features.profile.ProfileScreenRoutes
import com.cmp.showcase.ui.core.AboutScreen
import com.cmp.showcase.ui.core.BottomNavigation
import com.cmp.showcase.ui.core.HomeScreen
import com.cmp.showcase.ui.core.HomeScreenRoutes
import com.cpm.showcase.features.projects.ProjectsScreen
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
            startDestination = AppRoutes.Graph
        ) {
            navigation<AppRoutes.Graph>(
                startDestination = AppRoutes.Home
            ) {
                composable<AppRoutes.Home> {
                    val homeNavController = rememberNavController()
                    HomeScreen(onBottomNavClick = {
                        when (it) {
                            BottomNavigation.Home -> homeNavController.navigate(HomeScreenRoutes.Projects)
                            BottomNavigation.About -> homeNavController.navigate(HomeScreenRoutes.About)
                        }
                    }, container = {
                        NavHost(
                            navController = homeNavController,
                            startDestination = HomeScreenRoutes.Graph
                        ) {
                            navigation<HomeScreenRoutes.Graph>(
                                startDestination = HomeScreenRoutes.Projects
                            ) {
                                composable<HomeScreenRoutes.Projects> {
                                    ProjectsScreen(onClick = {
                                        navController.navigate(CurrencyConverterRoutes.Graph)
                                    })
                                }
                                composable<HomeScreenRoutes.About> {
                                    AboutScreen(onAboutDeveloperClick = {
                                        navController.navigate(ProfileScreenRoutes.Graph)
                                    })
                                }
                            }
                        }
                    })
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

            navigation<ProfileScreenRoutes.Graph>(
                startDestination = ProfileScreenRoutes.Home
            ) {
                composable<ProfileScreenRoutes.Home> {
                    ProfileScreen(onBackClick = {
                        navController.popBackStack()
                    })
                }
            }
        }
    }
}
