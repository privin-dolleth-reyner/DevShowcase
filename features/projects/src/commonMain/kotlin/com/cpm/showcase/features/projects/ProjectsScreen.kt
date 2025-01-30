package com.cpm.showcase.features.projects

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cmp.showcase.ui.core.BottomNavigation
import com.cmp.showcase.ui.core.HomeScreenViewModel
import devshowcase.features.projects.generated.resources.Res
import devshowcase.features.projects.generated.resources.projects_currency_converter
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProjectsScreen(onClick: (Int) -> Unit, viewModel: HomeScreenViewModel) {
    viewModel.setSelectedNav(BottomNavigation.Home)
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Card(
                modifier = Modifier.fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                contentColor = MaterialTheme.colors.onSurface,
                backgroundColor = MaterialTheme.colors.primarySurface
            ) {
                Button(
                    onClick = {
                        onClick(1)
                    }
                ) {
                    Text(
                        stringResource(Res.string.projects_currency_converter),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}