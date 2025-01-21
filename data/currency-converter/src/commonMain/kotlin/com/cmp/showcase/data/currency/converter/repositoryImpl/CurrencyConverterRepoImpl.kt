package com.cmp.showcase.data.currency.converter.repositoryImpl

import com.cmp.showcase.data.currency.converter.remote.RemoteDataSource
import com.cpm.showcase.domain.currency.converter.entity.Currency
import com.cpm.showcase.domain.currency.converter.repository.CurrencyConverterRepo

class CurrencyConverterRepoImpl(private val remoteDataSource: RemoteDataSource): CurrencyConverterRepo {
    override suspend fun getCurrencyCodes(): List<Currency> {
        return remoteDataSource.getSupportedCurrencies()
    }

    override suspend fun fetchAndSave(currencyCode: String) {

    }

    override suspend fun getRate(baseCurrencyCode: String, targetCurrencyCode: String): Double {
        return 0.0
    }

}