package com.cmp.showcase.data.currency.converter.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

actual fun getHttpClient(): HttpClient {
    return HttpClient(OkHttp.create())
}