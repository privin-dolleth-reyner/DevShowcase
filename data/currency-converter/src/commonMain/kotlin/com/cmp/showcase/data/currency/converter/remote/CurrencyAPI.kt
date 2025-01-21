package com.cmp.showcase.data.currency.converter.remote

import com.cpm.showcase.domain.currency.converter.Entity
import com.cpm.showcase.domain.currency.converter.entity.Currency
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyAPI(
    val name: String,
    val code: String
): Entity{
    fun toEntity(): Currency {
        return Currency(name, code)
    }
}

