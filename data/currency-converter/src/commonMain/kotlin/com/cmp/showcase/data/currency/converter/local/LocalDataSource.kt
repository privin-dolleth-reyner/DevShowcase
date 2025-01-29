package com.cmp.showcase.data.currency.converter.local

import com.cmp.showcase.currency.converter.CurrencyConverter
import com.cmp.showcase.currency.converter.ExchangeRateTable
import com.cpm.showcase.domain.currency.converter.entity.Currency

class LocalDataSource(databaseFactory: DatabaseFactory) {

    private val database = CurrencyConverter(databaseFactory.createSqlDriver())

    private val query = database.currencyConverterQueries

    suspend fun getAllCurrencies(): List<Currency> {
        return query.getAllCurrencies().executeAsList().map { Currency(it.name, it.code) }
    }

    suspend fun insertAllCurrencies(list: List<Currency>){
        query.transaction {
            list.forEach {
                query.insertCurrency(name = it.name, code =  it.code)
            }
        }

    }

    suspend fun insertCurrency(currency: Currency){
        query.insertCurrency(name = currency.name, code =  currency.code)
    }

    suspend fun deleteAllCurrencies() {
        query.deleteAllCurrencies()
    }

    suspend fun insertAllExchangeRates(baseCurrencyCode: String, map: Map<String,Double>, lastUpdate: Long, nextUpdate: Long){
        query.transaction {
            map.forEach {
                query.insertExchangeRate(baseCurrencyCode, it.key, it.value, lastUpdate, nextUpdate)
            }
        }

    }

    suspend fun insertExchangeRate(baseCurrencyCode: String, targetCurrencyCode: String, rate: Double, lastUpdate: Long, nextUpdate: Long){
        query.insertExchangeRate(baseCurrencyCode, targetCurrencyCode, rate, lastUpdate, nextUpdate)
    }

    suspend fun getExchangeRate(baseCurrencyCode: String, targetCurrencyCode: String): ExchangeRateTable? {
        return query.getExchangeRate(baseCurrencyCode, targetCurrencyCode).executeAsOneOrNull()
    }
}