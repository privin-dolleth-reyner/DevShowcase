package com.cmp.showcase.data.currency.converter.remote

import com.cmp.showcase.data.currency.converter.BuildConfig
import com.cmp.showcase.data.currency.converter.di.getHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
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
        install(Logging) {
            level = LogLevel.ALL
            logger = Logger.SIMPLE
        }
        install(DefaultRequest) {
            url(BuildConfig.BASE_URL_CURRENCY_CONVERTER)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(
                        accessToken = BuildConfig.API_KEY,
                        refreshToken = ""
                    )
                }
            }
        }
        install(ContentNegotiation) {

            json(
                json = Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }
}