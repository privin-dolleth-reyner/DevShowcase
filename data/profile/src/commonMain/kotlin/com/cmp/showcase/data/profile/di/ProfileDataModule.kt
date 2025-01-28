package com.cmp.showcase.data.profile.di

import com.cmp.showcase.data.profile.ProfileRepository
import com.cmp.showcase.data.profile.ProfileRepositoryImpl
import org.koin.core.KoinApplication
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single {
        ProfileRepositoryImpl()
    }.bind<ProfileRepository>()
}

fun KoinApplication.profileDataModule() = arrayOf(dataModule)