package com.cmp.showcase.data.currency.converter.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.cmp.showcase.currency.converter.CurrencyConverter
import com.cmp.showcase.data.currency.converter.local.DatabaseFactory

class AndroidDataBaseFactory(private val context: Context): DatabaseFactory {
    override fun createSqlDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = CurrencyConverter.Schema,
            context = context,
            name = "currencyConverter.db"
        )
    }
}