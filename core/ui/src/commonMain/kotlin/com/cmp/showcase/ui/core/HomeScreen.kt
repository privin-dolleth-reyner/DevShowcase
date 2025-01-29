package com.cmp.showcase.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import composeshowcase.core.ui.generated.resources.Res
import composeshowcase.core.ui.generated.resources.switch_theme_icon
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen(
    container: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onBottomNavClick: (nav: BottomNavigation) -> Unit,
    shouldShowAbout: Boolean = false,
    onToggleTheme: () -> Unit
) {
    val selectedNav =
        rememberSaveable { mutableStateOf(if (shouldShowAbout) BottomNavigation.About else BottomNavigation.Home) }

    Scaffold(
        modifier = modifier.statusBarsPadding(),
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
                    ElevatedNavbar(
                        onNavClick = {
                            when (it) {
                                BottomNavigation.Home -> onBottomNavClick(BottomNavigation.Home)
                                BottomNavigation.About -> onBottomNavClick(BottomNavigation.About)
                            }
                        },
                        selectedNav = selectedNav
                    )
                }
            }
        }) {
        Column {
            Title(onToggleTheme = onToggleTheme)
            container()
        }
    }
}


@Composable
fun ElevatedNavbar(
    modifier: Modifier = Modifier,
    onNavClick: (nav: BottomNavigation) -> Unit,
    selectedNav: MutableState<BottomNavigation>
) {
    val navList by remember {
        mutableStateOf(
            arrayListOf(
                BottomNavigation.Home,
                BottomNavigation.About
            )
        )
    }
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
                    it == selectedNav.value,
                    onClick = { nav: BottomNavigation ->
                        selectedNav.value = nav
                        onNavClick(selectedNav.value)
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


@Composable
private fun Title(modifier: Modifier = Modifier, onToggleTheme: () -> Unit) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth().height(80.dp)
                .background(MaterialTheme.colors.surface),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Compose Showcase",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(start = 16.dp)
            )

            IconButton(
                onClick = onToggleTheme
            ) {
                Icon(
                    painter = painterResource(Res.drawable.switch_theme_icon),
                    contentDescription = null
                )
            }

        }
        Divider(Modifier.fillMaxWidth(), color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
    }

}