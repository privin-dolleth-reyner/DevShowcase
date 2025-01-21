package com.cpm.showcase.domain.currency.converter.repository

import com.cpm.showcase.domain.currency.converter.entity.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyConverterRepo {
    fun getCurrencyCodes(): Flow<Currency>

    suspend fun fetchAndSave(currencyCode: String)

    suspend fun getRate(baseCurrencyCode: String, targetCurrencyCode: String): Double
}
