package com.cmp.showcase.data.currency.converter.local

import app.cash.sqldelight.db.SqlDriver


interface DatabaseFactory {
    fun createSqlDriver(): SqlDriver
}

