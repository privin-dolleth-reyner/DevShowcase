package com.cmp.showcase.data.currency.converter.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetExchangeRate(
    val documentation: String,
    val terms_of_use: String,
    val time_last_update_unix: Long,
    val time_next_update_unix: Long,
    val time_last_update_utc: String,
    val time_next_update_utc: String,
    val base_code: String,
    val conversion_rates: Map<String, Double>
)