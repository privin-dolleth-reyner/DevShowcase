package com.cmp.showcase.data.currency.converter.remote

import com.cpm.showcase.domain.currency.converter.entity.Currency
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteDataSource(private val client: HttpClient) {

    suspend fun getSupportedCurrencies(): List<Currency> {
        val response = client.get("/v6/latest/USD")
        when (val status = response.status.value){
            in 200..299 ->{
                println("Success")
            }
            else ->{
                println("Network error $status")
                throw Exception("Network error")
            }

        }
        return emptyList()
    }
}