package com.cmp.showcase

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.cpm.showcase.features.projects.ProjectsScreen
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
expect fun MyTheme(darkTheme: Boolean, content: @Composable() () -> Unit)

@Composable
@Preview
fun App() {
    MyTheme(isSystemInDarkTheme()) {
        val navController = rememberNavController()
        Scaffold(
            backgroundColor = MaterialTheme.colors.background,
            bottomBar = {
            BottomNavigation(Modifier.offset(y = (-50).dp), backgroundColor = Color.Transparent, elevation = 0.dp){
                Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()){
                    ElevatedNavbar(onNavClick = {
                        when(it){
                            BottomNavigation.Home -> navController.navigate(Routes.Home)
                            BottomNavigation.About -> navController.navigate(Routes.About)
                        }
                    })
                }
            }
        }){
            Text(text = "Showcase App", fontSize = 30.sp, modifier = Modifier.padding(16.dp))
            NavHost(
                navController = navController,
                startDestination = Routes.Graph
            ){
                navigation<Routes.Graph>(
                    startDestination = Routes.Home
                ){
                    composable<Routes.Home>{
                        ProjectsScreen()
                    }
                    composable<Routes.About> {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Text("About screen ")
                        }
                    }
                    composable<Routes.ProjectDetail> { data ->
                        val args = data.toRoute<Routes.ProjectDetail>()
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Text("Projects Detail screen ")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ElevatedNavbar(modifier: Modifier = Modifier, onNavClick: (nav: BottomNavigation) -> Unit ){
    val navList by remember { mutableStateOf(arrayListOf(BottomNavigation.Home, BottomNavigation.About)) }
    var selectedNav by remember { mutableStateOf(BottomNavigation.Home) }
    Surface(modifier.padding(4.dp), shape = RoundedCornerShape(24.dp), color = MaterialTheme.colors.surface, elevation = 2.dp) {
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
fun NavBarItem(nav: BottomNavigation, isSelected: Boolean, modifier: Modifier = Modifier, onClick: (nav: BottomNavigation) -> Unit ){
    Surface(modifier.clickable { onClick(nav) }, shape = RoundedCornerShape(24.dp), color = if(isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface) {
        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = nav.icon, contentDescription = null, tint = if(isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface)
            Spacer(Modifier.padding(2.dp))
            Text(text = nav.title, style = MaterialTheme.typography.caption)
        }
    }
}

