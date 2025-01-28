package com.cmp.showcase.ui.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun AboutScreen(onAboutDeveloperClick: ()-> Unit, modifier: Modifier = Modifier){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "About the App",
            style = MaterialTheme.typography.h4
        )
        Text(
            text = "This app is built using Compose Multiplatform, a modern UI toolkit for building native user interfaces across Android and iOS platforms using a single Kotlin codebase. Designed with scalability and performance in mind, the app offers a seamless and responsive user experience across all supported devices.It follows a modular architecture, designed like a super app, and serves as a portfolio showcasing all the apps developed by me.",
            style = MaterialTheme.typography.body1,
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onAboutDeveloperClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ){
            Text(text = "About Developer")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tech Stack",
            style = MaterialTheme.typography.h4
        )

        val techStack = listOf(
            "Dependency Injection (Koin): To manage dependencies efficiently",
            "Networking: Powered by Ktor for efficient API communication",
            "Persistence: Utilizes SQLDelight for shared database management",
            "Navigation: Compose navigation for seamless UI transitions",
            "Offline First Approach: Ensures data is always up-to-date",
            "Firebase firestore: For real-time database",
            "ViewModel: For state management",
            "Kotlin Coroutines: For asynchronous operations",
            "MVVM Architecture: Organized for modularity and maintainability",
            "Kotlin DSL: For better code readability",
        )

        techStack.forEach { tech ->
            Text(
                text = "• $tech",
                style = MaterialTheme.typography.body1
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Repository",
            style = MaterialTheme.typography.h4
        )

        BasicText(
            modifier = Modifier.clickable {
                //
            },
            text = AnnotatedString("GitHub Repository Link"),
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.primary,
                textDecoration = TextDecoration.Underline
            )
        )

        Spacer(modifier = Modifier.height(200.dp))
    }
}