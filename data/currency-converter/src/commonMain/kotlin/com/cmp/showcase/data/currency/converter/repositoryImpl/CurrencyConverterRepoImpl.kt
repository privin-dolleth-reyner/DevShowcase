package com.cmp.showcase.data.currency.converter.repositoryImpl

import com.cpm.showcase.domain.currency.converter.entity.Currency
import com.cpm.showcase.domain.currency.converter.repository.CurrencyConverterRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class CurrencyConverterRepoImpl: CurrencyConverterRepo {
    override fun getCurrencyCodes(): Flow<Currency> {
        return emptyFlow()
    }

    override suspend fun fetchAndSave(currencyCode: String) {

    }

    override suspend fun getRate(baseCurrencyCode: String, targetCurrencyCode: String): Double {
        return 0.0
    }

}