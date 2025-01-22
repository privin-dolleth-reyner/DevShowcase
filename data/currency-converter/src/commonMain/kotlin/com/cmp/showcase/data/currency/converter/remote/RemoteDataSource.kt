package com.cmp.showcase.data.currency.converter.remote

import com.cmp.showcase.data.currency.converter.remote.dto.GetCurrencyCodes
import com.cmp.showcase.data.currency.converter.remote.dto.GetExchangeRate
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RemoteDataSource(private val client: HttpClient) {

    suspend fun getSupportedCurrencies(): GetCurrencyCodes{
        val response = client.get("/v6/codes")
        return response.body<GetCurrencyCodes>()
    }

    suspend fun getExchangeRates(baseCurrencyCode: String): GetExchangeRate{
        val response = client.get("/v6/latest/$baseCurrencyCode")
        return response.body<GetExchangeRate>()
    }
}