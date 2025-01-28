package com.cmp.showcase.features.profile.di

import com.cmp.showcase.features.profile.ProfileViewmodel
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featuresModule = module {
    viewModelOf(::ProfileViewmodel)
}

fun KoinApplication.profileFeaturesModule() = arrayOf(featuresModule)