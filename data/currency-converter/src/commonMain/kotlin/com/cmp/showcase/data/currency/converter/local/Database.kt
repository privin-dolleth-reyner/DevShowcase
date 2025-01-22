package com.cmp.showcase.data.currency.converter.local

import app.cash.sqldelight.db.SqlDriver
import com.cmp.showcase.currency.converter.CurrencyConverter
import com.cpm.showcase.domain.currency.converter.entity.Currency


interface DatabaseFactory {
    fun createSqlDriver(): SqlDriver
}

class LocalDatabase(databaseFactory: DatabaseFactory) {

    private val database = CurrencyConverter(databaseFactory.createSqlDriver())

    private val query = database.currencyConverterQueries

    suspend fun getAllCurrencies(): List<Currency> {
        return query.getAllCurrencies().executeAsList().map { Currency(it.name, it.code) }
    }

    suspend fun insertCurrency(currency: Currency){
        query.insertCurrency(name = currency.name, code =  currency.code)
    }

    suspend fun deleteAllCurrencies() {
        query.deleteAllCurrencies()
    }

    suspend fun insertExchangeRate(baseCurrencyCode: String, targetCurrencyCode: String, rate: Double){
        query.insertExchangeRate(baseCurrencyCode, targetCurrencyCode, rate)
    }

    suspend fun getExchangeRate(baseCurrencyCode: String, targetCurrencyCode: String): Double? {
        return query.getExchangeRate(baseCurrencyCode, targetCurrencyCode).executeAsOneOrNull()
    }
}