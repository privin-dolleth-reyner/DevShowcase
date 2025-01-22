package com.cmp.showcase.features.currency.converter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CurrencyConverterScreen(modifier: Modifier = Modifier, viewModel: CurrencyConverterViewModel = koinViewModel()){
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        when(val s = state){
            is CurrencyUiState.Loading -> {
                CircularProgressIndicator(Modifier.size(100.dp))
            }
            is CurrencyUiState.Error -> {
                Text(s.errorMessage, style = MaterialTheme.typography.h5, textAlign = TextAlign.Center, modifier = Modifier, color = MaterialTheme.colors.error)
            }
            is CurrencyUiState.SupportedCurrencies ->{
                Text("Success", style = MaterialTheme.typography.h5, textAlign = TextAlign.Center, modifier = Modifier, color = MaterialTheme.colors.primary)
            }
            is CurrencyUiState.ConversionAmount -> {
                Text("${s.amount}", style = MaterialTheme.typography.h5, textAlign = TextAlign.Center, modifier = Modifier, color = MaterialTheme.colors.primary)
            }
            is CurrencyUiState.Idle -> {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Card(modifier = Modifier.fillMaxWidth().height(200.dp).padding(16.dp), shape = RoundedCornerShape(24.dp), contentColor = MaterialTheme.colors.onSurface, backgroundColor = MaterialTheme.colors.primarySurface) {
                        Button(onClick = {
                            viewModel.fetchCodes()
                        }){
                            Text("Currency converter app", style = MaterialTheme.typography.h5, textAlign = TextAlign.Center, modifier = Modifier)
                        }

                    }
                }
            }
        }
    }
}