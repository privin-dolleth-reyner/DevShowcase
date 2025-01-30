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
import devshowcase.core.ui.generated.resources.Res
import devshowcase.core.ui.generated.resources.about_app
import devshowcase.core.ui.generated.resources.about_developer
import devshowcase.core.ui.generated.resources.about_git_repository
import devshowcase.core.ui.generated.resources.about_repository
import devshowcase.core.ui.generated.resources.about_summary
import devshowcase.core.ui.generated.resources.about_tech
import devshowcase.core.ui.generated.resources.about_tech_stack
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AboutScreen(onAboutDeveloperClick: ()-> Unit, modifier: Modifier = Modifier, onUrlClick: (String) -> Unit, viewModel: HomeScreenViewModel){
    val scrollState = rememberScrollState()
    viewModel.setSelectedNav(BottomNavigation.About)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(Res.string.about_app),
            style = MaterialTheme.typography.h4
        )
        Text(
            text = stringResource(Res.string.about_summary),
            style = MaterialTheme.typography.body1,
        )

        OutlinedButton(
            onClick = onAboutDeveloperClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ){
            Text(text = stringResource(Res.string.about_developer))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.about_tech),
            style = MaterialTheme.typography.h4
        )

        val techStack = stringArrayResource(Res.array.about_tech_stack)

        techStack.forEach { tech ->
            Text(
                text = tech,
                style = MaterialTheme.typography.body1
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.about_repository),
            style = MaterialTheme.typography.h4
        )

        BasicText(
            modifier = Modifier.clickable {
                onUrlClick("https://github.com/privin-dolleth-reyner/DevShowcase")
            },
            text = AnnotatedString(stringResource(Res.string.about_git_repository)),
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.primary,
                textDecoration = TextDecoration.Underline
            )
        )

        Spacer(modifier = Modifier.height(200.dp))
    }
}