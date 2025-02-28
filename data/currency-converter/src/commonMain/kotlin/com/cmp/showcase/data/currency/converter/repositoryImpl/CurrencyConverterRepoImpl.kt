package com.cmp.showcase.data.currency.converter.repositoryImpl

import com.cmp.showcase.data.currency.converter.local.LocalDataSource
import com.cmp.showcase.data.currency.converter.remote.RemoteDataSource
import com.cpm.showcase.domain.currency.converter.entity.Currency
import com.cpm.showcase.domain.currency.converter.repository.CurrencyConverterRepo
import kotlinx.datetime.Clock

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
        val cachedRate = localDataSource.getExchangeRate(baseCurrencyCode, targetCurrencyCode)
        // Check expiry of data
        val currentTime = Clock.System.now()
        if (cachedRate != null && cachedRate.rate != 0.0 &&  currentTime.epochSeconds < cachedRate.nextUpdate){
            return cachedRate.rate
        }
        return remoteDataSource.getExchangeRates(baseCurrencyCode).also {
            localDataSource.insertAllExchangeRates(baseCurrencyCode, it.conversion_rates, it.time_last_update_unix, it.time_next_update_unix)
        }.conversion_rates[targetCurrencyCode] ?: 0.0
    }

}