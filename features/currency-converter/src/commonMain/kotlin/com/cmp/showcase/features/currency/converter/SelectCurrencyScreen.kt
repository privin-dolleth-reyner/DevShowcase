package com.cmp.showcase.features.currency.converter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cpm.showcase.domain.currency.converter.entity.Currency
import devshowcase.features.currency_converter.generated.resources.Res
import devshowcase.features.currency_converter.generated.resources.select_currency_loading
import devshowcase.features.currency_converter.generated.resources.select_currency_search_hint
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SelectCurrencyScreen(
    isBaseCurrency: Boolean,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onSelect: (Currency) -> Unit,
    viewModel: CurrencyConverterViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.fetchCodes()
    }
    val searchValue = remember { mutableStateOf("") }
    Surface(
        modifier = modifier.fillMaxSize()
            .statusBarsPadding()
            .clickable(
                enabled = false,
                onClick = {}
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(
                searchValue = searchValue.value,
                onBackClick = onBackClick,
                onValueChange = {
                    searchValue.value = it
                }
            )
            when (val uiState = state.currencyUiState) {
                is CurrencyUiState.Error -> {
                    ErrorView(
                        errorMessage = uiState.errorMessage,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                CurrencyUiState.Loading -> {
                    LoadingView(modifier = Modifier.fillMaxSize())
                }

                is CurrencyUiState.SupportedCurrencies -> {
                    val list =
                        (state.currencyUiState as CurrencyUiState.SupportedCurrencies).codes.filter {
                            (it.code.contains(searchValue.value, true) || it.name.contains(
                                searchValue.value,
                                true
                            ))
                        }

                    LazyColumn(
                        contentPadding = PaddingValues(8.dp),
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        items(list.size) { index ->
                            CurrencyItem(
                                item = list[index],
                                onClick = {
                                    viewModel.setCurrency(isBaseCurrency, list[index])
                                    onSelect(list[index])
                                },
                                modifier = Modifier.fillMaxWidth().padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorView(errorMessage: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = errorMessage)
    }
}

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column {
            CircularProgressIndicator()
                Text(text = stringResource(Res.string.select_currency_loading))
        }
    }
}

@Composable
fun CurrencyItem(item: Currency, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.clickable {
            onClick()
        },
    ) {
        Text(text = item.code, modifier = Modifier.padding(4.dp))
        Text(text = item.name, modifier = Modifier.padding(4.dp))
        Divider()
    }
}

@Composable
private fun SearchBar(
    searchValue: String,
    onBackClick: () -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colors.surface),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxHeight().size(60.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface
            )
        }
        TextField(
            searchValue,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier.fillMaxWidth().focusable(true),
            placeholder = {
                Text(text = stringResource(Res.string.select_currency_search_hint))
            }
        )
        Divider(Modifier.height(2.dp).fillMaxWidth().padding(vertical = 8.dp))
    }
}