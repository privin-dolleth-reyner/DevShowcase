package com.cmp.showcase.features.currency.converter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import devshowcase.features.currency_converter.generated.resources.Res
import devshowcase.features.currency_converter.generated.resources.currency_converter_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CurrencyConverterScreen(
    onChangeCurrency: (isBaseCurrency: Boolean) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CurrencyConverterViewModel = koinViewModel()
) {
    Surface(modifier = modifier.fillMaxSize().statusBarsPadding().clickable(enabled = false) {}) {
        Box(modifier = Modifier.fillMaxSize()) {
            val state by viewModel.state.collectAsStateWithLifecycle()

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Title(onBackClick = onBackClick)
                CurrencyPanel(state, modifier = Modifier.fillMaxWidth().weight(1.3f), onClick = {
                    when(it.inputType){
                        InputType.BaseCurrency ->onChangeCurrency(true)
                        InputType.TargetCurrency -> onChangeCurrency(false)
                        else -> viewModel.onInputClick(it)
                    }

                })
                InputButtons(
                    viewModel.inputPanel, onClick = {
                        viewModel.onInputClick(it)
                    },
                    modifier = Modifier.fillMaxWidth().weight(1.5f)
                )
            }
        }

    }
}

@Composable
private fun Title(onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    Column (modifier = modifier){
        Row(
            modifier = Modifier.fillMaxWidth().height(80.dp)
                .background(MaterialTheme.colors.surface),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick, modifier = Modifier.fillMaxHeight().size(60.dp)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface
                )
            }
            Text(
                text = stringResource(Res.string.currency_converter_title),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.fillMaxWidth()
            )

        }
        Divider(Modifier.fillMaxWidth(), color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
    }

}

@Preview
@Composable
fun InputButtons(items: List<Item>, modifier: Modifier = Modifier, onClick: (Item) -> Unit) {
    BoxWithConstraints(modifier = modifier) {
        val parentWidthDp = maxWidth
        val parentHeightDp = maxHeight

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.SpaceBetween,
            contentPadding = PaddingValues(1.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            val spacing = 1.dp * 4
            val width = (parentWidthDp - spacing) / 4
            val height = (parentHeightDp - spacing) / 4
            var i = 0
            repeat(3){
                items(3){
                    val item = items[i++ % 15]
                    InputButton(item, onClick, Modifier.fillMaxWidth().height(height).padding(4.dp))
                }
                item {
                    InputButton(items[i++ % 15], onClick, Modifier.fillMaxWidth().height(height).padding(4.dp))
                }
            }
            item(span = { GridItemSpan(2) }) {
                InputButton(items[i++ % 15], onClick, Modifier.width(width).height(height).padding(4.dp))
            }
            item(span = { GridItemSpan(1) }) {
                InputButton(items[i++ % 15], onClick, Modifier.width(width).height(height).padding(4.dp))
            }
            item(span = { GridItemSpan(1) }) {
                InputButton(items[i++ % 15], onClick, Modifier.width(width).height(height).padding(4.dp))
            }
        }
    }
}

@Composable
fun InputButton(item: Item, onClick: (Item) -> Unit, modifier: Modifier = Modifier) {
    val interactionSource by remember { mutableStateOf(item.interactionSource) }

    val isPressed by interactionSource.collectIsPressedAsState()
    var backgroundColor = MaterialTheme.colors.primary

    when(item.inputType){
        InputType.Clear,
        InputType.Swap->{
            backgroundColor = MaterialTheme.colors.secondary
        }
        else-> {}
    }
    TextButton(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        elevation = ButtonDefaults.elevation(),
        onClick = {
            onClick(item)
        },
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(backgroundColor = if (isPressed) MaterialTheme.colors.onSurface else backgroundColor),
    ) {
        Text(
            text = item.displayValue(),
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(4.dp),
        )
    }
}

@Composable
fun CurrencyPanel(state: State, modifier: Modifier, onClick: (Item) -> Unit) {
    val baseScrollState = rememberScrollState()
    val targetScrollState = rememberScrollState()
    Column(modifier = modifier.padding(8.dp)) {
        Currency(
            item = state.baseCurrencyCode,
            rate = state.baseToTargetRate,
            against = state.targetCurrencyCode.title,
            countryName = state.baseCountry,
            amount = state.baseAmount.displayValue(),
            onClick = onClick,
            modifier = Modifier.weight(1f).verticalScroll(state = baseScrollState, enabled = true)
        )
        Currency(
            item = state.targetCurrencyCode,
            rate = state.targetToBaseRate,
            against = state.baseCurrencyCode.title,
            countryName = state.targetCountry,
            amount = state.targetAmount.displayValue(),
            onClick = onClick,
            modifier = Modifier.weight(1f).verticalScroll(state = targetScrollState, enabled = true)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun Currency(item: Item, rate: String, against: String, countryName: String, amount: String, onClick: (Item) -> Unit, modifier: Modifier = Modifier){
    val scrollState = rememberScrollState()
    Box(modifier = modifier.padding(8.dp)){
        Card( onClick = { onClick(item)}, shape = RoundedCornerShape(8.dp)) {
            Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Text(
                    text = countryName,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = "1 ${item.title} = $rate $against",
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(4.dp)
                )
                Spacer(Modifier.height(1.dp))
                Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxWidth().padding(2.dp)) {
                    Text("$amount ${item.title}", style = MaterialTheme.typography.h4, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.horizontalScroll(state = scrollState,enabled = true))
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null, modifier = Modifier.size(24.dp).align(Alignment.CenterEnd))
                }
                Spacer(Modifier.height(1.dp))
            }
        }
    }

}