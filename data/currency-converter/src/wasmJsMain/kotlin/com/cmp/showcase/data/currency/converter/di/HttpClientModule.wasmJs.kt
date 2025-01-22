package com.cmp.showcase.data.currency.converter.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js

actual fun getHttpClient(): HttpClient {
    return HttpClient(Js)
}