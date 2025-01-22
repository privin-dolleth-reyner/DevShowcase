package com.cmp.showcase.data.currency.converter.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.cmp.showcase.currency.converter.CurrencyConverter
import com.cmp.showcase.data.currency.converter.local.DatabaseFactory


class NativeDatabaseFactory: DatabaseFactory{
    override fun createSqlDriver(): SqlDriver {
        return NativeSqliteDriver(
            CurrencyConverter.Schema,
            "currencyConverter.db"
        )
    }
}