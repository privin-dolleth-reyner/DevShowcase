package com.cmp.showcase.data.currency.converter.repositoryImpl

import com.cmp.showcase.data.currency.converter.remote.RemoteDataSource
import com.cpm.showcase.domain.currency.converter.entity.Currency
import com.cpm.showcase.domain.currency.converter.repository.CurrencyConverterRepo

class CurrencyConverterRepoImpl(
    private val remoteDataSource: RemoteDataSource
) : CurrencyConverterRepo {
    override suspend fun getCurrencyCodes(): List<Currency> {
        return remoteDataSource.getSupportedCurrencies().supported_codes
            .map {
                Currency(it[0], it[1])
            }.toList()
    }

    override suspend fun fetchAndSave(currencyCode: String) {

    }

    override suspend fun getRate(baseCurrencyCode: String, targetCurrencyCode: String): Double {
        return remoteDataSource.getExchangeRates(baseCurrencyCode).conversion_rates[targetCurrencyCode] ?: 0.0
    }

}