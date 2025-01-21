package com.cmp.showcase.data.currency.converter.remote

import com.cmp.showcase.data.currency.converter.di.getHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


fun createHttpClient(): HttpClient {
    return getHttpClient().config {
        install(Logging){
            level = LogLevel.ALL
            logger = Logger.SIMPLE
        }
        install(DefaultRequest){
            url("https://v6.exchangerate-api.com")
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
        install(ContentNegotiation){

            json(
                json = Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }
}