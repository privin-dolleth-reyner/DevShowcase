package com.cmp.showcase.di

import com.cmp.showcase.data.currency.converter.di.currencyDataModule
import com.cmp.showcase.features.currency.converter.di.currencyFeatureModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration


fun initKoin(config: KoinAppDeclaration? = null){
    startKoin {
        config?.invoke(this)

        modules(
            *currencyDataModule(),
            *currencyFeatureModule()
        )
    }
}