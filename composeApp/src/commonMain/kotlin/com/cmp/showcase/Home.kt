package com.cmp.showcase

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.cpm.showcase.features.projects.ProjectsScreen

@Composable
fun HomeScreen(navigationController: NavController, modifier: Modifier = Modifier){
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Showcase App", fontSize = 30.sp, modifier = Modifier.fillMaxSize().padding(8.dp), textAlign = TextAlign.Start)
            },
                modifier = Modifier.height(55.dp).statusBarsPadding())
        },
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            BottomNavigation(
                Modifier.offset(y = (-50).dp),
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            ) {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterVertically)
                ) {
                    ElevatedNavbar(onNavClick = {
                        when (it) {
                            BottomNavigation.Home -> navController.navigate(HomeScreenRoutes.Projects)
                            BottomNavigation.About -> navController.navigate(HomeScreenRoutes.About)
                        }
                    })
                }
            }
        }) {
        Column {
            NavHost(
                navController = navController,
                startDestination = HomeScreenRoutes.Graph
            ) {
                navigation<HomeScreenRoutes.Graph>(
                    startDestination = HomeScreenRoutes.Projects
                ) {
                    composable<HomeScreenRoutes.Projects> {
                        ProjectsScreen(onClick = {
                            navigationController.navigate(CurrencyConverterRoutes.Graph)
                        })
                    }
                    composable<HomeScreenRoutes.About> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text("About screen ", color = MaterialTheme.colors.onSurface)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ElevatedNavbar(modifier: Modifier = Modifier, onNavClick: (nav: BottomNavigation) -> Unit) {
    val navList by remember {
        mutableStateOf(
            arrayListOf(
                BottomNavigation.Home,
                BottomNavigation.About
            )
        )
    }
    var selectedNav by remember { mutableStateOf(BottomNavigation.Home) }
    Surface(
        modifier.padding(4.dp),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colors.surface,
        elevation = 2.dp
    ) {
        Row {
            navList.forEach {
                NavBarItem(
                    it,
                    it == selectedNav,
                    onClick = { nav: BottomNavigation ->
                        selectedNav = nav
                        onNavClick(selectedNav)
                    })
            }
        }
    }
}

@Composable
fun NavBarItem(
    nav: BottomNavigation,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: (nav: BottomNavigation) -> Unit
) {
    Surface(
        modifier.clickable { onClick(nav) },
        shape = RoundedCornerShape(24.dp),
        color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    ) {
        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = nav.icon,
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
            )
            Spacer(Modifier.padding(2.dp))
            Text(text = nav.title, style = MaterialTheme.typography.caption)
        }
    }
}

