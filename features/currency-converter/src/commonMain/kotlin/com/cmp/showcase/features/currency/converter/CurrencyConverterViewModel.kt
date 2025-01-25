package com.cmp.showcase.features.currency.converter

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmp.showcase.data.currency.converter.usecase.GetCodes
import com.cmp.showcase.data.currency.converter.usecase.GetConversionAmount
import com.cpm.showcase.domain.currency.converter.entity.Currency
import com.cpm.showcase.domain.currency.converter.repository.CurrencyConverterRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.round

class CurrencyConverterViewModel(
    private val getCodes: GetCodes,
    private val getConversionAmount: GetConversionAmount,
    private val currencyConverterRepo: CurrencyConverterRepo
): ViewModel() {
    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        fetchCodes(true)
    }

    val inputPanel by lazy {
        val list = mutableListOf<Item>()
        var i = 1
        repeat(3) {
            list.add(Item("${i++}", InputType.Number))
        }
        list.add(Item(Input.SWAP, InputType.Swap))
        repeat(3) {
            list.add(Item("${i++}", InputType.Number))
        }
        list.add(Item(Input.BACKSPACE, InputType.Backspace))
        repeat(3) {
            list.add(Item("${i++}", InputType.Number))
        }
        list.add(Item(Input.DOT, InputType.Dot))
        list.add(Item(Input.ZERO, InputType.Number))
        list.add(Item(Input.DOUBLE_ZERO, InputType.Number))
        list.add(Item(Input.CLEAR, InputType.Clear))
        return@lazy list.toList()
    }

    fun fetchCodes(shouldSetDefault: Boolean = false){
        _state.value = _state.value.copy(currencyUiState = CurrencyUiState.Loading)
        viewModelScope.launch {
            try {
                val codes = getCodes()
                _state.value = _state.value.copy(currencyUiState = CurrencyUiState.SupportedCurrencies(codes))
                if (shouldSetDefault){
                    setCurrency(isBaseCurrency = true, currency = codes.first { it.code == "USD" })
                    setCurrency(isBaseCurrency = false, currency = codes.first { it.code =="INR" })
                }
                println("Success VM")
            }catch (e: Exception){
                println(e.printStackTrace())
                _state.value = _state.value.copy(currencyUiState = CurrencyUiState.Error(e.message ?: "Something went wrong!"))
            }
        }
    }

    private fun convert(baseCurrencyCode: String, targetCurrencyCode: String, amount: Double){
        viewModelScope.launch {
            try {
                val value = getConversionAmount(baseCurrencyCode,targetCurrencyCode, amount)
                _state.value = _state.value.copy(targetAmount = _state.value.targetAmount.copy(title = value.toString()))
                println("Success VM")
            }catch (e: Exception){
                println(e.printStackTrace())
//                _state.value = CurrencyUiState.Error(e.message ?: "Something went wrong!")
            }

        }
    }

    fun onInputClick(item: Item){
        when(item.inputType){
            InputType.Dot->{
                if(_state.value.baseAmount.title.contains(".")){
                    return
                }
                _state.value = _state.value.copy(baseAmount = _state.value.baseAmount.copy(title = _state.value.baseAmount.title + "."))
            }
            InputType.Number -> {
                if (item.title == Input.DOUBLE_ZERO && _state.value.baseAmount.title.isEmpty()){
                    return
                }
                val currentValue = _state.value.baseAmount.title
                val newValue = currentValue + item.title
                _state.value = _state.value.copy(baseAmount = _state.value.baseAmount.copy(title = newValue))
                convertAndUpdateView()

            }
            InputType.Clear -> {
                clear()
            }
            InputType.Swap ->{
                onSwapCurrency()
            }
            InputType.Backspace ->{
                _state.value = _state.value.copy(baseAmount = _state.value.baseAmount.copy(title = _state.value.baseAmount.title.dropLast(1)))
                convertAndUpdateView()
            }
            else -> {
                // do nothing
            }
        }
    }

    private fun clear() {
        _state.value = _state.value.copy(
            baseAmount = _state.value.baseAmount.copy(title = ""),
            targetAmount = _state.value.targetAmount.copy(title = "")
        )
    }

    private fun convertAndUpdateView(){
        try {
            convert(_state.value.baseCurrencyCode.title, _state.value.targetCurrencyCode.title, _state.value.baseAmount.title.toDouble())
        }catch (e : NumberFormatException){
            println("Invalid number format")
           clear()
        }
    }

    private fun onSwapCurrency(){
        _state.value = _state.value.copy(
            baseCountry = _state.value.targetCountry,
            targetCountry = _state.value.baseCountry,
            baseToTargetRate = _state.value.targetToBaseRate,
            targetToBaseRate = _state.value.baseToTargetRate,
            baseCurrencyCode = _state.value.targetCurrencyCode.copy(inputType = InputType.BaseCurrency),
            targetCurrencyCode = _state.value.baseCurrencyCode.copy(inputType = InputType.TargetCurrency)
        )
        convertAndUpdateView()
    }

    fun setCurrency(isBaseCurrency: Boolean, currency: Currency) {
        println("Set currency ${currency.code}, $isBaseCurrency")
        if (isBaseCurrency){
            _state.value = _state.value.copy(baseCurrencyCode = _state.value.baseCurrencyCode.copy(title = currency.code), baseCountry = currency.name)
        }else{
            _state.value = _state.value.copy(targetCurrencyCode = _state.value.targetCurrencyCode.copy(title = currency.code), targetCountry = currency.name)
        }
        setRates()
        convertAndUpdateView()
    }

    private fun setRates(){
        viewModelScope.launch {
            val baseToTargetRate = currencyConverterRepo.getRate(_state.value.baseCurrencyCode.title, _state.value.targetCurrencyCode.title)
            val targetToBaseRate = currencyConverterRepo.getRate(_state.value.targetCurrencyCode.title, _state.value.baseCurrencyCode.title)
            _state.value = _state.value.copy(baseToTargetRate = baseToTargetRate.toString(), targetToBaseRate = targetToBaseRate.toString())
        }
    }
}


data class State(
    val baseCountry: String = "",
    val targetCountry: String = "",
    val baseToTargetRate: String = "",
    val targetToBaseRate: String = "",
    val baseCurrencyCode: Item = Item("USD", InputType.BaseCurrency),
    val targetCurrencyCode: Item = Item("INR", InputType.TargetCurrency),
    val baseAmount: Item = Item("", InputType.BaseAmount),
    val targetAmount: Item = Item("", InputType.TargetAmount),
    val currencyUiState: CurrencyUiState = CurrencyUiState.Loading,
)

sealed interface CurrencyUiState{
    data object Loading: CurrencyUiState
    data class SupportedCurrencies(val codes: List<Currency>): CurrencyUiState
    data class Error(val errorMessage: String): CurrencyUiState
}

enum class InputType{
    Number,
    Dot,
    Clear,
    BaseCurrency,
    TargetCurrency,
    BaseAmount,
    TargetAmount,
    Swap,
    Backspace
}

data class Item(val title: String, val inputType: InputType, val isActive: Boolean = false, val interactionSource: MutableInteractionSource = MutableInteractionSource()){
    fun displayValue(): String {
        return when (inputType) {
            InputType.BaseAmount -> title.ifEmpty { "0.0" }
            InputType.TargetAmount -> formatValue()

            else -> title
        }
    }

    private fun formatValue(): String {
        if (title.isEmpty()) return "0.0"
        val value = title.toDouble()
        return round(value).toString()
    }
}