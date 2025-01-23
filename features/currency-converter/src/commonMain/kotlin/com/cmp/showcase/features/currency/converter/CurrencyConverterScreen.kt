package com.cmp.showcase.features.currency.converter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CurrencyConverterScreen(modifier: Modifier = Modifier, viewModel: CurrencyConverterViewModel = koinViewModel()){
    Surface (modifier = Modifier.fillMaxSize().clickable(enabled = false){}){
        Box(contentAlignment = Alignment.BottomCenter) {
            val state by viewModel.state.collectAsStateWithLifecycle()
            when (val s = state) {
                is CurrencyUiState.Loading -> {
                    CircularProgressIndicator(Modifier.size(100.dp))
                }

                is CurrencyUiState.Error -> {
                    Text(
                        s.errorMessage,
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                        modifier = Modifier,
                        color = MaterialTheme.colors.error
                    )
                }

                is CurrencyUiState.SupportedCurrencies -> {
                    Text(
                        "Success",
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                        modifier = Modifier,
                        color = MaterialTheme.colors.primary
                    )
                }

                is CurrencyUiState.ConversionAmount -> {
                    Text(
                        "${s.amount}",
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                        modifier = Modifier,
                        color = MaterialTheme.colors.primary
                    )
                }

                is CurrencyUiState.Idle -> {

                    Column(modifier = Modifier.fillMaxWidth()) {
                        val list = mutableListOf<Item>()
                        var i = '1'
                        repeat(9) {
                            list.add(Item(i++))
                        }
                        list.add(Item('0'))
                        list.add(Item('.'))
                        list.add(Item('C'))
                        InputButtons(list, onClick = {
                            viewModel.convert("USD","INR", "$it".toDouble())
                        })
                    }
                }
            }
        }

    }
}

data class Item(val title: Char)

@Preview
@Composable
fun InputButtons(items: List<Item>, modifier: Modifier = Modifier, onClick: (Char) -> Unit){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // Set the number of columns to 3
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items.size) { index ->
            val item = items[index]
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = ButtonDefaults.elevation(4.dp),
                onClick = {
                    onClick(item.title)
                }
            ) {
                Text(
                    text = item.title.toString(),
                    style = MaterialTheme.typography.h3,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}