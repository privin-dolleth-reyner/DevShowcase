package com.cmp.showcase.di

import com.cmp.showcase.data.core.di.coreDataModule
import com.cmp.showcase.data.currency.converter.di.currencyDataModule
import com.cmp.showcase.data.profile.di.profileDataModule
import com.cmp.showcase.features.currency.converter.di.currencyFeatureModule
import com.cmp.showcase.features.profile.di.profileFeaturesModule
import com.cmp.showcase.ui.core.di.uiModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration


fun initKoin(config: KoinAppDeclaration? = null){
    startKoin {
        config?.invoke(this)

        modules(
            appModule,
            *uiModule(),
            *coreDataModule(),
            *currencyDataModule(),
            *currencyFeatureModule(),
            *profileDataModule(),
            *profileFeaturesModule(),
        )
    }
}