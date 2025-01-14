package com.cmp.showcase

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composeshowcase.composeapp.generated.resources.Res
import composeshowcase.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
expect fun MyTheme(darkTheme: Boolean, content: @Composable() () -> Unit)

@Composable
@Preview
fun App() {
    MyTheme(isSystemInDarkTheme()) {
        var showContent by remember { mutableStateOf(false) }
        Scaffold (
            topBar = {
                TopAppBar(
                    title = {
                        Text("Showcase App", style = MaterialTheme.typography.h3)
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        showContent = !showContent
                    },
                    content = {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }
        ) { paddingValues ->
            Column(Modifier.fillMaxWidth().padding(paddingValues), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { showContent = !showContent }) {
                    Text("Click me!")
                }
                AnimatedVisibility(showContent) {
                    val greeting = remember { Greeting().greet() }
                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painterResource(Res.drawable.compose_multiplatform), null)
                        Text("Compose: $greeting")
                    }
                }
            }
        }
    }
}