package com.cmp.showcase.features.currency.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmp.showcase.data.currency.converter.usecase.GetCodes
import com.cmp.showcase.data.currency.converter.usecase.GetConversionAmount
import com.cpm.showcase.domain.currency.converter.entity.Currency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CurrencyConverterViewModel(
    private val getCodes: GetCodes,
    private val getConversionAmount: GetConversionAmount
): ViewModel() {
    private val _state = MutableStateFlow<CurrencyUiState>(CurrencyUiState.Idle)
    val state = _state.asStateFlow()

    fun fetchCodes(){
        _state.value = CurrencyUiState.Loading
        viewModelScope.launch {
            try {
                val codes = getConversionAmount("SGD","INR", 2.0)
                _state.value = CurrencyUiState.ConversionAmount(codes)
                println("Success VM")
            }catch (e: Exception){
                println(e.printStackTrace())
                _state.value = CurrencyUiState.Error(e.message ?: "Something went wrong!")
            }

        }
    }
}

sealed interface CurrencyUiState{
    data object Idle: CurrencyUiState
    data object Loading: CurrencyUiState
    data class SupportedCurrencies(val codes: List<Currency>): CurrencyUiState
    data class ConversionAmount(val amount: Double): CurrencyUiState
    data class Error(val errorMessage: String): CurrencyUiState
}