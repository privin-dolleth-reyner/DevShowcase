package com.cmp.showcase.data.currency.converter.repositoryImpl

import com.cmp.showcase.data.currency.converter.local.LocalDataSource
import com.cmp.showcase.data.currency.converter.remote.RemoteDataSource
import com.cpm.showcase.domain.currency.converter.entity.Currency
import com.cpm.showcase.domain.currency.converter.repository.CurrencyConverterRepo

class CurrencyConverterRepoImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CurrencyConverterRepo {
    override suspend fun getCurrencyCodes(): List<Currency> {
        val cache = localDataSource.getAllCurrencies()
        if (cache.isNotEmpty()){
            return cache
        }
        return remoteDataSource.getSupportedCurrencies().supported_codes
            .map {
                Currency(name = it[1], code = it[0])
            }.toList().also {
                localDataSource.insertAllCurrencies(it)
            }
    }

    override suspend fun getRate(baseCurrencyCode: String, targetCurrencyCode: String): Double {
        val cachedRate = localDataSource.getExchangeRate(baseCurrencyCode, targetCurrencyCode) ?: 0.0
        if (cachedRate != 0.0){
            return cachedRate
        }
        return remoteDataSource.getExchangeRates(baseCurrencyCode).also {
            localDataSource.insertAllExchangeRates(baseCurrencyCode, it.conversion_rates)
        }.conversion_rates[targetCurrencyCode] ?: 0.0
    }

}