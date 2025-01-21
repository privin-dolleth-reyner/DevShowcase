package com.cmp.showcase.data.currency.converter.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.JsClient

actual fun getHttpClient(): HttpClient {
    return HttpClient()
}