package com.cmp.showcase.data.currency.converter.di

import com.cmp.showcase.data.currency.converter.remote.RemoteDataSource
import com.cmp.showcase.data.currency.converter.remote.createHttpClient
import com.cmp.showcase.data.currency.converter.repositoryImpl.CurrencyConverterRepoImpl
import com.cpm.showcase.domain.currency.converter.repository.CurrencyConverterRepo
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.cmp.showcase.data.currency.converter.usecase.GetCodes
import com.cmp.showcase.data.currency.converter.usecase.GetConversionAmount
import io.ktor.client.HttpClient

expect fun getHttpClient(): HttpClient

fun KoinApplication.currencyDataModule() = arrayOf(networkModule, platformDbModule)

val networkModule = module {
    single {
        createHttpClient()
    }
    singleOf(::RemoteDataSource)
    singleOf(::CurrencyConverterRepoImpl).bind<CurrencyConverterRepo>()
    singleOf(::GetCodes)
    singleOf(::GetConversionAmount)
}