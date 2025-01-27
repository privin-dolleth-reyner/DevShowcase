package com.cmp.showcase.data.currency.converter.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetCurrencyCodes(
    val result: String,
    val documentation: String,
    val terms_of_use: String,
    val supported_codes: List<List<String>>
)