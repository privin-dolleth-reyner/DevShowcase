package com.cpm.showcase.domain.currency.converter.repository

import com.cpm.showcase.domain.currency.converter.entity.Currency

interface CurrencyConverterRepo {
    suspend fun getCurrencyCodes(): List<Currency>

    suspend fun getRate(baseCurrencyCode: String, targetCurrencyCode: String): Double
}
